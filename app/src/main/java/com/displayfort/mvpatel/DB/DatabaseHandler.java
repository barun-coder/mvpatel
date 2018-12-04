/**
 * @author theBasics
 * Class Name: DatabaseHandler
 * Contains the handling of the whole database
 * Also contains the operations related to the database
 */
package com.displayfort.mvpatel.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Name
    private static final String TRACKER_DB = "MVPatel.db";
    // Database Version\
    private static SQLiteDatabase mSqLiteDatabase;
    private static final int VERSION = 10;//5
    private static final String TAG = "DatabaseHandler";
    private static final String DROP_IF_EXISTS = "DROP TABLE IF EXISTS ";
    private static final String FOREIGN_KEY_CONSTRAINT = "PRAGMA foreign_key=ON;";
//    private static File pathstring = new File(Environment.getExternalStorageDirectory(), "/NAVADMAP/NAVADDB");

    public DatabaseHandler(Context context) {
        super(context, TRACKER_DB, null, VERSION);
        mSqLiteDatabase = getWritableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        if (mSqLiteDatabase == null || !mSqLiteDatabase.isOpen()) {
            mSqLiteDatabase = super.getWritableDatabase();
        }
        while (mSqLiteDatabase.inTransaction()) {
        }
        return mSqLiteDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Creating table
         */
        db.execSQL(DbQueries.getCreateCategoryTable());
        db.execSQL(DbQueries.getCreateSubCategoryTable());
        db.execSQL(DbQueries.getCreateMasterCategoryTable());
        db.execSQL(DbQueries.getCreateCatAttachableTable());
        db.execSQL(DbQueries.getCreateSubAttachableTable());
        db.execSQL(DbQueries.getCreateProductAttachableTable());
        db.execSQL(DbQueries.getCreateAttachmentTable());
        db.execSQL(DbQueries.getCreateColorTable());
        db.execSQL(DbQueries.getCreateProductPriceTable());
        db.execSQL(DbQueries.getCreateProductTable());
        /**/
        db.execSQL(DbQueries.getCreateProjectTable());
        db.execSQL(DbQueries.getCreateRoomTable());
        db.execSQL(DbQueries.getCreateOrderDetailTable());
        db.execSQL(DbQueries.getCreateOrderTable());
        /* Insert data to a Table*/
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (1, 22);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (1, 10);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (1, 19);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (1, 20);");

        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (2, 1);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (2, 11);");

        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (3, 11);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (3, 12);");

        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (4, 17);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (4, 14);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (4, 15);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (4, 16);");

        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (4, 18);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (4, 19);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (4, 20);");
        db.execSQL("INSERT INTO " + DbCons.TABLE_MASTER_CATEGORY + " (" + DbCons._ID + ", " + DbCons.CAT_ID + ") VALUES (5, 21);");
//

    }

    private final String INTEGER_NO_COMMA = " Integer";
    private final String INTEGER = " INTEGER, ";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            if (newVersion > oldVersion) {
                db.execSQL("ALTER TABLE " + DbCons.TABLE_PROJECT + " ADD COLUMN " + DbCons.DISCOUNT_VALUE + " " + INTEGER_NO_COMMA);

                db.execSQL("ALTER TABLE " + DbCons.TABLE_PROJECT + " ADD COLUMN " + DbCons.DISCOUNT_TYPE + " " + INTEGER_NO_COMMA);
            }

        }

    }

    /**
     * DropReminder Table and Recreate
     */
    public void DropAndRecreateReminderTable() {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL(FOREIGN_KEY_CONSTRAINT);
    }

    public void databaseClose(SQLiteDatabase db) {
        try {
            if (db != null && db.isOpen()) {
                if (db.inTransaction()) {
                    db.setTransactionSuccessful();
                    db.endTransaction();
                }
                while (db.inTransaction()) {

                }

                db.close();

            }
        } catch (Exception e) {
            Log.e(TAG, "error databaseClose db : " + e);
            e.printStackTrace();
        }
    }

    public void clearDatabase(String TABLE_NAME) {
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(clearDBQuery);
    }


    public static void ShowLog(String text) {
        Log.i("DBUTILS:", text);
    }

    public static void ShowToast(String text, Context context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
/*
*    CREATE TABLE IF NOT EXISTS TableCategory(catId INTEGER, Status BOOLEAN, NewArrival BOOLEAN, catName TEXT)

     CREATE TABLE IF NOT EXISTS TableSubCategory(subcatId INTEGER, catId INTEGER, subcatName TEXT, Status BOOLEAN, NewArrival BOOLEAN, title TEXT, about TEXT)

     CREATE TABLE IF NOT EXISTS MasterRelation(ID INTEGER, catId Integer)

     CREATE TABLE IF NOT EXISTS AttachableRelation(ID INTEGER, attach_ID Integer)

     CREATE TABLE IF NOT EXISTS TableSubCategory(ID INTEGER, type TEXT, attachmentURL TEXT, color_ID INTEGER, Status BOOLEAN, title TEXT)

     CREATE TABLE IF NOT EXISTS TableColor(ID INTEGER, Status BOOLEAN, title TEXT)

     CREATE TABLE IF NOT EXISTS TableProductType(ID INTEGER, P_ID INTEGER, Price INTEGER, Status BOOLEAN, color_ID Integer)

     CREATE TABLE IF NOT EXISTS TableProduct(P_ID INTEGER, P_Name TEXT, Code TEXT, Detail TEXT, NewArrival BOOLEAN, Status BOOLEAN, subcatId Integer)

* */
}
