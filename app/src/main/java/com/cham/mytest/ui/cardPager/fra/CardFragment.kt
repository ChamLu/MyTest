package com.cham.mytest.ui.cardPager.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cham.mytest.R
import com.cham.mytest.ui.viewpager2.RvAdapter

/**
 * Created by liushaochen on 2019/3/4.
 */
class CardFragment : Fragment() {
    private var mView: View? = null
    private var recycler: RecyclerView? = null

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fm_card, null)

        // 初始化
        initView()
        return mView
    }

    /**
     * 初始化
     */
    private fun initView() {
        if (mView == null) {
            return
        }

        recycler = mView!!.findViewById(R.id.recycler)
        recycler!!.layoutManager = LinearLayoutManager(activity)
        recycler!!.itemAnimator = DefaultItemAnimator()


        recycler!!.adapter = mAdapter
        mAdapter.submitList(mList)
    }
}


