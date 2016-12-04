package com.jredu.myprojects.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.MySQLiteOpenHelper;
import com.jredu.myprojects.util.Constant;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//滚动视图
public class OfficialMatchActivity extends Activity {
    TextView match_title;
    TextView match_content;
    TextView match_ride_title;
    TextView match_ride_content;
    TextView match_offline_title;
    TextView match_offline_content;
    ImageView match_img;
    ImageView match_ride_img;
    ImageView match_offline_img;
    ImageView match_back;
    private ViewPager vp;
    private LinearLayout dotcontaint;
    ArrayList<ImageView> imgs;
    ArrayList<View> dots;
    int currentIndex = 0;
    int olderIndex = 0;
    private boolean isContinue = true;
    int m = 0;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;

    //图片数据
    private String[] image_url = new String[]{
            "http://p1.pstatp.com/large/ef90002ff44e55e990b.png",
            "http://p3.pstatp.com/large/e4d0007ca86759d9b4c.png",
            "http://p1.pstatp.com/large/e8e00073c48f6a12f19.png",
            "http://p9.pstatp.com/large/e5000010d3b64673e7d.png"};

    Handler h = new Handler() {
        public void handleMessage(android.os.Message msg) {
            vp.setCurrentItem(currentIndex);//设置此次要显示的pager
            //切换选中的圆点
            dots.get(olderIndex).setBackgroundResource(R.drawable.dot_nomal);//设置上次选中的圆点为不选中
            dots.get(currentIndex % image_url.length).setBackgroundResource(R.drawable.dot_focus);//设置当前选中的圆点为选中
            olderIndex = currentIndex % image_url.length;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_match);
        match_title = (TextView) findViewById(R.id.match_title);
        match_content = (TextView) findViewById(R.id.match_content);
        match_ride_title = (TextView) findViewById(R.id.match_ride_title);
        match_ride_content = (TextView) findViewById(R.id.match_ride_content);
        match_offline_title = (TextView) findViewById(R.id.match_offline_title);
        match_offline_content = (TextView) findViewById(R.id.match_offline_content);
        match_img = (ImageView) findViewById(R.id.match_img);
        match_ride_img = (ImageView) findViewById(R.id.match_ride_img);
        match_offline_img = (ImageView) findViewById(R.id.match_offline_img);
        match_back = (ImageView) findViewById(R.id.offical_back);
        match_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        if (m == 0) {
            query();
            cursor.close();
            sqLiteDatabase.close();
            m++;
        }
        init();
    }

    public void init() {
        vp = (ViewPager) findViewById(R.id.vp);
        dotcontaint = (LinearLayout) findViewById(R.id.dotcontaint);
        //获得网络图片，配置给Veiwpager
        getImageViewList();
        //获得圆点
        getDotList();
        //设置第一个圆点为选中状态
        dots.get(0).setBackgroundResource(R.drawable.dot_focus);
        vp.setAdapter(new MyVpAdapger());//配置pager页
        int currentItem = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % image_url.length;
        vp.setCurrentItem(currentItem);
        //通过定时器，制作自动划动效果
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentIndex = vp.getCurrentItem() + 1;//下一个页
                if (isContinue) {
                    h.sendEmptyMessage(0x123);//在此线程中，不能操作ui主线程
                }
            }
        }, 0, 3000);

    }

    private void getDotList() {
        // TODO Auto-generated method stub
        dots = new ArrayList<View>();
        //循环图片数组，创建对应数量的dot
        for (int i = 0; i < image_url.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.dot_layout, null);//加载布局
            View dot = view.findViewById(R.id.dotView);//得到布局中的dot点组件
            //收集dot
            dots.add(dot);
            //把布局添加到线性布局
            dotcontaint.addView(view);
        }

    }

    /**
     * 到网络获得图片信息，并赋值到ImageView中
     */
    private void getImageViewList() {
        // TODO Auto-generated method stub
        imgs = new ArrayList<ImageView>();

        BitmapUtils btUtil = new BitmapUtils(this);
        //加载图片
        for (int i = 0; i < image_url.length; i++) {
            ImageView img = new ImageView(this);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            btUtil.display(img, image_url[i]);
            imgs.add(img);
        }
    }

    class MyVpAdapger extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
//            super.destroyItem(container, position, object);
//            ImageView img = imgs.get(position);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            int item = position % image_url.length;
            container.addView(imgs.get(item));
            return imgs.get(item);
        }

    }


    public void query() {
        MySQLiteOpenHelper sqLiteOpenHelper = new MySQLiteOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query(
                Constant.SportInfo.TBL_NAME,
                new String[]{Constant.SportInfo.TBL_TITLE, Constant.SportInfo.TBL_CONTENT, Constant.SportInfo.TBL_IMG},
                null,
                null,
                null,
                null,
                null,
                null

        );
        while (cursor.moveToNext()) {
            int titleindex = cursor.getColumnIndex(Constant.SportInfo.TBL_TITLE);
            int contentindex = cursor.getColumnIndex(Constant.SportInfo.TBL_CONTENT);
            int imgindex = cursor.getColumnIndex(Constant.SportInfo.TBL_IMG);

            String title = cursor.getString(titleindex);
            String content = cursor.getString(contentindex);
            int img = cursor.getInt(imgindex);
            if (title.equals("线上赛事")) {
                match_title.setText(title);
                match_content.setText(content);
                match_img.setImageResource(img);
            } else if (title.equals("骑行赛事")) {
                match_ride_title.setText(title);
                match_ride_content.setText(content);
                match_ride_img.setImageResource(img);
            } else if (title.equals("线下赛事")) {
                match_offline_title.setText(title);
                match_offline_content.setText(content);
                match_offline_img.setImageResource(img);
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

