package com.cham.mytest.ui.motionlayout

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.cham.mytest.R
import com.cham.mytest.databinding.ActivityOneMotionBinding
import com.cham.mytest.utils.logeMsg
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2022/2/18 18:17
 * @UpdateUser:
 * @UpdateDate:     2022/2/18 18:17
 * @UpdateRemark:
 */
class OneMotionActivity : AppCompatActivity() {


    var images = intArrayOf(
        R.drawable.ic_av1,
        R.drawable.ic_av3,
        R.drawable.ic_av4,
        R.drawable.ic_av5
    )

    var imageDrawable = mutableListOf<Int>()
    private lateinit var mBinding: ActivityOneMotionBinding

    companion object {
        val TAG = "OneMotionActivity"
        fun startOneMotionActivity(context: Context) {
            val intent = Intent(context, OneMotionActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityOneMotionBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                return 4
            }

            override fun populate(view: View?, index: Int) {
                if (view is ImageView) {
                    Log.e(TAG, "populate: $index  ")
                    view.setImageResource(images[index])

                }
            }

            override fun onNewItem(index: Int) {

                Log.e(TAG, "onNewItem: $index")

                    Glide.with(this@OneMotionActivity)
                        .load(images[index])
                        .apply(RequestOptions().transform(BlurTransformation(10)))
                        .into(object :CustomTarget<Drawable>(){
                            override fun onResourceReady(
                                resource: Drawable,
                                transition: Transition<in Drawable>?
                            ) {
                                mBinding.ivBackground.setImageDrawable(resource)
                            }
                            override fun onLoadCleared(placeholder: Drawable?) {

                            }
                        })



            }

        })

        for (i in 0 until 10 ){
            imageDrawable.add(i)
        }

//        val iterator = imageDrawable.iterator()
//
//        while(iterator.hasNext()){
//            val d = iterator.next()
//            if(d== 5){
//                iterator.remove()
//            }
//        }


        for (i in imageDrawable.indices step 3 ){
            var list = mutableListOf<Int >()
            // i = 0 3 6 9
            for(j in 0 until 3) {
                if(imageDrawable.size > i + j) {
                    list.add(imageDrawable[i + j])
                }
            }
            logeMsg(  "   -  "+ list)
        }






    }
}