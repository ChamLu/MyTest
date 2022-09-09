package com.cham.mytest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val kv = MMKV.defaultMMKV()

        kv.encode("k1", "K1的值")
        val s = HashMap<String, String>()
        s.put("", "")

        logeMsg(message = "mmkv 取值: ${kv.decodeString("k1")}", TAG)

        val set: HashSet<String> = hashSetOf("aaaa", "sdasdad")
        kv.encode("hase", set)
        logeMsg(message = "mmvk 获取HashSet内容：" + kv.decodeStringSet("hase").toString(), TAG)


        logeMsg(message = "onCreate  进程名字: " + MyApp.get().getProcesssName(this), TAG)

        val database = AppDataBase.getInstance(applicationContext)

        fab.setOnClickListener { view ->
            viewModel.mS.value = 2
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            lifecycleScope.launch(IO) {

                val listUser = mutableListOf<UserEntity>()
                for (i in 0..300) {
                    val sRandom = (0..300).random()
                    listUser.add(UserEntity(userName = "ss $sRandom", userP = "pp " + i))
                }

//                database.userDao().deleteAll()

                //删除前100条数据
                database.userDao().delete(database.userDao().queryTop100())
                database.userDao().insertUser(listUser)
            }

        }
        mBinding.fab2.setOnClickListener { view ->
            lifecycleScope.launch(IO) {

                database.userDao().queryTop100()

            }
        }

    }


}