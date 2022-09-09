package com.cham.mytest.ui.viewpager2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cham.mytest.R
import com.cham.mytest.databinding.FragmentListBinding
import com.cham.mytest.ui.viewpager2.RvAdapter
import com.cham.mytest.ui.viewpager2.vm.AtyViewModel
import com.cham.mytest.utils.logeMsg

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/8/21 20:49
 * @UpdateUser:
 * @UpdateDate:     2020/8/21 20:49
 * @UpdateRemark:
 */
class ListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

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

    private var isLoaded = false
    private val mAdapter by lazy {
        RvAdapter()
    }

    private lateinit var mBinding: FragmentListBinding


    private val mViewModel by activityViewModels<AtyViewModel>()

    companion object {
        private const val ARG_SECTION_NUMBER = "ListFragment"

        private const val TAG = "ListFragment"

        @JvmStatic
        fun newInstance(sectionNumber: Int): ListFragment {
            return ListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    private var isOnStart = false
    private var isOnResume = 0
    private val mInt by lazy {
        requireArguments().getInt(ARG_SECTION_NUMBER)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvList.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()
        isOnStart = true
        Log.e(TAG, "onStart: $mInt")
    }

    override fun onResume() {
        super.onResume()
        //方案1
//        isOnResume++
//        if(isOnResume==1){
//          //  Log.e(TAG, "onResume: $mInt"  + "次数 $isOnResume")
//            mAdapter.submitList(mList)
//
//            mViewModel.mTitle.observe(requireActivity(), Observer {
//                Log.e(TAG, "onResume: $mInt"  + "点击 $it")
//
//            })
//        }

        if (!isLoaded) {
            mBinding.swipe.setOnRefreshListener(this)
            mAdapter.submitList(mList)
            Log.e(TAG, "onResume: $mInt" + "次数 $isOnResume")
            isLoaded = true
        }

    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: $mInt")
    }

    override fun onRefresh() {
        mBinding.swipe.isRefreshing = false
        logeMsg(TAG, "下拉刷新")
        mAdapter.submitList(mList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }
}