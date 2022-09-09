package com.cham.lib_annotations

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/3/9 17:23
 * @UpdateUser:
 * @UpdateDate:     2021/3/9 17:23
 * @UpdateRemark:
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class BindLayout(val value:Int = -1)
