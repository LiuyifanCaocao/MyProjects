package com.jredu.myprojects.baseadpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/28.
 */
public class MyAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> lists;
    ArrayList<String> tabs;

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setLists(ArrayList<Fragment> lists) {
        this.lists = lists;
    }

    public void setTabs(ArrayList<String> tabs) {
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return lists ==null?null:lists.get(position);
    }

    @Override
    public int getCount() {
        return tabs==null?null:tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs == null ? null : tabs.get(position);
    }

}
