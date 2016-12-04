package com.jredu.myprojects.util;

/**
 * Created by Administrator on 2016/10/14.
 */
public class PhoneData {
    public static class PhoneDatas{
        public static final String TBL_NAME = "PHONE";
        public static final String TBL_PHONENAME = "PHONENAME";
        public static final String TBL_PHONENUMBER = "PHONENUMBER";

        public static String getCreatTableSQL(){
            String sql2 = "create table if not exists "
                    + TBL_NAME
                    + "("
                    + " _id integer primary key autoincrement, "
                    + TBL_PHONENAME + " text, "
                    + TBL_PHONENUMBER + " text "
                    + ")";
            return sql2;
        }
    }
}
