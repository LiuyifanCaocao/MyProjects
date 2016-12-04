package com.jredu.myprojects.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jredu.myprojects.R;
import com.jredu.myprojects.fragment.Fragment_Find;
import com.jredu.myprojects.fragment.Fragment_My;
import com.jredu.myprojects.fragment.Fragment_RunCircle;
import com.jredu.myprojects.fragment.Fragment_run;
//主Activity 有5个fragment
public class FirstPageActivity extends FragmentActivity {
    Fragment_RunCircle fragment_runcircle;
    Fragment_Find fragment_find;
    Fragment_run fragment_run;
    Fragment_My fragment_my;
    Button button_runcircle;
    Button button_find;
    Button button_run;
    Button button_my;
    RadioGroup radioGroup;
    FragmentManager fm = null;
    FragmentTransaction ft = null;
    private long edittime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - edittime > 2000) {
                Toast.makeText(FirstPageActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                edittime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_pagelayout);
        button_runcircle = (Button) findViewById(R.id.sportCircle);
        button_find = (Button) findViewById(R.id.find);
        button_run = (Button) findViewById(R.id.sport);
        button_my = (Button) findViewById(R.id.my);
        fragment_runcircle = new Fragment_RunCircle();
        fragment_find = new Fragment_Find();
        fragment_run = new Fragment_run();
        fragment_my = new Fragment_My();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.framegent, fragment_run);
        ft.commit();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (checkedId) {
                    case R.id.sportCircle:
                        ft.replace(R.id.framegent, fragment_runcircle);
                        ft.commit();
                        break;
                    case R.id.find:
                        ft.replace(R.id.framegent, fragment_find);
                        ft.commit();
                        break;
                    case R.id.sport:
                        ft.replace(R.id.framegent, fragment_run);
                        ft.commit();
                        break;
                    case R.id.my:
                        ft.replace(R.id.framegent, fragment_my);
                        ft.commit();
                        break;
                }
            }
        });
    }

}
