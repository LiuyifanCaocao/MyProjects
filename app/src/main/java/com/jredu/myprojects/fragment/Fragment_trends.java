package com.jredu.myprojects.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.ListBaseAdapter;
import com.jredu.myprojects.baseadpater.MySQLiteOpenHelper;
import com.jredu.myprojects.entity.AppleNews;
import com.jredu.myprojects.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_trends extends Fragment {
    ListView listView1;
    SwipeRefreshLayout swipeRefreshLayout;
    RequestQueue queue = null;
    String title;
    String content;
    String priUrl;
    String url;
    Cursor cursor;
    ImageView imgBar;
    boolean flag = false;
    final List<AppleNews> list = new ArrayList<AppleNews>();

    public Fragment_trends() {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_trends, container, false);
        listView1 = (ListView) v.findViewById(R.id.listView1);
        imgBar = (ImageView) v.findViewById(R.id.imgBar);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.trends_SRL);
        queue = Volley.newRequestQueue(getContext());
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(list.get(position).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                title = list.get(position).getTitle();
                content = list.get(position).getDescription();
                priUrl = list.get(position).getPicUrl();
                url = list.get(position).getUrl();
                flag = query();
                if (flag) {
                    MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext());
                    SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Collection.CollectionData.TBL_TITLE, title);
                    contentValues.put(Collection.CollectionData.TBL_CONTENT, content);
                    contentValues.put(Collection.CollectionData.TBL_PRIURL, priUrl);
                    contentValues.put(Collection.CollectionData.TBL_URL, url);
                    sqLiteDatabase.insert(Collection.CollectionData.TBL_NAME, null, contentValues);
                    sqLiteDatabase.close();
                    Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "此新闻已收藏", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
        /*自动请求网络数据*/
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorGreen);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        Message message = handler.obtainMessage(1, "over");
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        return v;
    }

    public boolean query() {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext());
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query(
                Collection.CollectionData.TBL_NAME,
                new String[]{Collection.CollectionData.TBL_TITLE},
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount()==0){
            flag=true;
        }{
            while (cursor.moveToNext()) {
                int titleindex = cursor.getColumnIndex(Collection.CollectionData.TBL_TITLE);
                String oldTitle = cursor.getString(titleindex);
                if (title.equals(oldTitle)) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
        }

        sqLiteDatabase.close();
        return flag;
    }

    public void getData() {

        StringRequest request = new StringRequest(
                Request.Method.GET,
               /* "http://apis.baidu.com/txapi/apple/apple?num=10",*/
                "http://apis.baidu.com/txapi/tiyu/tiyu?num=10",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String ss = response;
                        analysls(ss);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imgBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "读取失败", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    String jsonObject = new String(new String(response.data, "UTF-8"));
                    return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (Exception je) {
                    return Response.error(new ParseError(je));
                }
            }

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("apikey", "06a5cf0cb65219e1d36be292e27f147f");
                return map;
            }

        };
        queue.add(request);
    }

    private List<AppleNews> analysls(String ss) {


        try {
            JSONObject jsonObject = new JSONObject(ss);
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            JSONArray jsonArray = jsonObject.getJSONArray("newslist");
            for (int i = 0; jsonArray.length() > 0; i++) {

                final JSONObject object = jsonArray.getJSONObject(i);
                ImageRequest imageRequest = new ImageRequest(
                        object.getString("picUrl"),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                AppleNews appleNews;
                                try {
                                    appleNews = new AppleNews(
                                            object.getString("ctime"),
                                            object.getString("title"),
                                            object.getString("description"),
                                            object.getString("picUrl"),
                                            object.getString("url"),
                                            response
                                    );
                                    list.add(appleNews);
                                    if (list.size() > 4) {
                                        ListBaseAdapter listBaseAdapter = new ListBaseAdapter(getContext(), list);
                                        listView1.setAdapter(listBaseAdapter);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        0,
                        0,
                        Bitmap.Config.ARGB_8888,
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );
                queue.add(imageRequest);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
