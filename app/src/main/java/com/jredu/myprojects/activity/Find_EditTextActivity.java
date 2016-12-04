package com.jredu.myprojects.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.MyAdapter;
import com.jredu.myprojects.fragment.CubeFragment;
import com.jredu.myprojects.fragment.PersonFragment;

import java.util.ArrayList;

//viewpager 实现两个fragment支持手势滑动
public class Find_EditTextActivity extends FragmentActivity{
    EditText editText_person;
    EditText editText_cube;
    ImageView edit_search_back;
    TextView find_edittext_person;
    TextView find_edittext_cube;
    ArrayList<Fragment> fragmentArrayList;
    ArrayList<String> stringArrayList;
    ViewPager viewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__edit_text);
        editText_person = (EditText) findViewById(R.id.find_edittext_person);
        editText_cube = (EditText) findViewById(R.id.find_edittext_cube);
        edit_search_back = (ImageView) findViewById(R.id.edit_search_back);
        find_edittext_person = (TextView) findViewById(R.id.find_edittext_person);
        find_edittext_cube = (TextView) findViewById(R.id.find_edittext_cube);
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new PersonFragment());
        fragmentArrayList.add(new CubeFragment());
        myAdapter.setLists(fragmentArrayList);
        stringArrayList = new ArrayList<String>();
        stringArrayList.add("人");
        stringArrayList.add("团");
        myAdapter.setTabs(stringArrayList);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablelayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(myAdapter);
        //关联viewPager和TabLayout
        tabLayout.setupWithViewPager(viewPager);
        edit_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        IntentFilter intentFilter = new IntentFilter("com.zx");
        registerReceiver(receiver,intentFilter);
    }
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                    if (intent.getExtras().getBoolean("flag")){
                        find_edittext_cube.setVisibility(View.VISIBLE);
                        find_edittext_person.setVisibility(View.GONE);
                    }else {
                        find_edittext_cube.setVisibility(View.GONE);
                        find_edittext_person.setVisibility(View.VISIBLE);
                    }
            }
        };

    @Override
    protected void onDestroy() {
        if (receiver!=null){
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }
}
