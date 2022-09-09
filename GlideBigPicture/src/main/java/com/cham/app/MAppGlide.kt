package com.cham.app

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * @Author:         Cham
 * @Description:     配置总体的 Glide 里面配置的东西都会覆盖原来的
 * @CreateDate:     2021/4/7 18:19
 * @UpdateUser:
 * @UpdateDate:     2021/4/7 18:19
 * @UpdateRemark:
 */
@GlideModule
class MAppGlide: AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setImageDecoderEnabledForBitmaps(true)


    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }

}