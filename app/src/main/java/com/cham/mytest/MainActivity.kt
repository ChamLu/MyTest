package com.cham.mytest

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.cham.mytest.database.AppDataBase
import com.cham.mytest.database.UserEntity
import com.cham.mytest.databinding.ActivityMainBinding
import com.cham.mytest.ui.main.SectionsPagerAdapter
import com.cham.mytest.utils.logeMsg
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    companion object {
        const val TAG = "MainActivity"

    }

    private lateinit var mBinding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootDir = MMKV.initialize(this)
        logeMsg(message = "mmkv root: $rootDir", TAG)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.lifecycleOwner = this


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        mBinding.viewPager.adapter = sectionsPagerAdapter

        mBinding.tabs.setupWithViewPager(mBinding.viewPager)

        val kv = MMKV.defaultMMKV()

        kv.encode("k1", "K1的值")
        val s = HashMap<String, String>()
        s.put("", "")

        logeMsg(message = "mmkv 取值: ${kv.decodeString("k1")}", TAG)

        val set: HashSet<String> = hashSetOf("aaaa", "sdasdad")
        kv.encode("hase", set)
        logeMsg(message = "mmvk 获取HashSet内容：" + kv.decodeStringSet("hase").toString(), TAG)


//        logeMsg(message = "onCreate  进程名字: " + MyApp.get().getProcesssName(this), TAG)

        val database = AppDataBase.getInstance(applicationContext)

        mBinding.fab.setOnClickListener { view ->
            viewModel.mS.value = 2


            /**
             * 数据库 Room 操作
             * */
//            lifecycleScope.launch(IO) {
//
//                val listUser = mutableListOf<UserEntity>()
//                for (i in 0..300) {
//                    val sRandom = (0..300).random()
//                    listUser.add(UserEntity(userName = "ss $sRandom", userP = "pp " + i))
//                }
//
////                database.userDao().deleteAll()
//
//                //删除前100条数据
//                database.userDao().delete(database.userDao().queryTop100())
//                database.userDao().insertUser(listUser)
//            }

        }
        mBinding.fab2.setOnClickListener { view ->
            hookLiveData()

        }

        viewModel.mS.observe(this,{

            logeMsg("","MainAty")
        })


    }


    private fun hookLiveData() {

        val mVersion = viewModel.mS.javaClass.superclass.getDeclaredField("mVersion")
        mVersion.isAccessible = true
        val mVersionValue = mVersion.get(viewModel.mS)

        logeMsg("hook: LiveData mVersion = $mVersionValue", "hook livedata")

        val mObservers = viewModel.mS.javaClass.superclass.getDeclaredField("mObservers")
        mObservers.isAccessible = true
        val mObserversValue = mObservers.get(viewModel.mS)

        //SafeIterableMap
        logeMsg(mObserversValue.javaClass.name)

        //获取到 注册Observers 的数量
        val mSize =mObserversValue.javaClass.getDeclaredField("mSize")
        mSize.isAccessible = true
        val mSizeValue = mSize.get(mObserversValue)
        logeMsg(""+mSizeValue)

       // getDeclaredMethods(),该方法是获取本类中的所有方法，包括私有的(private、protected、默认以及public)的方法。

     //   logeMsg("hook: mObserversValue = $mObserversValue", ""+mObserversValue.javaClass.name)

        val methodGet = mObserversValue.javaClass.getDeclaredMethod("get", Any::class.java)
        methodGet.isAccessible = true


        //myObserver就是自定义的Observer，即通过Observer这个key来拿到SafeIterableMap的值，

        val myObserver = Observer<Int> {
            logeMsg("initObserve:testNumber = $it", "hook livedata")
        }

        //这里也就是LifecycleBoundObserver的
        val objectWrapperEntry = methodGet.invoke(mObserversValue, myObserver)
//
//        val objectWrapper = (objectWrapperEntry!! as Map.Entry<*, *>).value
//
//        // ObserverWrapper mLastVersion
//
//        val mLastVersion = objectWrapper!!.javaClass.superclass.getDeclaredField("mLastVersion")
//        mLastVersion.isAccessible = true
//
//        val mLastVersionValue = mLastVersion.get(objectWrapper)
//
//        logeMsg("hook:observerWrapper mLastVersion = $mLastVersionValue", "hook livedata")


    }

}