package com.jredu.myprojects.baseadpater;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/29.
 */
public class Offical_MatchAdapter extends PagerAdapter {
    private ArrayList<ImageView> lists;


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }



}
