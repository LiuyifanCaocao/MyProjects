package com.jredu.myprojects.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jredu.myprojects.R;
//广场搜索界面
public class SquareSearchActivity extends Activity {
SearchView searchView;
    ListView listView;
    private final  String[] mString = {"aaa","bbb","ccc","ddd"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_search);
        listView = (ListView) findViewById(R.id.search_listview);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mString));
        listView.setTextFilterEnabled(true);

    }

}
