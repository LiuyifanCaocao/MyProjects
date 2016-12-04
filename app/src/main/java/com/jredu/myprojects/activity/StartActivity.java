package com.jredu.myprojects.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jredu.myprojects.R;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends Activity {
TextView textView;
    Timer timer;
    TimerTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startlayout);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SpannableString spannableString = new SpannableString("咕咚直播  运动新秀场");
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),0,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView= (TextView) findViewById(R.id.title);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

         timer = new Timer();
         task = new TimerTask(){
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task,3000);
    }
}
