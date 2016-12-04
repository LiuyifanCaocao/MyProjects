/*
package com.jredu.myprojects.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jredu.myprojects.R;

public class ContactPersonActivity extends Activity{
ImageView contact_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_person);
        contact_add = (ImageView) findViewById(R.id.contact_add);
        contact_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactPersonActivity.this,GetPhoneListActivity.class);
                startActivity(intent);
            }
        });
    }


}
*/
