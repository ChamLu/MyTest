package com.cham.mytest.ui.testrv


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cham.mytest.R
import com.cham.mytest.databinding.ActivityAtoBinding



/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/6/30 20:40
 * @UpdateUser:
 * @UpdateDate:     2020/6/30 20:40
 * @UpdateRemark:
 */
class TestAtoActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityAtoBinding


    private val  mViewmodel by lazy {

        ViewModelProvider(this).get(TestAtoViewModel::class.java)

    }

    private val starBto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                Log.e("TestAtoActivity", ": " + it.data?.getStringExtra("value"))
            }


        }


    companion object{
        const val TAG ="TestAtoActivity"

    }


    private val starPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {

            } else {

            }
        }

    private  val starUri  = registerForActivityResult(ActivityResultContracts.GetContent()){

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_ato)



        /**
         * 这页面是从 TestNodeActivity 调过来的
         * */
        intent.data?.let {
            Log.e(
                "FragmentActivity.TAG",
                "host = " + it.host + " path = " + it.path
                    .toString() + " query ： " + it.query + " scheme : "+it.scheme
            )
        }

        var s  = "".ifEmpty { "asdsad" }

        mBinding.btn1.setOnClickListener {

          starBto.launch(Intent(this@TestAtoActivity, TestBtoActivity::class.java))

         //   startActivity(Intent(this@TestAtoActivity, TestBtoActivity::class.java))
            //请求权限
       //      starPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        }


        mBinding.btn2.setOnClickListener {

            mViewmodel.mAsync()

        }


        mViewmodel.mBoolean.observe(this, Observer {

            Toast.makeText(this,"$it",Toast.LENGTH_SHORT).show()

        })
        mViewmodel.mBoolean1.observe(this, Observer {

            Toast.makeText(this,"1完成了 ---$it",Toast.LENGTH_SHORT).show()
        })
        mViewmodel.mBoolean2.observe(this, Observer {

            Toast.makeText(this,"2完成了 ---$it",Toast.LENGTH_SHORT).show()
        })

    }


}