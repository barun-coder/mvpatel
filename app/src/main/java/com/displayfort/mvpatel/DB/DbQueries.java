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

    /*Category table*/
    static String getCreateCategoryTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_CATEGORY);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons.CAT_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.NEW_ARRIVAL);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.CAT_NAME);
        mBuffer.append(TEXT_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /*Master Table*/
    static String getCreateMasterCategoryTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_MASTER_CATEGORY);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons._ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.CAT_ID);//
        mBuffer.append(INTEGER_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /*Attachbale Relation*/
    static String getCreateCatAttachableTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_CAT_ATTACHABLE_REL);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons._ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.ATTACHABLE_ID);//
        mBuffer.append(INTEGER_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    static String getCreateSubAttachableTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_SUBCAT_ATTACHABLE_REL);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons._ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.ATTACHABLE_ID);//
        mBuffer.append(INTEGER_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    static String getCreateProductAttachableTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_PRODUCT_ATTACHABLE_REL);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons._ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.ATTACHABLE_ID);//
        mBuffer.append(INTEGER_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /*Attachment */
    static String getCreateAttachmentTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_ATTACHMENT);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons._ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.TYPE);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.URL);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.ATTACHABLE_ID);//
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.COLOR_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.TITLE);//
        mBuffer.append(TEXT_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /*SUBCategory*/
    static String getCreateSubCategoryTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_SUBCATEGORY);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons.SUBCAT_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.CAT_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.SUBCAT_NAME);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.NEW_ARRIVAL);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.TITLE);//
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.ABOUT);//
        mBuffer.append(TEXT_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /*Color*/
    static String getCreateColorTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_COLOR);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons._ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.TITLE);
        mBuffer.append(TEXT_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /*Product*/
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

    /*Product Price/Type*/
    static String getCreateProductPriceTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_PRODUCT_PRICE_TYPE);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons._ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.PRODUCT_ID);
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.PRODUCT_PRICE);
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.COLOR_ID);//0
        mBuffer.append(INTEGER_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /*Project*/
    static String getCreateProjectTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_PROJECT);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons.PROJECT_ID);//0
        mBuffer.append(INT_P_KEY_AUTOINC);
        mBuffer.append(DbCons.TITLE);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.DISCOUNT_TYPE);//
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.DISCOUNT_VALUE);//
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.CREATION);//0
        mBuffer.append(TEXT_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }


    /*Room Table*/
    static String getCreateRoomTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_ROOM);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons.ROOM_ID);//0
        mBuffer.append(INT_P_KEY_AUTOINC);
        mBuffer.append(DbCons.PROJECT_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.TITLE);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.CREATION);//0
        mBuffer.append(TEXT_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /*Order Detail Table*/
    static String getCreateOrderDetailTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_ORDER_DETAIL);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons.ORDER_ID);//0
        mBuffer.append(INT_P_KEY_AUTOINC);
        mBuffer.append(DbCons.PRODUCT_ID);
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.PRODUCT_NAME);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.PRODUCT_CODE);
        mBuffer.append(TEXT);
        mBuffer.append(DbCons.PRODUCT_DETAIL);//
        mBuffer.append(TEXT);
//        mBuffer.append(DbCons.DISCOUNT_TYPE);//
//        mBuffer.append(TEXT);
//        mBuffer.append(DbCons.DISCOUNT_VALUE);//
//        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.PRODUCT_TYPE_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.PRODUCT_PRICE);
        mBuffer.append(INTEGER);

        mBuffer.append(DbCons.COLOR_ID);
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.TITLE);
        mBuffer.append(TEXT);

        mBuffer.append(DbCons.ATTACHABLE_ID);
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.URL);
        mBuffer.append(TEXT);


        mBuffer.append(DbCons.STATUS);//
        mBuffer.append(BOOLEAN);
        mBuffer.append(DbCons.CREATION);//0
        mBuffer.append(TEXT_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }

    /*Order Table*/
    static String getCreateOrderTable() {
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(CREATE_TABLE);
        mBuffer.append(DbCons.TABLE_ORDER);
        mBuffer.append(OPEN_BRACES);
        mBuffer.append(DbCons._ID);//0
        mBuffer.append(INT_P_KEY_AUTOINC);
        mBuffer.append(DbCons.ORDER_ID);//0
        mBuffer.append(INTEGER);
        mBuffer.append(DbCons.ROOMID);//0
        mBuffer.append(INTEGER_NO_COMMA);
        mBuffer.append(CLOSE_BRACES);
        Utility.showLog(mBuffer.toString());
        return mBuffer.toString();
    }


}
