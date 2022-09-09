package com.cham.mytest.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.cham.mytest.MainViewModel
import com.cham.mytest.R
import com.cham.mytest.bean.MemberBean
import com.cham.mytest.databinding.FragmentNicoBinding
import com.cham.mytest.testchain.TestNodeActivity
import com.cham.mytest.ui.cardPager.CardPagerActivity
import com.cham.mytest.ui.eventDispatch.EventActivity
import com.cham.mytest.ui.motionlayout.OneMotionActivity
import com.cham.mytest.ui.motionlayout.TwoMotionActivity
import com.cham.mytest.ui.rv.RvActivity
import com.cham.mytest.ui.testrv.TestRcyBoomActivity
import com.cham.mytest.ui.快排.Student
import com.cham.mytest.utils.logeMsg
import com.cham.mytest.worker.Workkkk
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random


/**
 * A placeholder fragment containing a simple view.
 */
class NicoFragment : Fragment() {

    private val TAG = "NicoFragment"


    private val viewModel by createViewModelLazy<PageViewModel>(PageViewModel::class,
        { ViewModelStore() })

    private val vm by activityViewModels<MainViewModel>()

    private lateinit var mBinding: FragmentNicoBinding
    private val mSparse = SparseArray<Job>()


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)

        /*
        *  最早在 View  处于 Created 状态时从数据流收集数据，并在
          生命周期进入 STOPPED 状态时 SUSPENDS（挂起）收集操作。
         在 View 转为 DESTROYED 状态时取消数据流的收集操作。*/
        lifecycleScope.launchWhenCreated {

            /*/ 最早在 View  处于 STARTED 状态时从数据流收集数据，并在
        // 生命周期进入 STOPPED 状态时 STOPPED（停止）收集操作。
        // 它会在生命周期再次进入 STARTED 状态时自动开始进行数据收集操作。*/

            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.m1StateFlow.collect {
                    logeMsg("在onCreate订阅", "$it")
                }
            }

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_nico, container, false)
        mBinding.lifecycleOwner = this

        viewModel.countDown()

        testOnClick()

        return mBinding.root
    }


    override fun onResume() {
        super.onResume()
        if (viewModel.isLazyInit) {
            viewModel.isLazyInit = false

            val mListStudent = mutableListOf<Student>().apply {
                add(Student("里斯", "洒洒水", true, 5.0))
                add(Student("里斯1", "洒洒水1", false, 5.0))
                add(Student("里斯2", "洒洒水2", true, 5.0))
                add(Student("里斯3", "洒洒水3", false, 5.0))
                add(Student("里斯4", "洒洒水4", true, 5.0))
                add(Student("里斯5", "洒洒水5", false, 5.0))
                add(Student("里斯6", "洒洒水6", true, 5.0))
                add(Student("里斯7", "洒洒水7", false, 5.0))
                add(Student("里斯8", "洒洒水8", true, 5.0))
                add(Student("里斯9", "洒洒水9", false, 5.0))
                add(Student("里斯10", "洒洒水10", true, 5.0))
                add(Student("里斯11", "洒洒水11", false, 5.0))
                add(Student("里斯12", "洒洒水12", true, 5.0))
                add(Student("里斯13", "洒洒水13", false, 5.0))
                add(Student("里斯14", "洒洒水14", true, 4.1))
                add(Student("里斯15", "洒洒水15", false, 4.0))
                add(Student("里斯16", "洒洒水16", true, 5.0))
                add(Student("里斯17", "洒洒水17", false, 6.0))
                add(Student("里斯18", "洒洒水18", true, 7.0))
                add(Student("里斯19", "洒洒水19", true, 8.0))
                add(Student("里斯20", "洒洒水20", true, 9.0))
            }

            val s = mListStudent.filter { it.passing && it.averageGrade > 4.0 } // 1
                .sortedBy { it.averageGrade } // 2 排序 低到高
                .take(10) // 取前面多少条 排序前
                .sortedWith(compareBy({ it.surname }, { it.name })) // 4

            logeMsg(message = s.toString())

            mBinding.tvSpean.text = "爱可以简简单单没有伤害"
        }


        vm.mS.observe(viewLifecycleOwner, Observer {
            logeMsg("Fragment")
         //   Toast.makeText(requireContext(), TAG + "$it", Toast.LENGTH_SHORT).show()
        })
    }


    /**
     * 线程同步顺序执行
     * */
    private fun 线程() {
        var count = 0
        val mutex = Mutex()

        lifecycleScope.launchWhenResumed {
//            withContext(IO){


            mutex.withLock {
                repeat(1000000) {
                    count++
                }

            }
            //       }
            println(Thread.currentThread().name + "-----------------" + 1 + mutex.isLocked)
        }

        lifecycleScope.launchWhenResumed {
            println(Thread.currentThread().name + "-----------------" + 2 + mutex.isLocked)
            mutex.withLock {
                println(Thread.currentThread().name + "-----------------" + 3 + mutex.isLocked)
                repeat(1000000) {
                    count++
                }
                println(Thread.currentThread().name + "-----------------" + 4 + mutex.isLocked)
            }

            println(Thread.currentThread().name + "-----------------" + 5 + mutex.isLocked)
        }
        println(count)

    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun testOnClick() {

        mBinding.tvSpean.setOnClickListener {
            if (mBinding.tvSpean.selectionStart == -1 && mBinding.tvSpean.selectionEnd == -1) {
                logeMsg(" 普通点击事件  ", "0000")
            } else {
                logeMsg("不处理----  ", "Spanner点击事件 后再回调")
            }
            threadLocalTest()
        }




        mBinding.btn1.setOnClickListener {

            lifecycleScope.launch {

                repeatOnLifecycle(Lifecycle.State.STARTED){

                    viewModel.mSflow.collect{
                        logeMsg("收集回来的--$it")

                    }
                }

            }

            viewModel.sentValue()

            //测试一
            var sBean = MemberBean()
            sBean.memberId = "广告: ---vivo S7手机将不惧距离与光线的限制，带来全场景化自拍体验，刷新了5G时代的自拍旗舰标准"
            mBinding.tvSpean4.text = sBean.memberKk
            mBinding.tvSpean4.isSelected = true
            mBinding.tvSpean4.setOnClickListener {
                Toast.makeText(context, sBean.memberKk, Toast.LENGTH_SHORT).show()
            }


            //测试二
            viewModel.onTest()

        }
        mBinding.btn2.setOnClickListener {
            Log.e("后部分线程明： ", Thread.currentThread().name)
            context?.let {
                Glide.with(it)
                    .load(R.drawable.bg_live_error)
                    .into(mBinding.iv1)
            }

            setFlow(5)
            线程()

        }
        mBinding.btn3.setOnClickListener {
            //测试限制的MaxHeightRecyclerView
            val mDailog = RecyclerViewDialog()
            mDailog.show(childFragmentManager, "伤心太平洋")


            //启动工作Work
            Workkkk.doMyWork(context = requireContext())

        }


        mBinding.btn4.setOnClickListener {
            Workkkk.cancelMyWork()
//            val mBottomSheetDialogFragment = BottomsheetVp2Dialog()
//            mBottomSheetDialogFragment.show(childFragmentManager, "bottomSheet")
        }
        mBinding.btn5.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    context,
                    TestRcyBoomActivity::class.java
                )
            )
        }
        mBinding.btn6.setOnClickListener {
            startActivity(Intent(requireContext(), TestNodeActivity::class.java))

        }
        mBinding.btn7.setOnClickListener {
            EventActivity.startEventActivity(requireContext())
        }
        mBinding.btn8.setOnClickListener {
            RvActivity.startRv(requireContext())
        }

        mBinding.btn9.setOnClickListener {
            CardPagerActivity.startCardPagerActivity(requireContext())
        }
        mBinding.btn10.setOnClickListener {

            OneMotionActivity.startOneMotionActivity(requireContext())

        }
        mBinding.btn11.setOnClickListener {
            TwoMotionActivity.startTwoMotionActivity(requireContext())
        }
        val r = Random(9)
        mBinding.btnAdd.setOnClickListener {


            mBinding.tvSpean.text=r.nextInt(10).toString()
        }
    }


    private fun setFlow(tt: Int) {
        if (mSparse[0] != null && mSparse[0].isActive)
            mSparse[0].cancel()


        mSparse.append(0, lifecycleScope.launchWhenResumed {
            try {
                flow<Int> {
                    for (i in tt downTo 1) {
                        delay(1000L)
                        emit(i)
                    }
                }.flowOn(Dispatchers.IO)
                    .collect {
                        //这里线程 是跟随 lifecycleScope.launchWhenResumed 的线程
                        Log.e(TAG, " 接收值： $it  " + Thread.currentThread().name)

                    }

            } catch (e: java.lang.Exception) {

            }


        })


    }


    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): NicoFragment {
            return NicoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    val threadLocal = ThreadLocal<String>()


    private fun threadLocalTest(){
        threadLocal.set("DDD")

        val lopp =Looper.getMainLooper()
        lifecycleScope.launch(IO) {
            threadLocal.set("ABC")
            logeMsg("threadLocal1： ${threadLocal.get()}")
        }
        lifecycleScope.launch(IO) {
            threadLocal.set("BCD")
            logeMsg("threadLocal2： ${threadLocal.get()}")
        }

        logeMsg("threadLocal： ${threadLocal.get()}")

        //找出 1-100 的素数  素数是 >1 的 只能备自身整除的数
        val primeNumber = (1..100).filter {num ->
            //true  留下
            (2 until num).none {
                num % it == 0
            } && num>1
        }

        logeMsg(primeNumber.toString()  )

    }
}