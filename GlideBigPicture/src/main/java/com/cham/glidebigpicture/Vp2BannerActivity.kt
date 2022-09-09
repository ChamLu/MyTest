package com.cham.glidebigpicture

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/6/7 14:59
 * @UpdateUser:
 * @UpdateDate:     2021/6/7 14:59
 * @UpdateRemark:
 */
class Vp2BannerActivity : AppCompatActivity() {


    companion object {
        fun startVp2BannerActivity(context: Context) {
            val intent=Intent(context,Vp2BannerActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}