package com.jredu.myprojects.util;

/**
 * Created by Administrator on 2016/10/2.
 */
public class Constant {
    public static class SportInfo{
        public static final String TBL_NAME = "SPORTINFO";
        public static final String TBL_TITLE = "SPORTINFO_TITLE";
        public static final String TBL_CONTENT = "SPORTINFO_CONTENT";
        public static final String TBL_IMG = "SPORTINFO_IMG";
        public static String getCreatTableSQL(){
            String sql = "create table if not exists "
                    + TBL_NAME
                    + "("
                    + " _id integer primary key autoincrement, "
                    + TBL_TITLE + " text, "
                    + TBL_CONTENT + " text, "
                    + TBL_IMG + " varchar(50) "
                    + ")";
            return sql;
        }
    }
}
