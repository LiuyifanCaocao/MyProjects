package com.jredu.myprojects.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.myprojects.R;
import com.jredu.myprojects.activity.LoginActivity;
import com.jredu.myprojects.activity.MyCollectActivity;
import com.jredu.myprojects.activity.MyConnectionActivity;
import com.jredu.myprojects.activity.MyFriendActivity;
import com.jredu.myprojects.baseadpater.MyMsgAdapter;
import com.jredu.myprojects.entity.MyMsg;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_My extends Fragment {
    RelativeLayout my_relativeLayout;
    ImageView my_img;
    ListView my_listview;
    List<MyMsg> list;
    MyMsg myMsg;
    TextView my_name;
    Bundle bundle;
    Intent intent;
    Bitmap bitmap = null;
    HttpURLConnection conn = null;
    InputStream is = null;

    public Fragment_My() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment__my, container, false);
        my_img = (ImageView) v.findViewById(R.id.my_img);
        my_name = (TextView) v.findViewById(R.id.my_QQname);
        my_listview = (ListView) v.findViewById(R.id.my_listview);
        my_relativeLayout = (RelativeLayout) v.findViewById(R.id.my_relativeLayout);
        intent = getActivity().getIntent();
        bundle = intent.getExtras();
        final String flag = bundle.getString("flag");
        if (flag.equals("qq")) {
            my_name.setText(bundle.getString("name"));
            String imageUri = bundle.getString("icon");
            MyImgThread imgThread = new MyImgThread(imageUri);
            Thread thread = new Thread(imgThread);
            thread.start();
        }
        my_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("sina")){
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        list = new ArrayList<MyMsg>();
        myMsg = new MyMsg("我的好友", " ", R.drawable.jiantou);
        list.add(myMsg);
        myMsg = new MyMsg("我的关注", " ", R.drawable.jiantou);
        list.add(myMsg);
        myMsg = new MyMsg("我的收藏", " ", R.drawable.jiantou);
        list.add(myMsg);
        myMsg = new MyMsg("检查更新", "V1.1.2", R.drawable.jiantou);
        list.add(myMsg);
        MyMsgAdapter myMsgAdapter = new MyMsgAdapter(getContext(), list);
        my_listview.setAdapter(myMsgAdapter);
        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!flag.equals("sina")) {
                    switch (list.get(position).getTitle()) {
                        case "我的好友":
                            Intent intent = new Intent(getContext(), MyFriendActivity.class);
                            startActivity(intent);
                            break;
                        case "我的关注":
                            Intent intent1 = new Intent(getContext(), MyConnectionActivity.class);
                            startActivity(intent1);
                            break;
                        case "我的收藏":
                            Intent intent2 = new Intent(getContext(), MyCollectActivity.class);
                            startActivity(intent2);
                            break;
                        case "检查更新":
                            checked();
                            break;
                    }
                }else {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        return v;
    }

    public void checked(){
       Toast.makeText(getContext(),"你的咕咚已是最新版本",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
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
                my_img.setImageBitmap(bitmap);
            }
        }
    };

}
