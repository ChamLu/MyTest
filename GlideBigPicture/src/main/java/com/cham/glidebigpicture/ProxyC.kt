package com.cham.glidebigpicture

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.contracts.InvocationKind

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/5/27 15:35
 * @UpdateUser:
 * @UpdateDate:     2021/5/27 15:35
 * @UpdateRemark:
 */
class ProxyC {


    class ProxyA : InvocationHandler{
        //proxy:目标类方法

        override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {

         return   method.invoke(args)
        }

    }

}