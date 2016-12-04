package com.jredu.myprojects.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.jredu.myprojects.R;
//进入的主界面
public class MainActivity extends Activity {
    LocationManager lm;
    Button button;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.inter_dialoglayout);
                final Button noBtn = (Button) window.findViewById(R.id.inter_button_no);
                final Button yesBtn = (Button) window.findViewById(R.id.inter_button_yes);
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, StartActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (!isGpsAble(lm)) {
                            Toast.makeText(MainActivity.this, "请打开GPS~", Toast.LENGTH_SHORT).show();
                            openGPS2();
                        }
                    }
                });
                alertDialog.setCancelable(false);
            }
        });

        if (i == 0) {
            button.performClick();
            i++;
        }
    }
    private boolean isGpsAble(LocationManager lm) {
        return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ? true : false;
    }

    private void openGPS2() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==0){
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
