package com.cham.mytest.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.cham.mytest.MainViewModel
import com.cham.mytest.R
import com.cham.mytest.databinding.FragmentPokoBinding
import com.cham.mytest.databinding.LayoutViewstubBinding
import com.cham.mytest.ui.applayoutbar_ui.AppLAyoutBarActivity
import com.cham.mytest.ui.drawerLayout.DrawerLayoutActivity
import com.cham.mytest.ui.viewpager2.ui.ViewPager2MainActivity
import com.cham.mytest.utils.logeMsg
import com.google.gson.JsonArray
import com.google.gson.JsonObject

/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/05/25
 *  @Description  :
 *
 */
class PokoFragment : Fragment() {

    private val TAG = "PokoFragment"

    private val vm by activityViewModels<MainViewModel>()
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
        fun newInstance(sectionNumber: Int): PokoFragment {
            return PokoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    private lateinit var mBinding: FragmentPokoBinding

    private lateinit var mLayoutBinding: LayoutViewstubBinding
    private var jsob=JsonObject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_poko, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btn1.setOnClickListener {
            if (!mBinding.viewstub1.isInflated) {
                mLayoutBinding =
                    DataBindingUtil.getBinding(mBinding.viewstub1.viewStub!!.inflate())!!
            }
            testOnClick()
        }

        mBinding.btn2.setOnClickListener {
            if (this::mLayoutBinding.isInitialized) {
                Log.e(TAG, "已经实例化")
                mLayoutBinding.tvViewstub.text = "改变了 ViewStub 的内容"
            } else {
                Log.e(TAG, "还没有实例化")
            }
        }

        mBinding.btn3.setOnClickListener {
          startActivity(Intent(requireContext(), ViewPager2MainActivity::class.java))
        }

        mBinding.btn4.setOnClickListener {

            startActivity(Intent(requireContext(), DrawerLayoutActivity::class.java))

        }

        mBinding.btn5.setOnClickListener {
            testJsonObject()

        }
        mBinding.btn6.setOnClickListener {
            changeJsonObject()
        }
        mBinding.btn7.setOnClickListener {
            AppLAyoutBarActivity. startAppLAyoutBarActivity(requireContext())
        }
    }


    override fun onResume() {
        super.onResume()
        vm.mS.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),TAG+it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun testOnClick() {
        if (this::mLayoutBinding.isInitialized) {
            Log.e(TAG, "已经实例化")
            mLayoutBinding.tvViewstub.setOnClickListener {
                mLayoutBinding.tvViewstub.text = "good Test"
            }
        } else {
            Log.e(TAG, "还没有实例化")
        }
    }


    private fun testJsonObject(){

        val jsArry =JsonArray()

        val jsob1=JsonObject()
        jsob1.addProperty("dfdsfsdf","")
        jsob1.addProperty("asdasd","asdasdasdasd")
        jsob1.addProperty("asdasd","asdasdasdasd")
        jsob1.addProperty("adaasdasdsdasd","asdasdasdasd")
        jsob1.addProperty("adasdasd",3000000)
        jsob1.addProperty("dd",1000)


        jsArry.add(jsob1)

        jsob.addProperty("要更改的字段","")
        jsob.addProperty("www",12312312)
        jsob.add("et",jsArry)


        logeMsg("TAG","--"+jsob.get("www").asLong)


        logeMsg("TAG",jsob.toString())

    }


    private fun changeJsonObject(){

        jsob.addProperty("要更改的字段","啦啦啦我边了")


        logeMsg("TAG",jsob.toString())
    }
}