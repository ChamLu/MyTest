package com.cham.marquee

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.cham.lib_annotations.BindLayout
import com.cham.lib_annotations.BindView
import com.google.android.material.button.MaterialButton

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/3/9 17:22
 * @UpdateUser:
 * @UpdateDate:     2021/3/9 17:22
 * @UpdateRemark:
 */
@BindLayout(R.layout.activity_testprocessor)
class TestProcessorActivity :AppCompatActivity(){
    private lateinit var container: View
    @BindView(R.id.btn1)
    lateinit var  btn1: MaterialButton

    @BindView(R.id.btn2)
    lateinit var  btn2: MaterialButton
    companion object {
        fun startTestProcessorActivity(context: Context) {
            val intent = Intent(context, TestProcessorActivity::class.java)
            context.startActivity(intent)

        }
    }


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ChamBind.bind(this)
        container = findViewById(R.id.motion)
        val motionLayout = container as? MotionLayout ?: return
        (container as? MotionLayout)?.setDebugMode( MotionLayout.DEBUG_SHOW_PATH)


        val primaryArray = resources.obtainTypedArray(R.array.primary_palettes)
        btn1.setOnClickListener {

            setTheme(primaryArray.getResourceId(0,0) )


        }

        btn2.setOnClickListener {
            motionLayout.transitionToStart()
           setTheme(primaryArray.getResourceId(2,0) )



        }

    }
}