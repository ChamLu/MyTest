package com.cham.mytest.ui.cardPager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
import com.cham.mytest.databinding.ActivityCardPagerBinding
import com.cham.mytest.ui.cardPager.fra.CardFragment
import com.cham.mytest.utils.logeMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/11/15 15:28
 * @UpdateUser:
 * @UpdateDate:     2021/11/15 15:28
 * @UpdateRemark:
 */
class CardPagerActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCardPagerBinding

    private lateinit  var mUtilAnim: UtilShowAnim
    private lateinit var  mAdapter: CardViewPagerAdapter
    /**
     * 数据源
     */
    private lateinit var mList: MutableList<CardFragment>

    /**
     * 切换动画
     */
    private lateinit var  mTransformer: CardTransformer
    companion object {
        fun startCardPagerActivity(context: Context) {
            val intent = Intent(context, CardPagerActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCardPagerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mUtilAnim =UtilShowAnim(mBinding.vpCard)

        mUtilAnim.cardAnim(UtilShowAnim.ANIM_TYPE_UNFOLD)
        mList = mutableListOf()
        mTransformer=CardTransformer()
        mBinding.vpCard.setPageTransformer( mTransformer)
        // 设置切换动画为 层叠效果，并获取预加载数量
        val offscreen = mTransformer.setTransformerType(CardTransformer.ANIM_TYPE_STACK)
        // 设置ViewPager的预加载数量
        mBinding.vpCard.offscreenPageLimit = offscreen
        initData()

    }

    var job : Job?=null
    private fun initData(){

        for (i in 0..10){
            val fragment1 = CardFragment()
            mList.add(fragment1)
        }


        mAdapter= CardViewPagerAdapter(this,mList)

        mBinding.vpCard.adapter=mAdapter

        mBinding.btn1.setOnClickListener {
            job=   lifecycle.coroutineScope.launch {
                try {
                    flow {
                        for (i in 20 downTo 1) {
                            delay(1000L)
                            emit(i)
                        }
                    }.flowOn(Dispatchers.Main)
                        .onStart {

                        }
                        .onCompletion {
                            logeMsg("倒计时 onCompletion")
                        }.collect {
                            logeMsg("倒计时 $it")

                        }
                } catch (e: Throwable) {
                    logeMsg("倒计时 catch:  " + e.message)
                } finally {
                    logeMsg("倒计时 finally")
                }

            }

        }

        mBinding.btn2.setOnClickListener {
            job?.cancel()
        }




    }
}