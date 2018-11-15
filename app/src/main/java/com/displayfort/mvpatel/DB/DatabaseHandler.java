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

import com.displayfort.mvpatel.Utils.Utility;


public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Name
    private static final String TRACKER_DB = "VT.db";
    // Database Version\
    private static SQLiteDatabase mSqLiteDatabase;
    private static final int VERSION = 7;//5
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
         * Creating the user table
         */

        db.execSQL(DbQueries.getCreateProductTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {

            String sql = "ALTER TABLE "
                    + DbCons.TABLE_OFFLINE_CUSTOMER + " ADD COLUMN " + DbCons.CUSTOMER_ORDER + " INTEGER";

            db.execSQL(sql);
            Utility.showLog("OnUpgrade successFul");
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


}
