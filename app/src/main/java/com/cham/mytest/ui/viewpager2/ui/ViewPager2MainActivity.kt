package com.cham.mytest.ui.viewpager2.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cham.mytest.R
import com.cham.mytest.databinding.ActivityViewpager2mainBinding
import com.cham.mytest.ui.viewpager2.vm.AtyViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/8/21 17:41
 * @UpdateUser:
 * @UpdateDate:     2020/8/21 17:41
 * @UpdateRemark:
 */
class ViewPager2MainActivity : AppCompatActivity(),View.OnClickListener,AppBarLayout.OnOffsetChangedListener {

    private lateinit var mBinding:ActivityViewpager2mainBinding

    private val mViewModel by viewModels<AtyViewModel>()

    private val tabList  = listOf(
        "晴天", "Mojito", "一路向北", "七里香", "我是一只鱼", "伤心太平洋", "离开真的残酷吗", "活着的人",
        "真的无所谓", "风言风语", "风吹沙", "一波还未平息", "我等的船还不来", "当你", "爱你", "月光", "眯着", "眼睛", "笑"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_viewpager2main)
        mBinding.lifecycleOwner=this
        mBinding.onclick=this

        mBinding.tab.tabMode = TabLayout.MODE_AUTO

        mBinding.vp2.adapter =object : FragmentStateAdapter(this){

            override fun getItemCount(): Int =tabList.size

            override fun createFragment(position: Int): Fragment {
                return ListFragment.newInstance(position)
            }
        }

        mBinding.mAppbar.addOnOffsetChangedListener(this)


        TabLayoutMediator(mBinding.tab, mBinding.vp2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabList[position]
            }).attach()






    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv1 -> {
                mViewModel.mTitle.value = 1
            }
            R.id.tv2 -> {
                mViewModel.mTitle.value = 2
            }
            R.id.tv3 -> {
                mViewModel.mTitle.value = 3
            }
            R.id.tv4 -> {
                mViewModel.mTitle.value = 4
            }


        }

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

        val percent = abs(verticalOffset).toFloat() /(appBarLayout!!.totalScrollRange).toFloat()


        mBinding.mToolbar.alpha =percent

       // logeMesg(message = "$percent")






    }

}