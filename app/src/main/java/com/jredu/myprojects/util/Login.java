package com.jredu.myprojects.util;

/**
 * Created by Administrator on 2016/10/18.
 */
public class Login {
    public static class LoginData {
        public static final String TBL_NAME = "LOGIN";
        public static final String TBL_TITLE = "LOGIN_TITLE";
        public static final String TBL_CONTENT = "LOGIN_CONTENT";


        public static String getCreatTableSQL() {
            String sql3 = "create table if not exists "
                    + TBL_NAME
                    + "("
                    + " _id integer primary key autoincrement, "
                    + TBL_TITLE + " text, "
                    + TBL_CONTENT + " text "
                    + ")";
            return sql3;
        }
    }
}
