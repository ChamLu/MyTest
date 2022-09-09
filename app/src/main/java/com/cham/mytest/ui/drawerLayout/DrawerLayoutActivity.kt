package com.cham.mytest.ui.drawerLayout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cham.mytest.R
import com.cham.mytest.databinding.ActivityDrawerlayoutBinding
import com.cham.mytest.ui.viewpager2.RvAdapter

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/9/4 15:05
 * @UpdateUser:
 * @UpdateDate:     2020/9/4 15:05
 * @UpdateRemark:
 */
class DrawerLayoutActivity : AppCompatActivity(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener,DrawerLayout.DrawerListener {


    private lateinit var mBinding: ActivityDrawerlayoutBinding

    private val mAdapter by lazy {
        RvAdapter()
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_drawerlayout)
        mBinding.onclick=this
        mBinding.swipe.setOnRefreshListener(this)
        mBinding.rvList.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }
        mAdapter.submitList(mList)

        mBinding.drawerlayout.addDrawerListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv1 -> {

            }
            R.id.tv2 -> {
            }
            R.id.tv3 -> {

            }
            R.id.tv4 -> {
                mBinding.drawerlayout.openDrawer(  GravityCompat.END)

            }

        }
    }

    override fun onRefresh() {
        mBinding.swipe.isRefreshing = false
        mAdapter.submitList(mList)
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

    }

    override fun onDrawerOpened(drawerView: View) {

        //打开
    }

    override fun onDrawerClosed(drawerView: View) {

        //关闭
    }

    override fun onDrawerStateChanged(newState: Int) {

        //0  结束
    }

}