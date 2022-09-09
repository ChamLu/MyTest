package com.cham.mytest.ui.testrv

import android.widget.BaseAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cham.mytest.R
import com.cham.mytest.databinding.ItemCoupondialogBinding

/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/05/09
 *  @Description  :
 *
 */
class TestRvAdapter :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_coupondialog){
    override fun convert(helper: BaseViewHolder, item: String) {

        helper.setText(R.id.tv_1,item)

    }
}