package com.cham.mytest.ui.nestedscroll

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cham.mytest.databinding.ActivityNestedscrollBinding
import com.cham.mytest.ui.viewpager2.RvAdapter
import com.cham.mytest.utils.logeMsg

class NestedScrollViewActivity : AppCompatActivity() {

    private val mBinding by lazy {

        ActivityNestedscrollBinding.inflate(layoutInflater)
    }

    companion object {
        @JvmStatic
        fun nestedScrollViewActivity(context: Context) {
            val starter = Intent(context, NestedScrollViewActivity::class.java)
            context.startActivity(starter)
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
        "不吐不快"
    )

    private val mAdapter by lazy {
        RvAdapter()
    }
    private val mAdapter2 by lazy {
        RvAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        logeMsg("firstViewId " + mBinding.firstView.id)

        logeMsg("twoViewaId " + mBinding.twoViewa.id)


//
//        mBinding.rvList.apply {
//            adapter = mAdapter
//            setHasFixedSize(true)
//        }
//        mAdapter.submitList(mList)
//
//        mBinding.rvTwo.apply {
//            adapter = mAdapter2
//            setHasFixedSize(true)
//        }
//
//        mAdapter2.submitList(mList)
    }
}