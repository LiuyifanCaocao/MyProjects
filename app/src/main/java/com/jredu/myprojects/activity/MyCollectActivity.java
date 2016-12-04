package com.jredu.myprojects.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.ListBaseAdapter;
import com.jredu.myprojects.baseadpater.MySQLiteOpenHelper;
import com.jredu.myprojects.entity.AppleNews;
import com.jredu.myprojects.util.Collection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyCollectActivity extends Activity implements View.OnClickListener {
    ImageView myCollectBack;
    ListView collectionListView;
    List<AppleNews> list = new ArrayList<AppleNews>();
    AppleNews appleNews;
    ListBaseAdapter listBaseAdapter;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    RequestQueue queue = null;
    String title;
    String content;
    String imgUrl;
    String url;
    Bitmap bitmap;
    ImageView my_CollectionImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        myCollectBack = (ImageView) findViewById(R.id.my_collect_back);
        my_CollectionImg = (ImageView) findViewById(R.id.my_CollectionImg);
        collectionListView = (ListView) findViewById(R.id.collectionListView);
        my_CollectionImg.setOnClickListener(this);
        myCollectBack.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);
        panDuan(list);
        Queue();
        Delete();
    }

    public void panDuan(List<AppleNews> list) {
        if (list.size() > 0) {
            my_CollectionImg.setVisibility(View.GONE);

        } else {
            my_CollectionImg.setVisibility(View.VISIBLE);

        }
    }

    public void Queue() {
        MySQLiteOpenHelper sqLiteOpenHelper = new MySQLiteOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query(
                Collection.CollectionData.TBL_NAME,
                new String[]{Collection.CollectionData.TBL_TITLE, Collection.CollectionData.TBL_CONTENT, Collection.CollectionData.TBL_PRIURL, Collection.CollectionData.TBL_URL},
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            int titleindex = cursor.getColumnIndex(Collection.CollectionData.TBL_TITLE);
            int contentindex = cursor.getColumnIndex(Collection.CollectionData.TBL_CONTENT);
            int imgindex = cursor.getColumnIndex(Collection.CollectionData.TBL_PRIURL);
            int urlindex = cursor.getColumnIndex(Collection.CollectionData.TBL_URL);
            title = cursor.getString(titleindex);
            content = cursor.getString(contentindex);
            imgUrl = cursor.getString(imgindex);
            url = cursor.getString(urlindex);
            MyImgThread imgThread = new MyImgThread(imgUrl);
            Thread thread = new Thread(imgThread);
            thread.start();
        }
    }

    public void Delete() {
        collectionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplication());//调用帮助类的构造函数 创建数据库
                sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();//调用帮助类的onCreate 创建表
                String title1 = list.get(position).getTitle();
                sqLiteDatabase.delete(
                        Collection.CollectionData.TBL_NAME,
                        Collection.CollectionData.TBL_TITLE + " = ? ",
                        new String[]{title1}
                );
                list.remove(position);
                listBaseAdapter.notifyDataSetChanged();
                Toast.makeText(MyCollectActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                panDuan(list);
                return true;
            }
        });
    }

    class MyImgThread implements Runnable {
        private String imgPath;
        private Bitmap bitmap;

        public MyImgThread(String imgPath) {
            this.imgPath = imgPath;
        }

        @Override
        public void run() {
            bitmap = getImgBitmap(imgPath);
            Message msg = new Message();
            msg.obj = bitmap;
            msg.what = 0;
            mHandler.sendMessage(msg);
        }
    }
    // 显示网络上的图片
    public Bitmap getImgBitmap(String imageUri) {

        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL myFileUrl = new URL(imageUri);
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.disconnect();
                is.close();
                is.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Bitmap bitmap = (Bitmap) msg.obj;
                appleNews = new AppleNews(title, content, url, bitmap);
                list.add(0, appleNews);
                listBaseAdapter = new ListBaseAdapter(getApplicationContext(), list);
                collectionListView.setAdapter(listBaseAdapter);
                panDuan(list);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_collect_back:
                onBackPressed();
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
