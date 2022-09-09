package com.cham.marquee

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.button.MaterialButton

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/3/4 10:34
 * @UpdateUser:
 * @UpdateDate:     2021/3/4 10:34
 * @UpdateRemark:
 */
class ButterKnifeActivity : AppCompatActivity() {

//    @BindView(R.id.btn_a)
//    lateinit var   btn1: MaterialButton
//
//    @BindView(R.id.btn_n)
//    lateinit var   btn2: MaterialButton
//
//    @BindView(R.id.tv_title)
//    lateinit var tvTitle:AppCompatTextView

    @BindView(R.id.btn_a)
    lateinit var btn1: MaterialButton

    @BindView(R.id.btn_n)
    lateinit var btn2: MaterialButton

    @BindView(R.id.btn_go)
    lateinit var btn3: MaterialButton

    @BindView(R.id.tv_title)
    lateinit var tvTitle1: AppCompatTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_butterknife)


        ButterKnife.bind(this)


        val sourceView: View = this.window.decorView


        Log.e("TAG", "View Id: " + sourceView.id)



        btn1.setOnClickListener {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        }

        btn2.setOnClickListener {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        }

        btn3.setOnClickListener {

            TestProcessorActivity.startTestProcessorActivity(this)
        }

    }

}