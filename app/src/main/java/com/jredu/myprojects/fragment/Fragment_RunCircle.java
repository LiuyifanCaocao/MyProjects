package com.jredu.myprojects.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.jredu.myprojects.R;
//简单网络请求

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_RunCircle extends Fragment {
    Fragment_trends fragment_trends;
    Fragment_square fragment_square;
    Button trendsBtn;
    Button squareBtn;
    int i = 0;
    int m = 0;
    public Fragment_RunCircle() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment__run_circle, container, false);
        fragment_trends = new Fragment_trends();
        fragment_square = new Fragment_square();
        trendsBtn = (Button) v.findViewById(R.id.runcircle_button);
        squareBtn = (Button) v.findViewById(R.id.runcircle_button1);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.run_circle_fragment, fragment_trends);
        ft.add(R.id.run_circle_fragment, fragment_square);
        //ft.hide(fragment_square);
        ft.hide(fragment_trends);
        ft.commit();
        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.run_circle_rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (checkedId) {
                    case R.id.runcircle_button:
                        ft.hide(fragment_trends);
                        ft.show(fragment_square);
                        ft.commit();
                        break;
                    case R.id.runcircle_button1:
                        ft.hide(fragment_square);
                        ft.show(fragment_trends);

                        ft.commit();
                        break;
                }
            }
        });
        return v;
    }
}
