package com.jredu.myprojects.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.myprojects.R;
import com.jredu.myprojects.Service.MusicService;
import com.jredu.myprojects.activity.MusicPlayerActivity;
import com.jredu.myprojects.activity.RunActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_run extends Fragment {
    LocationManager lm;
    ImageButton sportBtn;
    RadioButton run_radioBtn;
    RadioButton run_radioBtn1;
    RadioButton run_radioBtn2;
    RadioButton run_radioBtn3;
    RadioButton run_radioBtn4;
    RadioButton run_radioBtn5;
    RadioButton reset;
    RadioButton addmusic;
    TextView run_delete;
    TextView runTime;
    Button startsport;
    TextView sportText;
    TextView sportAcount;
    ImageView set;
    SharedPreferences sp = null;
    String time;
    String s = null;
    int style = 0;
    int acounts = 0;
    int count = 0;
    int i=0;
    public static final int RUN = 1;
    public static final int INTERRUN = 2;
    public static final int WALK = 3;
    public static final int RIDE = 4;
    public static final int SKIING = 5;
    public static final int SKATE = 6;
    public static final int COUNT0 = 0;
    public static final int COUNT1 = 1;

    public Fragment_run() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_run, container, false);
        IntentFilter intentFilter = new IntentFilter("com.my");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
        sportBtn = (ImageButton) v.findViewById(R.id.sportBtn);
        sportText = (TextView) v.findViewById(R.id.sportText);
        runTime = (TextView) v.findViewById(R.id.text);
        sportAcount = (TextView) v.findViewById(R.id.text3);
        startsport = (Button) v.findViewById(R.id.startsport);
        set = (ImageView) v.findViewById(R.id.set);
        openGps();
        sp = getActivity().getSharedPreferences("musiccount", Context.MODE_PRIVATE);
        time = sp.getString("time", "0.0");
        runTime.setText(time);
        sportAcount.setText(String.valueOf(sp.getInt("acounts",0)));
        sp = getActivity().getSharedPreferences("musiccount", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        editor.putInt("count", count);
        editor.commit();
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(Fragment_run.super.getActivity()).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.x = 0;
                wl.y = 500;
                wl.width = WindowManager.LayoutParams.MATCH_PARENT;
                wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wl);
                window.setContentView(R.layout.run_settinglayout);
                reset = (RadioButton) window.findViewById(R.id.reset);
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putInt("acounts", 0);
                        editor.putString("time","0");
                        editor.commit();
                        time = sp.getString("time", null);
                        runTime.setText(time);
                        sportAcount.setText(String.valueOf(sp.getInt("acounts", 0)));
                        alertDialog.dismiss();
                    }
                });
                addmusic = (RadioButton) window.findViewById(R.id.set_music);
                addmusic.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(Fragment_run.super.getContext(), MusicPlayerActivity.class);
                        startActivity(intent);
                        alertDialog.dismiss();
                        return false;
                    }
                });
                addmusic.setText(sp.getString("text", "播放音乐"));
                addmusic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sp = getActivity().getSharedPreferences("musiccount", Context.MODE_PRIVATE);
                        int count1 = sp.getInt("count", count);
                        Intent intent = new Intent(Fragment_run.super.getContext(), MusicService.class);
                        if ((count1 % 2) == COUNT0) {
                            getContext().startService(intent);
                            editor.putString("text", "关闭音乐");
                            editor.commit();
                            count1++;
                        } else if ((count1 % 2) == COUNT1) {
                            getContext().stopService(intent);
                            editor.putString("text", "播放音乐");
                            editor.commit();
                            count1++;
                        }
                        sp = getActivity().getSharedPreferences("musiccount", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("count", count1);
                        editor.commit();
                        alertDialog.dismiss();
                    }
                });

            }
        });
        selectExercise();
        return v;
    }

    public String IconChange(View v) {
        sp = getActivity().getSharedPreferences("sportType", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (v.getId() == R.id.run_radioBtn) {
            sportBtn.setImageResource(R.drawable.new_icon_running);
            s = "run";
        } else if (v.getId() == R.id.run_radioBtn1) {
            sportBtn.setImageResource(R.drawable.new_icon_interrun);
            s = "interRun";
        } else if (v.getId() == R.id.run_radioBtn2) {
            sportBtn.setImageResource(R.drawable.new_icon_walk);
            s = "walk";
        } else if (v.getId() == R.id.run_radioBtn3) {
            sportBtn.setImageResource(R.drawable.new_icon_ride);
            s = "ride";
        } else if (v.getId() == R.id.run_radioBtn4) {
            sportBtn.setImageResource(R.drawable.new_icon_skiing);
            s = "skiing";
        } else if (v.getId() == R.id.run_radioBtn5) {
            sportBtn.setImageResource(R.drawable.new_icon_skate);
            s = "skate";
        }
        editor.putString("type", s);
        editor.commit();
        return s;
    }
    /*是否打开GPS*/
    public void openGps(){
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        startsport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGpsAble(lm)) {
                    Toast.makeText(getContext(), "请打开GPS~", Toast.LENGTH_SHORT).show();
                    openGPS2();
                } else {
                    Intent intent = new Intent(getContext(), RunActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    /*选择运动类型*/
    public void selectExercise(){
        sportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(Fragment_run.super.getActivity()).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.x = 0;
                wl.y = 500;
                wl.width = WindowManager.LayoutParams.MATCH_PARENT;
                wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wl);
                window.setContentView(R.layout.fragment_run_dialoglayout);
                run_delete = (TextView) window.findViewById(R.id.run_delete);
                run_radioBtn = (RadioButton) window.findViewById(R.id.run_radioBtn);
                run_radioBtn1 = (RadioButton) window.findViewById(R.id.run_radioBtn1);
                run_radioBtn2 = (RadioButton) window.findViewById(R.id.run_radioBtn2);
                run_radioBtn3 = (RadioButton) window.findViewById(R.id.run_radioBtn3);
                run_radioBtn4 = (RadioButton) window.findViewById(R.id.run_radioBtn4);
                run_radioBtn5 = (RadioButton) window.findViewById(R.id.run_radioBtn5);
                if (i == 0) {
                    run_radioBtn.setChecked(true);
                    sportBtn.setImageResource(R.drawable.new_icon_running);
                    i++;
                }
                if (style == RUN) {
                    run_radioBtn.setChecked(true);
                    sportBtn.setImageResource(R.drawable.new_icon_running);
                } else if (style == INTERRUN) {
                    run_radioBtn1.setChecked(true);
                    sportBtn.setImageResource(R.drawable.new_icon_interrun);
                } else if (style == WALK) {
                    run_radioBtn2.setChecked(true);
                    sportBtn.setImageResource(R.drawable.new_icon_walk);
                } else if (style == RIDE) {
                    run_radioBtn3.setChecked(true);
                    sportBtn.setImageResource(R.drawable.new_icon_ride);
                } else if (style == SKIING) {
                    run_radioBtn4.setChecked(true);
                    sportBtn.setImageResource(R.drawable.new_icon_skiing);
                } else if (style == SKATE) {
                    run_radioBtn5.setChecked(true);
                    sportBtn.setImageResource(R.drawable.new_icon_skate);
                }
                run_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                run_radioBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startsport.setText("开始\n跑步");
                        sportText.setText("跑步总里程(公里)");
                        IconChange(v);
                        panduan(s);
                        alertDialog.hide();

                    }
                });
                run_radioBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startsport.setText("开始\n跑步");
                        sportText.setText("跑步总里程(公里)");
                        IconChange(v);
                        panduan(s);
                        alertDialog.hide();

                    }
                });
                run_radioBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startsport.setText("开始\n走路");
                        sportText.setText("走路总里程(公里)");
                        IconChange(v);
                        panduan(s);
                        alertDialog.hide();

                    }
                });
                run_radioBtn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startsport.setText("开始\n骑行");
                        sportText.setText("骑行总里程(公里)");
                        IconChange(v);
                        panduan(s);
                        alertDialog.hide();

                    }
                });
                run_radioBtn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startsport.setText("开始\n滑雪");
                        sportText.setText("滑雪总里程(公里)");
                        IconChange(v);
                        panduan(s);
                        alertDialog.hide();

                    }
                });
                run_radioBtn5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startsport.setText("开始\n滑冰");
                        sportText.setText("滑冰总里程(公里)");
                        IconChange(v);
                        panduan(s);
                        alertDialog.hide();
                    }
                });
            }
        });

    }

    public void panduan(String s) {
        sp = getActivity().getSharedPreferences("sportType", Context.MODE_PRIVATE);
        String type = sp.getString("type", s);
        sp = getActivity().getSharedPreferences("sportType", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp.edit();
        if (type.equals("run")) {
            style = 1;
        } else if (type.equals("interRun")) {
            style = 2;
        } else if (type.equals("walk")) {
            style = 3;
        } else if (type.equals("ride")) {
            style = 4;
        } else if (type.equals("skiing")) {
            style = 5;
        } else if (type.equals("skate")) {
            style = 6;
        }
        editor1.putInt("id", style);
        editor1.commit();
    }

    private boolean isGpsAble(LocationManager lm) {
        return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ? true : false;
    }

    private void openGPS2() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            time = "00:" + intent.getExtras().getString("data");
            sp = getActivity().getSharedPreferences("musiccount", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            acounts++;
            editor.putInt("acounts", acounts);
            editor.putString("time", time);
            editor.commit();
            runTime.setText(time);
            sportAcount.setText(String.valueOf(acounts));
        }
    };

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
