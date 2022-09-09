package com.cham.mytest.ui.testrv

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.cham.mytest.R
import com.cham.mytest.databinding.ActivityBtoBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/6/30 20:40
 * @UpdateUser:
 * @UpdateDate:     2020/6/30 20:40
 * @UpdateRemark:
 */
class TestBtoActivity : AppCompatActivity() {

    companion object {
        var TAG = TestBtoActivity::class.java.simpleName
    }

    private lateinit var s: Job

    private lateinit var mBinding: ActivityBtoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bto)
        mBinding.btn1.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().putExtra("value", "I am back !"))
            finish()
        }
        val s2 =   SealedBean()
        mBinding.btn2.setOnClickListener {
            sssssss()
        }
        mBinding.btn3.setOnClickListener {
            s.cancel()
        }
        mBinding.btn4.setOnClickListener {
         startActivity(Intent(this,TestAtoActivity::class.java))
        }
        mBinding.btn5.setOnClickListener {
           s2.setLifecycleB(this)

        }
        mBinding.btn6.setOnClickListener {
            gotoShop(this,"taobao://shop.m.taobao.com/shop/shop_index.htm?shop_id=185441148")
        }
    }

    /**
     * 跳转至商铺
     *
     * @param activity Activity
     * @param url      商铺地址
     */
    fun gotoShop(activity: Activity, url: String?) {
        try {
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(url)
            activity.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun sssssss() {
      runCatching {
       s=lifecycleScope.launchWhenResumed {
              flow<Int>() {
                  while (true){
                      delay(1000)
                      emit(1)
                  }
              }.flowOn(IO)
                  .collect {
                      Log.e(TAG, "s2: $it   " + Thread.currentThread().name)
                  }
          }

        }.onFailure {
          Log.e(TAG, "错误: " + it.message )

      }


        //   lifecycleScope.launchWhenResumed {
//            Log.e(TAG, "sssssss: ")
//            flow<Int> (){
//                Int.MAX_VALUE.downTo(0).forEach {
//                    delay(3000)
//                    emit(it)
//                }
//            }.flowOn(Main)
//                .collect {
//                    Log.e(TAG, "s2: $it   " + Thread.currentThread().name)
//                }
//
//        }
//


    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: " )
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: " )
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_FIRST_USER, Intent().putExtra("value", "onBackPressed!"))
        finish()

    }
}