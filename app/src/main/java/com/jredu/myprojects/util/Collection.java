package com.jredu.myprojects.util;

/**
 * Created by Administrator on 2016/10/8.
 */
public class Collection {
    public static class CollectionData {
        public static final String TBL_NAME = "COLLECTION";
        public static final String TBL_TITLE = "COLLECTION_TITLE";
        public static final String TBL_CONTENT = "COLLECTION_CONTENT";
        public static final String TBL_URL = "COLLECTION_URL";
        public static final String TBL_PRIURL = "COLLECTION_PRIURL";

        public static String getCreatTableSQL() {
            String sql1 = "create table if not exists "
                    + TBL_NAME
                    + "("
                    + " _id integer primary key autoincrement, "
                    + TBL_TITLE + " text, "
                    + TBL_CONTENT + " text, "
                    + TBL_PRIURL + " varchar(50), "
                    + TBL_URL + " varchar(50) "
                    + ")";
            return sql1;
        }
    }
}
