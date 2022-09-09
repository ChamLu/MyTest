package com.cc.mediacodec

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.cc.mediacodec.databinding.ActivityMain2Binding
import com.google.android.material.navigation.NavigationView

class MainActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val mBinding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }


    val redTextValues by lazy { resources.getStringArray(R.array.redTextValues) }
    val redTextColors by lazy { resources.getStringArray(R.array.redTextColors) }
    val pinkTextValues by lazy { resources.getStringArray(R.array.pinkTextValues) }
    val pinkTextColors by lazy { resources.getStringArray(R.array.pinkTextColors) }
    val purpleTextValues by lazy { resources.getStringArray(R.array.purpleTextValues) }
    val purpleTextColors by lazy { resources.getStringArray(R.array.purpleTextColors) }
    val deepPurpleTextValues by lazy { resources.getStringArray(R.array.deepPurpleTextValues) }
    val deepPurpleTextColors by lazy { resources.getStringArray(R.array.deepPurpleTextColors) }
    val indigoTextValues by lazy { resources.getStringArray(R.array.indigoTextValues) }
    val indigoTextColors by lazy { resources.getStringArray(R.array.indigoTextColors) }
    val blueTextValues by lazy { resources.getStringArray(R.array.blueTextValues) }
    val blueTextColors by lazy { resources.getStringArray(R.array.blueTextColors) }
    val lightBlueTextValues by lazy { resources.getStringArray(R.array.lightBlueTextValues) }
    val lightBlueTextColors by lazy { resources.getStringArray(R.array.lightBlueTextColors) }
    val cyanTextValues by lazy { resources.getStringArray(R.array.cyanTextValues) }
    val cyanTextColors by lazy { resources.getStringArray(R.array.cyanTextColors) }
    val tealTextValues by lazy { resources.getStringArray(R.array.tealTextValues) }
    val tealTextColors by lazy { resources.getStringArray(R.array.tealTextColors) }
    val greenTextValues by lazy { resources.getStringArray(R.array.greenTextValues) }
    val greenTextColors by lazy { resources.getStringArray(R.array.greenTextColors) }
    val lightGreenTextValues by lazy { resources.getStringArray(R.array.lightGreenTextValues) }
    val lightGreenTextColors by lazy { resources.getStringArray(R.array.lightGreenTextColors) }
    val limeTextValues by lazy { resources.getStringArray(R.array.limeTextValues) }
    val limeTextColors by lazy { resources.getStringArray(R.array.limeTextColors) }
    val yellowTextValues by lazy { resources.getStringArray(R.array.yellowTextValues) }
    val yellowTextColors by lazy { resources.getStringArray(R.array.yellowTextColors) }
    val amberTextValues by lazy { resources.getStringArray(R.array.amberTextValues) }
    val amberTextColors by lazy { resources.getStringArray(R.array.amberTextColors) }
    val orangeTextValues by lazy { resources.getStringArray(R.array.orangeTextValues) }
    val orangeTextColors by lazy { resources.getStringArray(R.array.orangeTextColors) }
    val deepOrangeTextValues by lazy { resources.getStringArray(R.array.deepOrangeTextValues) }
    val deepOrangeTextColors by lazy { resources.getStringArray(R.array.deepOrangeTextColors) }
    val brownTextValues by lazy { resources.getStringArray(R.array.brownTextValues) }
    val brownTextColors by lazy { resources.getStringArray(R.array.brownTextColors) }
    val greyTextValues by lazy { resources.getStringArray(R.array.greyTextValues) }
    val greyTextColors by lazy { resources.getStringArray(R.array.greyTextColors) }
    val blueGreyTextValues by lazy { resources.getStringArray(R.array.blueGreyTextValues) .toMutableList() }
    val blueGreyTextColors by lazy { resources.getStringArray(R.array.blueGreyTextColors) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        window.statusBarColor = Color.parseColor(redTextValues[5])
        mBinding.navView.itemIconTintList = null
        mBinding.navView.setNavigationItemSelectedListener(this)
        mBinding.layoutMain.title.setOnClickListener {
            if (!mBinding.container.isDrawerOpen(GravityCompat.START)) {
                mBinding.container.openDrawer(GravityCompat.START)
            }
        }
        mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(redTextValues[5]))
        mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(redTextValues[5]))


        mBinding.layoutMain.btnGo.setOnClickListener {
            MainActivity.startMainActivity(
                this,
                mBinding.layoutMain.edPlay.text.toString(),
                mBinding.layoutMain.edPlayNum.text.toString(),
                mBinding.layoutMain.edNum.text.toString(),
            )
        }


    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_red -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(redTextValues[5]))
                window.statusBarColor = Color.parseColor(redTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(redTextValues[5]))
            }
            R.id.nav_pink -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(pinkTextValues[5]))
                window.statusBarColor = Color.parseColor(pinkTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(pinkTextValues[5]))
            }
            R.id.nav_purple -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(purpleTextValues[5]))
                window.statusBarColor = Color.parseColor(purpleTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(purpleTextValues[5]))
            }
            R.id.nav_deep_purple -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(deepPurpleTextValues[5]))
                window.statusBarColor = Color.parseColor(deepPurpleTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(deepPurpleTextValues[5]))
            }
            R.id.nav_indigo -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(indigoTextValues[5]))
                window.statusBarColor = Color.parseColor(indigoTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(indigoTextValues[5]))
            }
            R.id.nav_blue -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(blueTextValues[5]))
                window.statusBarColor = Color.parseColor(blueTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(blueTextValues[5]))
            }
            R.id.nav_light_blue -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(lightBlueTextValues[5]))
                window.statusBarColor = Color.parseColor(lightBlueTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(lightBlueTextValues[5]))
            }
            R.id.nav_cyan -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(cyanTextValues[5]))
                window.statusBarColor = Color.parseColor(cyanTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(cyanTextValues[5]))
            }
            R.id.nav_teal -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(tealTextValues[5]))
                window.statusBarColor = Color.parseColor(tealTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(tealTextValues[5]))
            }
            R.id.nav_green -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(greenTextValues[5]))
                window.statusBarColor = Color.parseColor(greenTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(greenTextValues[5]))
            }
            R.id.nav_light_green -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(lightGreenTextValues[5]))
                window.statusBarColor = Color.parseColor(lightGreenTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(lightGreenTextValues[5]))
            }
            R.id.nav_lime -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(limeTextValues[5]))
                window.statusBarColor = Color.parseColor(limeTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(limeTextValues[5]))
            }
            R.id.nav_yellow -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(yellowTextValues[5]))
                window.statusBarColor = Color.parseColor(yellowTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(yellowTextValues[5]))
            }
            R.id.nav_amber -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(amberTextValues[5]))
                window.statusBarColor = Color.parseColor(amberTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(amberTextValues[5]))
            }
            R.id.nav_orange -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(orangeTextValues[5]))
                window.statusBarColor = Color.parseColor(orangeTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(orangeTextValues[5]))
            }
            R.id.nav_deep_orange -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(deepOrangeTextValues[5]))
                window.statusBarColor = Color.parseColor(deepOrangeTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(deepOrangeTextValues[5]))
            }
            R.id.nav_brown -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(brownTextValues[5]))
                window.statusBarColor = Color.parseColor(brownTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(brownTextValues[5]))
            }
            R.id.nav_grey -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(greyTextValues[5]))
                window.statusBarColor = Color.parseColor(greyTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(greyTextValues[5]))
            }
            R.id.nav_blue_grey -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(blueGreyTextValues[5]))
                window.statusBarColor = Color.parseColor(blueGreyTextValues[5])
                mBinding.layoutMain.btnGo.setBackgroundColor(Color.parseColor(blueGreyTextValues[5]))
            }
            else -> {
                mBinding.layoutMain.appBar.setBackgroundColor(Color.parseColor(redTextValues[5]))
            }
        }
        mBinding.container.closeDrawer(GravityCompat.START)
        return true
    }
}