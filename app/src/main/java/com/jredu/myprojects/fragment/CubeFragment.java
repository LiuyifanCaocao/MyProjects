package com.jredu.myprojects.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jredu.myprojects.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CubeFragment extends Fragment {
    Intent intent;
    public CubeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cube, container, false);
        ListView cube_list = (ListView) v.findViewById(R.id.cube_lsit);
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            onVisible();
        }
    }
    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }
    public void onVisible(){
        intent = new Intent();
        intent.setAction("com.zx");
        intent.putExtra("flag",true);
        getActivity().sendBroadcast(intent);
    }

}
