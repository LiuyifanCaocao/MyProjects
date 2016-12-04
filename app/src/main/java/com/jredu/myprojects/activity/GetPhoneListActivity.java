package com.jredu.myprojects.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.MySQLiteOpenHelper;
import com.jredu.myprojects.baseadpater.PhoneAdapter;
import com.jredu.myprojects.entity.Phone;
import com.jredu.myprojects.util.PhoneData;

import java.util.ArrayList;
import java.util.List;
//联系人添加好友
public class GetPhoneListActivity extends Activity implements View.OnClickListener {
    public static int GPLA = 1002;
    ListView contact_listview;
    PhoneAdapter phoneAdapter;
    ImageView add_phone_back;
    List<Phone> contact_list = new ArrayList<Phone>();
    Phone phone;
    Intent intent;
    Bundle bundle;
    SQLiteDatabase sqLiteDatabase;
    String displayname;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_list);
        add_phone_back = (ImageView) findViewById(R.id.add_phone_back);
        contact_listview = (ListView) findViewById(R.id.contact_listview);
        readContacts();
        phoneAdapter = new PhoneAdapter(this, contact_list);
        contact_listview.setAdapter(phoneAdapter);
        add_phone_back.setOnClickListener(this);
        contact_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent();
                bundle = new Bundle();
                bundle.putString("name", contact_list.get(position).getName());
                bundle.putString("number", contact_list.get(position).getNumber());
                intent.putExtras(bundle);
                setResult(GPLA, intent);
                finish();
                return true;
            }
        });
    }
//通过内容提供者获取手机联系人
    public void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                 displayname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                 number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = new Phone(displayname, number);
                contact_list.add(phone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_phone_back:
                onBackPressed();
                finish();
        }
    }
    public void Insert(int position){
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PhoneData.PhoneDatas.TBL_PHONENAME,contact_list.get(position).getName());
        contentValues.put(PhoneData.PhoneDatas.TBL_PHONENUMBER,contact_list.get(position).getNumber());
        sqLiteDatabase.insert(PhoneData.PhoneDatas.TBL_NAME,null,contentValues);

    }
}
