package com.jredu.myprojects.baseadpater;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jredu.myprojects.util.Collection;
import com.jredu.myprojects.util.Constant;
import com.jredu.myprojects.util.Login;
import com.jredu.myprojects.util.PhoneData;

/**
 * Created by Administrator on 2016/10/2.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sportinfo.db";//数据库名称
    private static final int VERSION = 1;   //数据库版本号
    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //创建表
        String sql = Constant.SportInfo.getCreatTableSQL();
        String sql1 = Collection.CollectionData.getCreatTableSQL();
        String sql2 = PhoneData.PhoneDatas.getCreatTableSQL();
        String sql3 = Login.LoginData.getCreatTableSQL();
        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+Constant.SportInfo.TBL_NAME);
        onCreate(db);
    }
}
