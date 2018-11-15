package com.displayfort.mvpatel.DB;


import com.displayfort.mvpatel.Utils.Utility;

public class DbQueries {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    private static final String INT_P_KEY_AUTOINC = " INTEGER PRIMARY KEY AUTOINCREMENT,";
    private static final String INT_P_KEY = " INTEGER PRIMARY KEY,";
    private static final String TEXT_NOT_NULL = " TEXT not null, ";
    private static final String INTEGER_NOT_NULL = " INTEGER not null ";
    private static final String OPEN_BRACES = "(";
    private static final String UNIQUE = " TEXT, ";//" TEXT UNIQUE,";
    private static final String INDEX = " INDEX";
    private static final String COMMA = ",";
    private static final String CLOSE_BRACES = ")";
    private static final String FOREIGN_KEY = " FOREIGN KEY";
    private static final String REFERENCES = " REFERENCES ";
    private static final String INTEGER = " INTEGER, ";
    private static final String BOOLEAN = " BOOLEAN, ";
    private static final String INTEGER__UNIQUE_NOT_NULL = " INTEGER UNIQUE not null,";
    private static final String TEXT = " TEXT, ";
    private static final String TEXT_PRIMARY_KEY = " TEXT PRIMARY KEY,";
    private static final String TEXT_NO_COMMA = " TEXT";
    private static final String TEXT_15 = " TEXT(15) ";
    private static final String NOT_NULL = " NOT NULL, ";
    private static final Object INTEGER_NO_COMMA = " Integer";

    /**/
    static String getCreateCategoryTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_CATEGORY);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons.CAT_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.CAT_NAME);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.NEW_ARRIVAL);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.SUBCAT_ID);//
        mBuffer.append(INTEGER_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /**/
    static String getCreateMasterCategoryTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_MASTER_CATEGORY);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons.CAT_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.CAT_NAME);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.NEW_ARRIVAL);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.SUBCAT_ID);//
        mBuffer.append(INTEGER_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /**/
    static String getCreateProductTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_PRODUCT);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons.PRODUCT_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.PRODUCT_NAME);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.PRODUCT_CODE);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.PRODUCT_DETAIL);//
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.NEW_ARRIVAL);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.SUBCAT_ID);//
        mBuffer.append(INTEGER_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }


}
