package com.cham.mytest.ui.cardPager;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cham.mytest.ui.cardPager.fra.CardFragment;

import java.util.List;

public class CardViewPagerAdapter extends FragmentStateAdapter {

    private FragmentManager mManager;  //创建FragmentManager
    private List<CardFragment> mList; //创建一个List<Fragment>

    //定义构造带两个参数
    public CardViewPagerAdapter(FragmentActivity fragmentActivity, List<CardFragment> list) {
        super(fragmentActivity);
        this.mManager = fragmentActivity.getSupportFragmentManager();
        this.mList = list;

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
