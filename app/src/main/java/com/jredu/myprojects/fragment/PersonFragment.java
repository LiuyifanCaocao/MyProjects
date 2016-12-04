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
public class PersonFragment extends Fragment {

    Bundle bundle;
    Intent intent;

    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_person, container, false);
        ListView person_list = (ListView) v.findViewById(R.id.person_lsit);
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
        intent.putExtra("flag",false);
        getActivity().sendBroadcast(intent);
    }

}
