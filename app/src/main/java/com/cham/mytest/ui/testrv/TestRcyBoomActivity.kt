package com.cham.mytest.ui.testrv

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cham.mytest.R
import com.cham.mytest.databinding.ActivityTestrcyboomBinding
import com.cham.mytest.ui.main.RcyDialogAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/05/09
 *  @Description  :
 *
 */
class TestRcyBoomActivity :AppCompatActivity() {

    private  lateinit var mBinding:ActivityTestrcyboomBinding

    private val TAG ="TestRcyBoomActivity"
    private var isFirst =false
    private val mAdapter by lazy {
        TestRvAdapter()
    }
    private var mList = mutableListOf<String>(
        "我是一只鱼",
        "伤心太平洋",
        "离开真的残酷吗？",
        "活着的人",
        "真的无所谓",
        "风言风语",
        "风吹沙",
        "一波还未平息",
        "我等的船还不来",
        "不吐不快"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=   DataBindingUtil.setContentView(this, R.layout.activity_testrcyboom)
        mBinding.lifecycleOwner=this
        isFirst =true
        Log.e(TAG, "onCreate: "+getProcessName() )
    }

    override fun onResume() {
        super.onResume()
        if(isFirst){
            mAdapter.addData(mList)
            mBinding.rcyTestBoom.setHasFixedSize(true)
            mBinding.rcyTestBoom.layoutManager = SpeedLinLayoutManage(this)
        //    mBinding.rcyTestBoom.layoutManager =LinearLayoutManager(this)
            mBinding.rcyTestBoom.adapter =mAdapter

            mBinding.btn6.setOnClickListener {
               testSend()
            }
            mBinding.rcyTestBoom.addOnScrollListener(object:RecyclerView.OnScrollListener(){

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                 //   super.onScrollStateChanged(recyclerView, newState)

                    if( !recyclerView.canScrollVertically(1) ){
                        //已经滚动到底部了
                     Log.e(TAG,"滑动到底了---1 ")
                    }

                    // 0 --静止没有滚动
                    //1 --正在被外部拖拽,一般为用户正在用手指滚动
                    //2 --自动滚动
                    Log.e(TAG," 状态 ：  $newState  ")
//                    val lastPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//                    if( lastPosition == mAdapter.itemCount - 1){
//                        Log.e(TAG,"滑动到底了---2 ")
//                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    Log.e(TAG," 距离 ：  $dy  ")
                }
            })


        }


    }

    private  fun testSend(){
        mBinding.rcyTestBoom.smoothScrollToPosition(0)
//        lifecycleScope.launch {
//            withContext(Dispatchers.Main) {
//                for (i in 0..10000) {
//                    delay(200)
//                    mAdapter.addData("****$i *****")
//                    mBinding.rcyTestBoom.smoothScrollToPosition(mAdapter.itemCount-1)
//                }
//            }
//
//
//        }
    }

    fun getProcessName(): String {

        val pid = android.os.Process.myPid()//获取进程pid
        var processName = ""
        val am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager//获取系统的ActivityManager服务
        for (appProcess in am.runningAppProcesses) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName
                break
            }
        }

        return processName
    }


}