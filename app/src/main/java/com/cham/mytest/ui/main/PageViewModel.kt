package com.cham.mytest.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.cham.mytest.utils.logeMsg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class PageViewModel : ViewModel() {

    private val TAG = "PageViewModel"
    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    init {
        /**
        * 不关联声明周期，永远在接收，并且手动接触
        * */
        _index.observeForever {
            logeMsg(message = "observeForever:$it")
        }

        _index.removeObserver {
            logeMsg(message = "removeObserver:$it")

        }
    }

    /**
     * 它始终是有值的
     * 它的值是唯一的
     * 它允许被多个观察者共用 (因此是共享的数据流)
     * 它永远只会把最新的值重现给订阅者，这与活跃观察者的数量是无关的。
     *
     *
     * 当 View 进入 STOPPED 状态时，LiveData.observe() 会自动取消注册使用方，
     * 而从 StateFlow 或任何其他数据流收集数据的操作并不会自动停止。如需实现相同的行为，您需要从 Lifecycle.repeatOnLifecycle 块收集数据流。
     *
     * StateFlow只有在值发生改变时才会返回，如果发生更新但值没有变化时
     * */
    var m1StateFlow = MutableStateFlow(Random.nextInt(10))


    var isLazyInit = true

    var mSflow :Flow<Int> = flow {
        val s=  Random.nextInt(10)
        logeMsg("每次重新发射？")
        emit(s)
    }

    fun sentValue() {
      val s=  Random.nextInt(10)
        m1StateFlow.value = s
    }

    fun onTest() {
        //超时抛出异常
        viewModelScope.launch {
            try {
                Log.e(TAG, Thread.currentThread().name)
                withTimeout(5000L) {
                    delay(3000L)
                    Log.e(TAG, "delay")
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
            } finally {
                Log.e(TAG, "finally")
            }

        }

    }


    fun countDown() {
        viewModelScope.launch {

            var s = parseDate(
                "2020-03-23 19:09:35", "yyyy-MM-dd HH:mm:ss",
                TimeZone.getTimeZone("Asia/Shanghai")
            ).time

            var s2 = parseDate(
                "2020-03-23 19:39:23 ", "yyyy-MM-dd HH:mm:ss",
                TimeZone.getTimeZone("Asia/Shanghai")
            ).time

            var s3 = s2 - s

            Log.e(TAG, "---1: $s")
            Log.e(TAG, "---1:" + timeParse(s3 / 1000L))

            val sdf = SimpleDateFormat("HH:mm:ss")
            val date1 = sdf.parse("19:09:35")
            val date2 = sdf.parse("19:19:35")

            val date = date2.time - date1.time

            Log.e(TAG, "---2: $date")

            Log.e(TAG, "---2:" + timeParse(date / 1000L))


        }
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    /**
     * 对格式化的日期转换成Date
     * @param formatDate
     * @param regular 格式 [JavaDoc 常量][SimpleDateFormat]
     * @return
     */
    private fun parseDate(
        formatDate: String?,
        regular: String?,
        timeZone: TimeZone?
    ): Date {
        var date = Date()
        val df: DateFormat = SimpleDateFormat(regular)
        df.timeZone = timeZone
        try {
            date = df.parse(formatDate)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return date
        }
    }

    private fun timeParse(duration: Long): String {
        var minute: String = (duration / 60).toString()
        var seconds: String = (duration % 60).toString()
        if (minute.length < 2) {
            minute = "0$minute"
        } else if (minute.length > 3) {
            minute = "60"
        }
        if (seconds.length < 2) {
            seconds = "0$seconds"
        }
        return "$minute:$seconds"
    }
}