package com.jredu.myprojects.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jredu.myprojects.activity.Find_EditTextActivity;
import com.jredu.myprojects.activity.OfficialMatchActivity;
import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.MySQLiteOpenHelper;
import com.jredu.myprojects.util.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Find extends Fragment {
    RelativeLayout layout;
    ImageView search;
    SQLiteDatabase sqLiteDatabase;
    String[] title = {"0","线上赛事", "骑行赛事", "线下赛事"};
    String[] content = {"0","不用到现场就可以跑", "小伙伴一起骑", "到现场去闹起来"};
    int[] img = {0,R.drawable.online_match, R.drawable.ride_match, R.drawable.offline_match};
    int i=0;
    int m=0;
    public Fragment_Find() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment__find, container, false);
        search = (ImageView) v.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Find_EditTextActivity.class);
                startActivity(intent);
            }
        });
        layout = (RelativeLayout) v.findViewById(R.id.find_relative4);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m==0){
                    for (int i = 0; i < title.length; i++) {
                        insertData(i);
                    }
                    sqLiteDatabase.close();
                    m++;
                }


                Intent intent = new Intent(getContext(), OfficialMatchActivity.class);
                startActivity(intent);
            }
        });
        return v;

    }

    public void insertData(int i) {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext());
         sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.SportInfo.TBL_TITLE,title[i]);
        contentValues.put(Constant.SportInfo.TBL_CONTENT,content[i]);
        contentValues.put(Constant.SportInfo.TBL_IMG,img[i]);
        sqLiteDatabase.insert(Constant.SportInfo.TBL_NAME,null,contentValues);

    }

}
