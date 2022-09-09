package com.cham.mytest.ui.bottomsheet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cham.mytest.R
import com.cham.mytest.databinding.DialogRcyVpBinding
import com.cham.mytest.ui.main.listene.onSrcoll
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout


/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/05/07
 *  @Description  :   做个滑动的 ViewPage Dialog
 *
 */
class BottomsheetVp2Dialog : BottomSheetDialogFragment() {

    private val TAG = "BottomsheetVp2Dialog"

    private lateinit var mBinding: DialogRcyVpBinding

    private var mTabList = mutableListOf<String>(
        "一只鱼",
        "太平洋",
        "残酷",
        "活着"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_rcy_vp, container, false)
        return mBinding.root
    }

    public lateinit var bottomSheetbehavior: BottomSheetBehavior<FrameLayout>
    override fun onStart() {
        super.onStart()
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        bottomSheetbehavior =(dialog as BottomSheetDialog).behavior

        bottomSheetbehavior.setPeekHeight(1000,true)


        bottomSheetbehavior.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
             //   Log.e(TAG,"slideOffset---  $slideOffset")
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            //    Log.e(TAG,"newState---  $newState")
            }

        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTabList.forEach {
            mBinding.tab.addTab(mBinding.tab.newTab().setText(it))
        }

        mBinding.vp2.registerOnPageChangeCallback(onPageChangeCallback)
        val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.apply {
                    mBinding.vp2.setCurrentItem(position, true)
                }
            }
        }
        mBinding.tab.addOnTabSelectedListener(onTabSelectedListener)

        // mBinding.tab.setupWithViewPager(mBinding.vp1)


        mBinding.vp2.adapter = object : FragmentStateAdapter(requireActivity()) {
            override fun getItemCount(): Int = mTabList.size
            override fun createFragment(position: Int): Fragment {
                val fragment =Vp2Fragment.newInstance(position)
                fragment.setOnSrcolled(object :
                    onSrcoll {
                    override fun rcyOnScrolled(dy: Int,recyclerView: RecyclerView) {
                        when{
                            dy>10 &&   recyclerView.canScrollVertically(-1)->{
                                //表示是否能向上滚动,false表示已经滚动到底部
                          //      recyclerView.canScrollVertically(1)
                                if( bottomSheetbehavior.state!=3){
                                    bottomSheetbehavior.state= BottomSheetBehavior.STATE_EXPANDED
                                }
                            }
                            //滑动到顶部
                            dy< -10 &&   recyclerView.canScrollVertically(1)&&  recyclerView.computeVerticalScrollOffset()==0 ->{
                                //表示是否能向下滚动，false表示已经滚动到顶部
                            //    recyclerView.canScrollVertically(-1)
                                if( bottomSheetbehavior.state!=4){
                                    bottomSheetbehavior.state= BottomSheetBehavior.STATE_COLLAPSED
                                }
                            }

                        }

                    }

                })
                return fragment

            }
        }


        mBinding.vp1.adapter = object :
            FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                val fragment =Vp2Fragment.newInstance(position)
                   fragment.setOnSrcolled(object :
                       onSrcoll {
                    override fun rcyOnScrolled(dy: Int,r: RecyclerView) {
                        when{
                            dy>10 ->{
                                //表示是否能向上滚动,false表示已经滚动到底部
                                r.canScrollVertically(1)
                                if( bottomSheetbehavior.state!=3){
                                    bottomSheetbehavior.state= BottomSheetBehavior.STATE_EXPANDED
                                }
                            }
                            dy< -10 ->{
                                //表示是否能向下滚动，false表示已经滚动到顶部
                                r.canScrollVertically(-1)
                                if( bottomSheetbehavior.state!=4){
                                    bottomSheetbehavior.state= BottomSheetBehavior.STATE_COLLAPSED
                                }
                            }

                        }

                    }

                })

                return fragment
            }

            override fun getCount(): Int = mTabList.size

            override fun getPageTitle(position: Int): CharSequence? {
                return mTabList[position]
            }

        }
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            mBinding.tab.setScrollPosition(
                position, positionOffset,
                true, true
            )
        }

        override fun onPageSelected(position: Int) {
            mBinding.tab.selectTab(mBinding.tab.getTabAt(position))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("BottomsheetVp2Dialog", "销毁")
        mBinding.vp2.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

}