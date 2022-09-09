package com.cham.mytest.ui.main

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.cham.mytest.R
import com.cham.mytest.databinding.DailogRcyBinding


/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/05/07
 *  @Description  :   RecyclerView 的Dialog
 *
 */
class RecyclerViewDialog : DialogFragment() {

    private val TAG = "RecyclerViewDialog"
    private val mAdapter by lazy {
        RcyDialogAdapter()
    }
    private lateinit var mBinding: DailogRcyBinding

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
//        "我是一只鱼",
//        "伤心太平洋",
//        "离开真的残酷吗？",
//        "活着的人",
//        "真的无所谓",
//        "风言风语",
//        "风吹沙",
//        "一波还未平息",
//        "我等的船还不来",
        "baby"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(false)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dailog_rcy, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.rcyMax.adapter = mAdapter
        mAdapter.submitList(mList)
        mBinding.tvRefresh.setOnClickListener {
            mAdapter.submitList(mList2)
        }
    }

    override fun onStart() {
        super.onStart()
        setDialogParams()
    }

    private fun setDialogParams() {
        dialog?.let { it ->
            val window: Window? = it.window
            window?.let { window ->
                //刷新的时候 宽度才不会变
                val dm = DisplayMetrics();
                requireActivity().windowManager.defaultDisplay.getMetrics(dm)
                //控制两边的宽度
                window.setLayout(
                    (dm.widthPixels * 1).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val params = window.attributes

                params.dimAmount = 0.6f
                params.gravity = Gravity.CENTER
                window.attributes = params
            }
        }
    }


    /**
     * dialog取消监听
     * */
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Log.e(TAG, "dialog取消了")
    }
}