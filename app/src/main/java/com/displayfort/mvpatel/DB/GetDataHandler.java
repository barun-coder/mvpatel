package com.displayfort.mvpatel.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.displayfort.mvpatel.Model.CategoryDao;

import java.util.ArrayList;

/**
 * Created by pc on 15/11/2018 18:22.
 * MVPatel
 */
public class GetDataHandler extends MasterDataHandler {
    private Context mContext;

    public GetDataHandler(Context context) {
        super(context);
        this.mContext = context;
    }

    public ArrayList<CategoryDao> getCategoryListByMaster(int MID) {
        ShowLog("getCategoryListByMaster");
        ArrayList<CategoryDao> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_CATEGORY, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    CategoryDao categoryDao = new CategoryDao();
//                    categoryDao.mCustomerLocalId = cursor.getString(cursor.getColumnIndex(DbCons.CUSTOMER_UNIQUE_ID));
//                    categoryDao.mCustomerServerId = cursor.getString(cursor.getColumnIndex(DbCons.CUSTOMER_SERVER_ID));
//                    categoryDao.mCustomerName = cursor.getString(cursor.getColumnIndex(DbCons.CUSTOMER_NAME));
//                    categoryDao.mCustomerCreationDate = cursor.getString(cursor.getColumnIndex(DbCons.CUSTOMER_CREATION_DATE));
//                    categoryDao.mCustomerAddress = cursor.getString(cursor.getColumnIndex(DbCons.CUSTOMER_ADDRESS));
//                    categoryDao.mCode = cursor.getString(cursor.getColumnIndex(DbCons.CUSTOMER_CODE));

                    if (categoryDao != null) {
                        contactList.add(categoryDao);

                    }
                }
            }
        } catch (Exception e) {
            ShowLog("Exception in getAllCategories" + e.getStackTrace());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
            databaseClose(db);
        }
        return contactList;
    }
}
