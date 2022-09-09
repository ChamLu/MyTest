package com.cham.mytest.ui.applayoutbar_ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cham.mytest.R
import com.cham.mytest.databinding.ActivtiyApplayoutbarBinding
import com.cham.mytest.ui.viewpager2.RvAdapter

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/1/8 17:17
 * @UpdateUser:
 * @UpdateDate:     2021/1/8 17:17
 * @UpdateRemark:
 */
class AppLAyoutBarActivity : AppCompatActivity() {


   companion object {
       fun startAppLAyoutBarActivity(context: Context) {
           val intent = Intent(context, AppLAyoutBarActivity::class.java)
           context.startActivity(intent)

       }
   }

    private var mList = mutableListOf<String>(
        "我是一只鱼",
        "伤心太平洋",
        "离开真的残酷吗？",
        "活着的人",
        "真的无所谓",
        "风言风语",
        "风吹沙",
        "一波还未平息",
        "我等的船还不来",
        "不吐不快",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来",
        "我等的船还不来"
    )
    private val mAdapter by lazy {
        RvAdapter()
    }

    private lateinit var mBinding: ActivtiyApplayoutbarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this, R.layout.activtiy_applayoutbar)

        mBinding.rvList.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }
        mAdapter.submitList(mList)

    }
}