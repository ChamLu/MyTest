package com.cham.mytest.ui.rv

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cham.mytest.GlideApp
import com.cham.mytest.R
import com.cham.mytest.utils.logeMsg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import kotlin.coroutines.CoroutineContext

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/11/9 10:51
 * @UpdateUser:
 * @UpdateDate:     2021/11/9 10:51
 * @UpdateRemark:
 */
class RvIvAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rv_layoutmanager),
    LifecycleObserver,
    CoroutineScope {



    override fun convert(holder: BaseViewHolder, item: String) {

        GlideApp.with(context).load(item).diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(1080, 400)
            .into(holder.getView<ImageView>(R.id.iv2))

        logeMsg("convert ${holder.layoutPosition}项")

        holder.getView<ImageView>(R.id.iv2).setOnClickListener {

            Toast.makeText(context, "第${holder.layoutPosition}项目", Toast.LENGTH_SHORT).show()

        }

        if(holder.layoutPosition==3){
            setT(holder)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        logeMsg("创建第${super.onCreateViewHolder(parent, viewType).layoutPosition}项")
        return super.onCreateViewHolder(parent, viewType)

    }

    private fun setT(holder: BaseViewHolder){

    }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}