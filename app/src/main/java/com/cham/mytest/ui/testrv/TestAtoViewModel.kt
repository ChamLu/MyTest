package com.cham.mytest.ui.testrv

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/7/18 16:52
 * @UpdateUser:
 * @UpdateDate:     2020/7/18 16:52
 * @UpdateRemark:
 */
class TestAtoViewModel : ViewModel() {


    var mBoolean = MutableLiveData<Boolean>()

    var mBoolean1 = MutableLiveData<Boolean>()
    var mBoolean2 = MutableLiveData<Boolean>()


    fun mAsync() {

        val s1 = viewModelScope.async {
            delay(1000)
            mBoolean1.value = true
            mBoolean1.value!!
        }


        val s2 = viewModelScope.async {
            delay(3000)
            mBoolean2.value = true
            mBoolean2.value!!
        }

        viewModelScope.launch {
            withContext(IO){
                Log.e("TestAtoViewModel", "mAsync1: "+ Thread.currentThread().name )
            }
            Log.e("TestAtoViewModel", "mAsync2: "+ Thread.currentThread().name )

        }






//        viewModelScope.launch {
//            mBoolean.value = s1.await() && s2.await()
//        }


//        viewModelScope.launch {
//
//            val a: Deferred<Boolean> = async {
//                delay(2000)
//                mBoolean1.value= true
//                return@async  mBoolean1.value!!
//            }
//
//
//            val b = async {
//                delay(1000)
//                mBoolean2.value= true
//                return@async mBoolean2.value!!
//            }
//
//
//            mBoolean.value = a.await() && b.await()
//
//
//        }


    }
}