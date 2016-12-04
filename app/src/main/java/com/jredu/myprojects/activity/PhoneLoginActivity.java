package com.jredu.myprojects.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.myprojects.R;
import com.jredu.myprojects.baseadpater.MySQLiteOpenHelper;
import com.jredu.myprojects.util.Login;

public class PhoneLoginActivity extends Activity {
    public static final int PhoneLogin1 = 1003;
    Button phone_login;
    Button phone_login1;
    TextView accountText;
    EditText account;
    EditText pwd;
    String accountET;
    String pwdET;
    ImageView back;
    Bundle bundle;
    Intent intent;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        phone_login = (Button) findViewById(R.id.phone_login);//登录按钮
        phone_login1 = (Button) findViewById(R.id.phone_login1);//注册按钮
        account = (EditText) findViewById(R.id.account);
        pwd = (EditText) findViewById(R.id.pwd);
        accountText = (TextView) findViewById(R.id.accounText);
        back = (ImageView) findViewById(R.id.phone_login_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle.getString("name").equals("注册")) {
            phone_login1.setVisibility(View.VISIBLE);
            phone_login.setVisibility(View.GONE);
            accountText.setText("账号注册");
            phone_login1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accountET = account.getText().toString();
                    pwdET = pwd.getText().toString();
                    flag = query(accountET,pwdET);
                    if (flag&&accountET.length()>=6&&pwdET.length()>=6) {
                        Insert();
                        phone_login1.setVisibility(View.GONE);
                        phone_login.setVisibility(View.VISIBLE);
                        accountText.setText("账号登录");
                        login();
                        Toast.makeText(PhoneLoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(PhoneLoginActivity.this, "此账号已注册或账号密码不能少于6位数", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
           login();
        }
    }
    //登录
    public void login(){
        phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountET = account.getText().toString();
                pwdET = pwd.getText().toString();
                flag = query(accountET,pwdET);
                if (!flag) {
                    Toast.makeText(PhoneLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PhoneLoginActivity.this, FirstPageActivity.class);
                    bundle = new Bundle();
                    bundle.putString("flag", "phone");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    LoginActivity.instance.finish();
                } else {
                    Toast.makeText(PhoneLoginActivity.this, "请输入正确账号或密码登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //插入数据
    public void Insert() {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Login.LoginData.TBL_TITLE, accountET);
        contentValues.put(Login.LoginData.TBL_CONTENT, pwdET);
        sqLiteDatabase.insert(Login.LoginData.TBL_NAME, null, contentValues);

    }
    //查询数据
    public boolean query(String accountET,String pwdET) {
        flag = false;
        MySQLiteOpenHelper sqLiteOpenHelper = new MySQLiteOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query(
                Login.LoginData.TBL_NAME,
                new String[]{Login.LoginData.TBL_TITLE, Login.LoginData.TBL_CONTENT},
                null,
                null,
                null,
                null,
                null,
                null

        );
        if (cursor.getCount() == 0){
            flag = true;
        }else{
            while (cursor.moveToNext()) {
                int nameindex = cursor.getColumnIndex(Login.LoginData.TBL_TITLE);
                int numberindex = cursor.getColumnIndex(Login.LoginData.TBL_CONTENT);
                String name = cursor.getString(nameindex);
                if (name.equals(accountET)) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
        }
        return flag;
    }
    //返回
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
