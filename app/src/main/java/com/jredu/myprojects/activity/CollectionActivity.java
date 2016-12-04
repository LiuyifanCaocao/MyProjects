/*
package com.jredu.myprojects.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.MySQLiteOpenHelper;
import com.jredu.myprojects.entity.AppleNews;
import com.jredu.myprojects.fragment.Fragment_My;
import com.jredu.myprojects.util.Collection;
import com.jredu.myprojects.util.Constant;

import java.util.List;

public class CollectionActivity extends Activity {
    List<AppleNews> list;
    AppleNews appleNews;
    ImageView collectionback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collectionback = (ImageView) findViewById(R.id.collection_back);
        collectionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectionActivity.this, Fragment_My.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void query(){
        MySQLiteOpenHelper sqLiteOpenHelper = new MySQLiteOpenHelper(this);
       SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                Constant.SportInfo.TBL_NAME,
                new String[]{Collection.CollectionData.TBL_TITLE, Collection.CollectionData.TBL_CONTENT,Collection.CollectionData.TBL_URL,Collection.CollectionData.TBL_PRIURL},
                null,
                null,
                null,
                null,
                null,
                null

        );
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int titleindex = cursor.getColumnIndex(Collection.CollectionData.TBL_TITLE);
            int contentindex = cursor.getColumnIndex(Collection.CollectionData.TBL_CONTENT);
            int imgindex = cursor.getColumnIndex(Collection.CollectionData.TBL_URL);
            int imgurlindex = cursor.getColumnIndex(Collection.CollectionData.TBL_PRIURL);
            String title = cursor.getString(titleindex);
            String content = cursor.getString(contentindex);
            String url = cursor.getString(imgindex);
            String priurl = cursor.getString(imgurlindex);
        }
    }
}
*/
