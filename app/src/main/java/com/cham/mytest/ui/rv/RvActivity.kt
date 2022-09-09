package com.cham.mytest.ui.rv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import com.cham.mytest.databinding.ActivityRvBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/11/8 14:50
 * @UpdateUser:
 * @UpdateDate:     2021/11/8 14:50
 * @UpdateRemark:
 */
class RvActivity : AppCompatActivity() {


    companion object {
        fun startRv(context: Context) {
            val intent = Intent(context, RvActivity::class.java)
            context.startActivity(intent)
        }

        val listImg = listOf<String>(
            "https://b.zhutix.com/bizhi/win11-wp/01.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/03.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/07.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/16.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/17.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/18.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/19.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/20.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/21.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/23.jpg",

//            "https://b.zhutix.com/bizhi/win11-wp/25.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/26.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/27.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/28.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/29.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/01.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/02.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/03.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/04.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/05.jpg",

//            "https://b.zhutix.com/bizhi/colorful-gradient/08.jpg",
//            "https://b.zhutix.com/bizhi/Surface-Collection/02.jpg",
//            "https://b.zhutix.com/bizhi/Surface-Collection/04.jpg",
//            "https://b.zhutix.com/bizhi/Surface-Collection/05.jpg",
//            "https://b.zhutix.com/bizhi/Surface-Collection/06.jpg",
//            "https://b.zhutix.com/bizhi/kimi/01.jpg",
//            "https://b.zhutix.com/bizhi/kimi/02.jpg",
//            "https://b.zhutix.com/bizhi/kimi/10.jpg",
            "https://b.zhutix.com/bizhi/kimi/11.jpg"
        )
        val listImg2 = listOf<String>(
//            "https://b.zhutix.com/bizhi/win11-wp/01.jpg",
            "https://b.zhutix.com/bizhi/win11-wp/03.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/07.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/16.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/17.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/18.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/19.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/20.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/21.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/23.jpg",

//            "https://b.zhutix.com/bizhi/win11-wp/25.jpg",
            "https://b.zhutix.com/bizhi/win11-wp/26.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/27.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/28.jpg",
//            "https://b.zhutix.com/bizhi/win11-wp/29.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/01.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/02.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/03.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/04.jpg",
//            "https://b.zhutix.com/bizhi/colorful-gradient/05.jpg",

//            "https://b.zhutix.com/bizhi/colorful-gradient/08.jpg",
            "https://b.zhutix.com/bizhi/Surface-Collection/02.jpg",
//            "https://b.zhutix.com/bizhi/Surface-Collection/04.jpg",
//            "https://b.zhutix.com/bizhi/Surface-Collection/05.jpg",
//            "https://b.zhutix.com/bizhi/Surface-Collection/06.jpg",
            "https://b.zhutix.com/bizhi/kimi/01.jpg",
//            "https://b.zhutix.com/bizhi/kimi/02.jpg",
            "https://b.zhutix.com/bizhi/kimi/10.jpg",
//            "https://b.zhutix.com/bizhi/kimi/11.jpg"
        )
    }


    private val mBinding by lazy {
        ActivityRvBinding.inflate(layoutInflater)
    }

    private val mAdapter by lazy {
        RvIvAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        val ls = RvLayoutManager(10)
        val s1 = BannerLayoutManager(this, BannerLayoutManager.VERTICAL)
        val sAdapter = AAAAdapter(this)



        mBinding.rv1.apply {
            adapter = mAdapter
            setHasFixedSize(true)
            layoutManager = ls
            setItemViewCacheSize(-1)

            //      layoutManager=LinearLayoutManager(this@RvActivity)
        }


        mAdapter.setNewInstance(RvActivity.listImg.toMutableList())
        lifecycle.addObserver(ls)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(mBinding.rv1)

        var curPos = 0

        mBinding.btn1.setOnClickListener {

            mBinding.rv1.smoothScrollToPosition(++curPos)
        }


        mBinding.btn2.setOnClickListener {

            mAdapter.setNewInstance(listImg2.toMutableList())
            curPos = 0
        }
        lifecycleScope.launchWhenResumed {


            flow {
                while (true) {
                    delay(2000L)
                    emit(2)
                }
            }.flowOn(Dispatchers.Main)
                .collect { mBinding.rv1.smoothScrollToPosition(++curPos) }

        }
    }
}