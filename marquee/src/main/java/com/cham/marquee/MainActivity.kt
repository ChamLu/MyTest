package com.cham.marquee

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.cham.marquee.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private var animatedValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val in3 = listOf<Int>(
            R.color.teal_200,
            R.color.white,
            R.color.purple_200,
            R.color.purple_500,
            R.color.colorPrimary_e6,
            R.color.purple_700
        )




        var inColor =in3[0]
        lifecycleScope.launch {
            flow<Int> {
                while (true) {
                    delay(2000L)
                    val random = (0..5).random()
                    emit(in3[random])
                }

            }.flowOn(Dispatchers.Main).collect {

                val animator = ValueAnimator.ofInt(0,1)

                animator.duration =300

                animator.addUpdateListener {p->
                    animatedValue = p.animatedValue as Int
                }
                animator.start()


                //ContextCompat.getColor(this@MainActivity, it)

                mBinding.tvPlayer.setTextColor(getCurrentColor(100f,inColor,it))

                inColor =it




            }



        }

    }


    /**
     * 根据fraction值来计算当前的颜色。
     */
    private fun getCurrentColor(fraction: Float, startColor: Int, endColor: Int): Int {
        val redCurrent: Int
        val blueCurrent: Int
        val greenCurrent: Int
        val alphaCurrent: Int
        val redStart: Int = Color.red(startColor)
        val blueStart: Int = Color.blue(startColor)
        val greenStart: Int = Color.green(startColor)
        val alphaStart: Int = Color.alpha(startColor)
        val redEnd: Int = Color.red(endColor)
        val blueEnd: Int = Color.blue(endColor)
        val greenEnd: Int = Color.green(endColor)
        val alphaEnd: Int = Color.alpha(endColor)
        val redDifference = redEnd - redStart
        val blueDifference = blueEnd - blueStart
        val greenDifference = greenEnd - greenStart
        val alphaDifference = alphaEnd - alphaStart
        redCurrent = (redStart + fraction * redDifference).toInt()
        blueCurrent = (blueStart + fraction * blueDifference).toInt()
        greenCurrent = (greenStart + fraction * greenDifference).toInt()
        alphaCurrent = (alphaStart + fraction * alphaDifference).toInt()
        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent)
    }
}