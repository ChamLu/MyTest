package com.cham.service

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.datastore.preferences.core.edit
import com.cham.app.MApp
import com.cham.datastore.DataStoreManager.NETEASE_COUNT
import com.cham.datastore.DataStoreManager.dataStore
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2022/1/19 14:11
 * @UpdateUser:
 * @UpdateDate:     2022/1/19 14:11
 * @UpdateRemark:  accessibilityEventTypes  监听的事件类型，例如：typeAllMask 表示全部事件，而 typeViewClicked 表示只监听点击事件
 *                 accessibilityFeedbackType：监听事件的反馈模式。
 *                 canRetrieveWindowContent：是否允许获取视图层级的访问权，如果它被设置为 false，node.getSource() 方法会调用失败。
 *                 accessibilityFlags：指定 Flag，一般用于指定根据 Node 获取 View ID 的权限。
 *                 packageNames：开启监听的应用包名，可以指定多个包名，通过逗号“,”分割，不设置此属性标识全局监听。
 *                 description：辅助功能的描述，它会显示在系统设置的“无障碍”中的描述信息中。
 *                 notificationTimeout：响应的毫秒数。
 */
class IsOkService : AccessibilityService() {
    companion object {
        const val TAG = "IsOkService"
    }

    //网易云启动页
    // com.netease.cloudmusic.activity.LoadingActivity

    //com.netease.cloudmusic.activity.MainActivity

    private var mJob: Job? = null


    private var netCounterFlow: Flow<Int> = dataStore.data.map {
        it[NETEASE_COUNT] ?: 0
    }

    var netEaseCount = 0
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val sourceNodeInfo = rootInActiveWindow ?: return
        event?.let { event ->
            val packageName = event.packageName.toString()
            val eventType = event.eventType
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                //窗口改变
                Log.e(TAG, " packageName = $packageName")

                //针对网易云音乐
                if (packageName.contains("com.netease.cloudmusic")) {

                    MainScope().launch {
                        if (netCounterFlow.first() == 0) {
                           MApp.get().dataStore.edit { data ->
                                data[NETEASE_COUNT] = 1
                            }

                            val s1 =
                                sourceNodeInfo.findAccessibilityNodeInfosByViewId("com.netease.cloudmusic:id/portal_rv")


                            if (s1.isNotEmpty()) {
                                for (s in s1) {
                                    Log.e(TAG, "onAccessibilityEvent: " + s.childCount)

                                    //私人FM
                                    val s2 = s.getChild(1)
                                    if (s2.isClickable) {
                                        withContext(Main) {
                                            s2.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                        }


                                    }
                                }
                            }

                        }

                    }

                } else {

                    ACCESSIBILITY_SERVICE
                    Log.e(TAG, " packageName2 = $packageName")
                    MainScope().launch {
                        withContext(Main) {
                            MApp.get().dataStore.edit {
                                it[NETEASE_COUNT] = 0
                            }
                            val ssss = netCounterFlow.first()

                            Log.e(TAG, "onAccessibilityEvent: $ssss")

                        }

                    }

                }


            }

        }


    }

    override fun onInterrupt() {

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}