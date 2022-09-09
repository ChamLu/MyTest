package com.cham.mytest.ui.motionlayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cham.mytest.databinding.ActivityTwoMotionBinding
import com.cham.mytest.ui.viewpager2.RvAdapter
import com.cham.mytest.utils.logeMsg
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2022/2/25 17:13
 * @UpdateUser:
 * @UpdateDate:     2022/2/25 17:13
 * @UpdateRemark:
 */
class TwoMotionActivity : AppCompatActivity() {

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
        "不吐不快",
        "有惊无险", "",
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
    )
    private val mAdapter by lazy {
        RvAdapter()
    }

    companion object {
        fun startTwoMotionActivity(context: Context) {
            val intent = Intent(context, TwoMotionActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val mBinding by lazy { ActivityTwoMotionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.rvList.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }
        mAdapter.submitList(mList)

        val listener = AppBarLayout.OnOffsetChangedListener { unused, verticalOffset ->
            val seekPosition = -verticalOffset / mBinding.applayout.totalScrollRange.toFloat()
            mBinding.mmmm.progress = seekPosition
        }

        mBinding.applayout.addOnOffsetChangedListener(listener)


        mBinding.ivFilter.setOnClickListener {
            logeMsg("${mBinding.rvList.recycledViewPool.getRecycledViewCount(1)}")

            logeMsg("Size: ${mBinding.rvList.recycledViewPool.getRecycledViewCount(1)}")

        }

        runBlocking {
            val m = launch {
                //不可取消 上下文不同了
                launch(Job()) {
                    logeMsg("A")
                    delay(1000)
                    logeMsg("B")
                }
                launch {
                    delay(100)
                    logeMsg("C")
                    delay(1000)
                    logeMsg("D")
                }
                val c = coroutineContext
                launch(c) {
                    delay(100)
                    logeMsg("E")
                    delay(1000)
                    logeMsg("F")
                }
                launch(IO) {

                    delay(100)
                    logeMsg("G")
                    delay(1000)
                    logeMsg("H")
                }
            }

            delay(500)
            m.cancelAndJoin()

            delay(1000)

        }


    }

}