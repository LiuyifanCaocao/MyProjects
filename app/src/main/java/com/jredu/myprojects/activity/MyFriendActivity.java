package com.jredu.myprojects.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.MySQLiteOpenHelper;
import com.jredu.myprojects.baseadpater.PhoneAdapter;
import com.jredu.myprojects.entity.Phone;
import com.jredu.myprojects.util.PhoneData;

import java.util.ArrayList;
import java.util.List;

public class MyFriendActivity extends Activity {
    private static int TAG = 1001;
    public static int GPLA = 1002;
    ImageView my_friendImg;
    TextView add_friend;
    TextView my_friendText;
    ListView myFriendList;
    ImageView my_friendBack;
    PhoneAdapter phoneAdapter;
    List<Phone> phoneList = new ArrayList<Phone>();
    Phone phone;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    String[] names = {"删除好友", "拨打电话"};
    String selectedName = "删除好友";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);
        my_friendImg = (ImageView) findViewById(R.id.my_friendImg);
        my_friendText = (TextView) findViewById(R.id.my_friendText);
        myFriendList = (ListView) findViewById(R.id.myFriendList);
        add_friend = (TextView) findViewById(R.id.add_friend);
        my_friendBack = (ImageView) findViewById(R.id.my_friend_back);
        my_friendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        query();
        panDuan(phoneList);
        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyFriendActivity.this, GetPhoneListActivity.class);
                startActivityForResult(intent, TAG);
            }
        });
        myFriendList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MyFriendActivity.this)
                        .setTitle("请选择要进行的操作")
                        .setSingleChoiceItems(names, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedName = names[which];
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               if (selectedName.equals("删除好友")){
                                   Toast.makeText(MyFriendActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                   delete(position);
                                   phoneList.remove(position);
                                   phoneAdapter.notifyDataSetChanged();
                                   panDuan(phoneList);
                               }else {
                                   Intent intent = new Intent();
                                   intent.setAction(intent.ACTION_CALL);
                                   intent.setData(Uri.parse("tel:"+phoneList.get(position).getNumber()));
                                   startActivity(intent);
                               }
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean flag = true;
        if (requestCode == TAG && resultCode == GPLA) {
            Bundle bundle = data.getExtras();
            String name = (String) bundle.get("name");
            String number = (String) bundle.get("number");
            phone = new Phone(name, number);
            for (int i = 0; i < phoneList.size(); i++) {
                if (phoneList.get(i).getName().equals(name) && phoneList.get(i).getNumber().equals(number)) {
                    Toast.makeText(MyFriendActivity.this, "已存在好友，无需添加", Toast.LENGTH_SHORT).show();
                    flag = false;
                    return;
                } else {
                    flag = true;
                }
            }
            if (flag) {
                Toast.makeText(MyFriendActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                phoneList.add(0, phone);
                panDuan(phoneList);
                Insert();
            }
            phoneAdapter = new PhoneAdapter(this, phoneList);
            myFriendList.setAdapter(phoneAdapter);
        }
    }

    public void panDuan(List<Phone> phoneList) {
        if (phoneList.size() > 0) {
            my_friendImg.setVisibility(View.GONE);
            my_friendText.setVisibility(View.GONE);
        } else {
            my_friendImg.setVisibility(View.VISIBLE);
            my_friendText.setVisibility(View.VISIBLE);
        }
    }

    public void query() {
        MySQLiteOpenHelper sqLiteOpenHelper = new MySQLiteOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query(
                PhoneData.PhoneDatas.TBL_NAME,
                new String[]{PhoneData.PhoneDatas.TBL_PHONENAME, PhoneData.PhoneDatas.TBL_PHONENUMBER},
                null,
                null,
                null,
                null,
                null,
                null

        );
        while (cursor.moveToNext()) {
            int nameindex = cursor.getColumnIndex(PhoneData.PhoneDatas.TBL_PHONENAME);
            int numberindex = cursor.getColumnIndex(PhoneData.PhoneDatas.TBL_PHONENUMBER);
            String name = cursor.getString(nameindex);
            String number = cursor.getString(numberindex);
            phone = new Phone(name, number);
            phoneList.add(phone);
            phoneAdapter = new PhoneAdapter(this, phoneList);
            myFriendList.setAdapter(phoneAdapter);
        }
    }

    public void delete(int position) {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this);//调用帮助类的构造函数 创建数据库
        sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();//调用帮助类的onCreate 创建表
        String name1 = phoneList.get(position).getName();
        String number1 = phoneList.get(position).getNumber();
        sqLiteDatabase.delete(
                PhoneData.PhoneDatas.TBL_NAME,
/*
                PhoneData.PhoneDatas.TBL_PHONENAME + " = ? & " + PhoneData.PhoneDatas.TBL_PHONENUMBER + " = ? ",
*/
                PhoneData.PhoneDatas.TBL_PHONENAME + " = ? ",
                new String[]{name1}
        );
        //sqLiteDatabase.execSQL("DELETE FROM PHONE WHERE PHONENAME = ? & PHONENUMBER = ? ",new String[]{name1,number1});

        sqLiteDatabase.close();
    }

    public void Insert() {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PhoneData.PhoneDatas.TBL_PHONENAME, phoneList.get(0).getName());
        contentValues.put(PhoneData.PhoneDatas.TBL_PHONENUMBER, phoneList.get(0).getNumber());
        sqLiteDatabase.insert(PhoneData.PhoneDatas.TBL_NAME, null, contentValues);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
