package com.cham.mytest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.cham.mytest.R
import com.cham.mytest.databinding.ItemCoupondialogBinding


/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/05/07
 *  @Description  :  ListAdapter   检测不同的 List
 *
 */
class RcyDialogAdapter :
    ListAdapter<String, BaseDataBindingHolder<ItemCoupondialogBinding>>(StringDiffCallback()) {


    override fun onBindViewHolder(
        holder: BaseDataBindingHolder<ItemCoupondialogBinding>,
        position: Int
    ) {
        holder.dataBinding?.tv1?.text = getItem(position)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDataBindingHolder<ItemCoupondialogBinding> {

        val binding = DataBindingUtil.inflate<ItemCoupondialogBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_coupondialog, parent, false
        )

        return BaseDataBindingHolder(binding.root)
    }

    /**
     * 静态内部类
     * */
    class StringDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}


