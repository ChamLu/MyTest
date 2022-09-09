package com.cham.mytest.ui.eventDispatch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cham.mytest.R
import com.cham.mytest.databinding.ActivityEventBinding
import com.cham.mytest.utils.logeMsg

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/10/29 15:10
 * @UpdateUser:
 * @UpdateDate:     2021/10/29 15:10
 * @UpdateRemark:
 */
class EventActivity : AppCompatActivity() {

    companion object {
        fun startEventActivity(context: Context) {
            val intent= Intent(context,EventActivity::class.java)
            context.startActivity(intent)
        }
    }
    private lateinit var mBinding: ActivityEventBinding

    val TAG ="EventActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> logeMsg("onTouchEvent ACTION_DOWN",TAG)
            MotionEvent.ACTION_MOVE -> logeMsg("onTouchEvent ACTION_MOVE",TAG)
            MotionEvent.ACTION_UP -> logeMsg("onTouchEvent ACTION_UP",TAG)
        }
        val flag = super.onTouchEvent(event)
        logeMsg("onTouchEvent return: $flag",TAG)

        return flag
    }

}