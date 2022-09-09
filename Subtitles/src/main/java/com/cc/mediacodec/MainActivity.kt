package com.cc.mediacodec

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.cc.mediacodec.databinding.ActivityMainBinding
import java.util.ArrayList


class MainActivity : AppCompatActivity(), SensorEventListener {


    companion object {
        val TAG = "MainActivity"
        val TITLE = "TITLE"
        val MILLISECOND = "MILLISECOND"
        val COLORNUM = "COLORNUM"

        fun startMainActivity(
            context: Context,
            title: String? = "FrogMonster",
            millisecond: String?,
            colorNum: String?
        ) {
            val intent = Intent(context, MainActivity::class.java).apply {

                if (title.isNullOrBlank()) {
                    putExtra(TITLE, "FrogMonster")
                } else putExtra(TITLE, title)


                if (millisecond.isNullOrBlank()) {
                    putExtra(MILLISECOND, 1000)
                } else putExtra(MILLISECOND, millisecond.toInt())


                if (colorNum.isNullOrBlank()) {
                    putExtra(COLORNUM, 0)
                } else putExtra(COLORNUM, colorNum.toInt())


            }
            context.startActivity(intent)
        }
    }

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    private var sensorManager: SensorManager? = null


    //加速度传感器测量施加到设备的加速度，包括重力
    //动态检测（摇晃、倾斜等） 测量在所有三个物理轴向（x、y、z）上施加在设备上的重力，单位为 m/s2。
    private var sensorAaccelerometer: Sensor? = null

    private var gravity = mutableListOf(0f, 0f, 0f)

    private var linear_acceleration = mutableListOf<Float>(0f, 0f, 0f)

    val mTitle: String? by lazy {
        intent.extras?.getString(TITLE)
    }
    val mMillisecond by lazy {
        intent.extras?.getInt(MILLISECOND)
    }

    val mColorNum by lazy {
        intent.extras?.getInt(COLORNUM)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(mBinding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorAaccelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        mTitle?.let {
            mBinding.tvPlayer.text = mTitle
        }



        ActivityCompat.requestPermissions(this, permissions, 1000)

        val harmonyTypeFace =
            Typeface.createFromAsset(assets, "fonts/HarmonyOS_Sans_Medium_Italic.ttf")

        mBinding.tvPlayer.typeface = harmonyTypeFace


        val listColor1 =
            mutableListOf<Int>(Color.parseColor("#F3E5F5"), Color.parseColor("#4A148C"))

        val listColor2 =
            mutableListOf<Int>(Color.parseColor("#EA80FC"), Color.parseColor("#AA00FF"))

        //莱茵蓝
        val listColor3 =
            mutableListOf<Int>(Color.parseColor("#E2F2FD"), Color.parseColor("#002FA7"))


        var aColor = mutableListOf<Int>()

        when (mColorNum) {
            0 -> {
                aColor = listColor1
            }
            1 -> {
                aColor = listColor2
            }
            2 -> {
                aColor = listColor3
            }
        }


        val colorAnim =
            ObjectAnimator.ofInt(mBinding.tvPlayer, "textColor", aColor[0], aColor[1])
        colorAnim.duration = mMillisecond!!.toLong()
        val argbEvaluator = MyColorEvaluator()
        colorAnim.setEvaluator(argbEvaluator)
        colorAnim.repeatCount = ValueAnimator.INFINITE
        colorAnim.repeatMode = ValueAnimator.REVERSE
        colorAnim.start()

        val apAnim =
            ObjectAnimator.ofFloat(mBinding.lottieClap, "alpha", 1f, 0f, 1f)
        apAnim.duration = 1500
        apAnim.repeatCount = ValueAnimator.INFINITE
        apAnim.repeatMode = ValueAnimator.REVERSE
        apAnim.start()
    }


    inner class MyColorEvaluator : TypeEvaluator<Int> {
        private var startHsv = FloatArray(3)
        var endHsv = FloatArray(3)
        var outHsv = FloatArray(3)


        override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
            Color.colorToHSV(startValue, startHsv)
            Color.colorToHSV(endValue, endHsv)
            val alpha =
                startValue shr 24 + ((endValue shr 24 - startValue shr 24) * fraction).toInt()
            // 计算当前动画完成度（fraction）所对应的颜色值
            if (endHsv[0] - startHsv[0] > 180) {
//                val s1  = endHsv[0]
//                endHsv[0]  = s1- 360. toFloat()

                endHsv[0].minus(360)
            } else if (endHsv[0] - startHsv[0] < -180) {
//                val s1 = endHsv[0]
//                endHsv[0] =s1+360

                endHsv[0].plus(360)
            }
            outHsv[0] = startHsv[0] + (endHsv[0] - startHsv[0]) * fraction
            if (outHsv[0] > 360) {
//                outHsv.get(0) -= 360
                outHsv[0].minus(360)

            } else if (outHsv[0] < 0) {
//                outHsv.get(0) += 360
                outHsv[0].plus(360)
            }
            outHsv[1] = startHsv[1] + (endHsv[1] - startHsv[1]) * fraction
            outHsv[2] = startHsv[2] + (endHsv[2] - startHsv[2]) * fraction


            return Color.HSVToColor(alpha, outHsv)


        }

    }

    override fun onResume() {
        super.onResume()
        sensorAaccelerometer?.also { light ->
            sensorManager?.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    /**
     * 加速度计使用标准的传感器坐标系。实际上，这意味着当设备以自然屏幕方向平放在桌子上时，以下条件适用：
     *如果将设备推向左侧（因此向右移动），则 x 加速度值为正。
     *如果将设备推到底部（因此它向远离您的方向移动），则 y 加速度值为正。
     *如果您以 A m/s2 的加速度将设备推向天空，则 z 加速度值等于 A + 9.81，该值对应设备的加速度 (+A m/s2) 减去重力 (-9.81 m/s2)。
     *固定设备的加速度值为 +9.81，该值对应设备的加速度（0 m/s2 减去重力 -9.81 m/s2)。
     * */
    override fun onSensorChanged(event: SensorEvent?) {
        //events.values[0] 沿 x 轴的加速力（包括重力）
        //events.values[1] 沿 y 轴的加速力（包括重力）
        //events.values[2] 沿 z 轴的加速力（包括重力）


        event?.let { events ->

            val alpha: Float = 0.8f

            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * events.values[0]
            gravity[1] = alpha * gravity[1] + (1 - alpha) * events.values[1]
            gravity[2] = alpha * gravity[2] + (1 - alpha) * events.values[2]

            // Remove the gravity contribution with the high-pass filter.
            linear_acceleration[0] = events.values[0] - gravity[0]
            linear_acceleration[1] = events.values[1] - gravity[1]
            linear_acceleration[2] = events.values[2] - gravity[2]


            Log.e(TAG, "onSensorChanged: ${linear_acceleration[0]}")


        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}