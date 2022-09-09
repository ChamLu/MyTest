package com.cham.glidebigpicture

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cham.glidebigpicture.databinding.ActivityExpandtextBinding

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/6/24 16:59
 * @UpdateUser:
 * @UpdateDate:     2021/6/24 16:59
 * @UpdateRemark:
 */
class ExpandableTextActivity:AppCompatActivity() {

   
    companion object {
        fun startExpandableTextActivity(context: Context) {
            val intent= Intent(context,ExpandableTextActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val mBinding by lazy {

        ActivityExpandtextBinding.inflate(layoutInflater)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.tvExpand.setOriginalText("我是测试很长很差稍等哈数据库的即可获得房价快速导航福克斯大家很健康的粉红色短裤符合时代科技士大夫江户时代尽快发货速度加快圣诞节粉红色的尽快发货速度加快十大科技粉红色的尽快发货速度加快四大皆空粉红色的尽快发货速度加快多久发货速度尽快发货速度加快是东京核辐射的接口返回是大家看法和受到激发函数的接口粉红色的尽快发航空四道口附近合适的借口粉红色的尽快发货十大科技粉红色的尽快发货速度加快十大科技粉红色的尽快发货速度加快深刻搭街坊好久开始大幅回升的接口返回上的飞机和快速的尽快发货速度加快粉红色士大夫艰苦合适的尽快发货速度加快粉红色欸健康的粉丝偶饿可就是看看拉萨佛i二的数据恢复iEMS你发ui速度加快恢复是对何况数据的客户反馈士大夫户口")



    }

    override fun onResume() {
        super.onResume()
    }
}