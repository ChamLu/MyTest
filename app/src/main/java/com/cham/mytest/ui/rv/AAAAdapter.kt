package com.cham.mytest.ui.rv

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cham.mytest.GlideApp

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/12/10 14:30
 * @UpdateUser:
 * @UpdateDate:     2021/12/10 14:30
 * @UpdateRemark:
 */
 class AAAAdapter(val context:Context): RecyclerView.Adapter<AAAAdapter.NormalHolder>() {

    private var urlList: List<String>? = null

    public fun setData(s: List<String>){
        urlList=s
    }

    class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bannerItem: ImageView

        init {
            bannerItem = itemView as ImageView
            val params: RecyclerView.LayoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            bannerItem.setLayoutParams(params)
            bannerItem.setScaleType(ImageView.ScaleType.FIT_XY)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalHolder {
      return   NormalHolder( ImageView(context))
    }

    override fun onBindViewHolder(holder: NormalHolder, position: Int) {

        if(urlList!=null){
            val url = urlList!![position % urlList!!.size]
            val img = holder.itemView as ImageView
            GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(1080, 400).into(img)
        }
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE;
    }
}