package com.cham.mytest.ui.stack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/11/3 11:01
 * @UpdateUser:
 * @UpdateDate:     2020/11/3 11:01
 * @UpdateRemark:
 */


/**StackTrace简述
 * 1 StackTrace用栈的形式保存了方法的调用信息.
 * 2 怎么获取这些调用信息呢?
 *   可用Thread.currentThread().getStackTrace()方法
 *   得到当前线程的StackTrace信息.
 *   该方法返回的是一个StackTraceElement数组.
 * 3 该StackTraceElement数组就是StackTrace中的内容.
 * 4 遍历该StackTraceElement数组.就可以看到方法间的调用流程.
 *   比如线程中methodA调用了methodB那么methodA先入栈methodB再入栈.
 * 5 在StackTraceElement数组下标为2的元素中保存了当前方法的所属文件名,当前方法所属
 *   的类名,以及该方法的名字.除此以外还可以获取方法调用的行数.
 * 6 在StackTraceElement数组下标为3的元素中保存了当前方法的调用者的信息和它调用
 *   时的代码行数.
 *
 *
 *
 *   1 methodA()调用methodB()
 *   methodB()调用methodC()
 *   2 在methodC()中获取StackTrace中的内容并遍历StackTraceElement数组
 *   这样就能观察到从开始到现在的方法间调用流程.
 *   在该流程中可以观察到:
 *   StackTraceElement数组下标为2的元素中保存了当前方法的所属文件名,
 *   当前方法所属的类名,以及该方法的名字.
 *   除此以外还可以利用stackTraceElement.getLineNumber()获取调用getStackTrace()方法的行数.
 *
 *   在StackTraceElement数组下标为3的元素中保存了当前方法的调用者的信息.
 *   并且可以还可以利用stackTraceElement.getLineNumber()获取到调用时的代码行数.
 *   注意此时获取到的不再是调用getStackTrace()方法的行数.
 * 3 methodC()调用methodD()
 *   在methodD()中获取StackTraceElement数组下标为2和3的元素信息.
 *   这两个元素包含了对于代码调试的重要信息.所以在此单独获取查看.
 *   */
class StackTraceElementActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}