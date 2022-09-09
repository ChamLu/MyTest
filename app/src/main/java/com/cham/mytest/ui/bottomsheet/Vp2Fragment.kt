package com.cham.mytest.ui.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cham.mytest.R
import com.cham.mytest.databinding.FragmentRcyBinding
import com.cham.mytest.ui.main.listene.onSrcoll
import com.cham.mytest.ui.main.RcyDialogAdapter

/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/05/07
 *  @Description  :
 *
 */
class Vp2Fragment : Fragment() {

    private val TAG = "Vp2Fragment"

    companion object {
        private const val ARG_SECTION_NUMBER = "LifeServicesOrderFragment"

        @JvmStatic
        fun newInstance(sectionNumber: Int): Vp2Fragment {
            return Vp2Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    private lateinit var mBinding: FragmentRcyBinding
    private val mAdapter by lazy {
        RcyDialogAdapter()
    }
    private lateinit var mViewModel: Vp2ViewModel
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

    private var mList2 = mutableListOf<String>(
        "我是一只鱼"
    )

    private lateinit var mListener: onSrcoll


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rcy, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(Vp2ViewModel::class.java)
        initView()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initView() {
        mBinding.fragmentRcy.adapter = mAdapter
        Log.e("Vp2Fragment", "加载第${requireArguments().getInt(ARG_SECTION_NUMBER)} 页")
        when (requireArguments().getInt(ARG_SECTION_NUMBER)) {
            0 -> {
                mAdapter.submitList(mList)
            }
            1 -> {

                mAdapter.submitList(mList2)
            }
            2 -> {
                for (i in 0..requireArguments().getInt(ARG_SECTION_NUMBER)) {
                    mList.add("第 $i")
                }
                mAdapter.submitList(mList)
            }
            else -> {
                for (i in 0..20) {
                    mList.add("第 $i")
                }
                mAdapter.submitList(mList)
            }
        }

        //滑动监听
        mBinding.fragmentRcy.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mListener.rcyOnScrolled(dy, mBinding.fragmentRcy)


            }
        })

    }

    fun setOnSrcolled(li: onSrcoll) {
        mListener = li
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("Vp2Fragment", "销毁第${requireArguments().getInt(ARG_SECTION_NUMBER)} 页")
    }
}