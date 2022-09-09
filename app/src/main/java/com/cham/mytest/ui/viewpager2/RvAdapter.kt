package com.cham.mytest.ui.viewpager2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.cham.mytest.R
import com.cham.mytest.databinding.ItemVp2RvBinding
import com.cham.mytest.utils.logeMsg

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/8/23 18:05
 * @UpdateUser:
 * @UpdateDate:     2020/8/23 18:05
 * @UpdateRemark:
 */
class RvAdapter : ListAdapter<String, BaseDataBindingHolder<ItemVp2RvBinding>>(
    StringDiffCallback()
) {

    val img = listOf(
        R.drawable.ic_av1,
        R.drawable.ic_av2,
        R.drawable.ic_av3,
        R.drawable.ic_av4,
        R.drawable.ic_av5
    )

    var createInt =0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDataBindingHolder<ItemVp2RvBinding> {
        val binding = DataBindingUtil.inflate<ItemVp2RvBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_vp2_rv, parent, false
        )
        logeMsg("onCreateViewHolder : ${createInt++}" )
        return BaseDataBindingHolder(binding.root)
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }



    override fun onBindViewHolder(holder: BaseDataBindingHolder<ItemVp2RvBinding>, position: Int) {

        val randoms = (0..4).random()
        holder.dataBinding?.let {
            it.iv1.setImageResource(img[randoms])

            it.tvNum.text = "$position"

            it.tv1.text = getItem(position)
        }
        logeMsg("onBindViewHolder : "+position )

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