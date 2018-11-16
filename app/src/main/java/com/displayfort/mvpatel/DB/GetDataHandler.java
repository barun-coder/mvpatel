package com.displayfort.mvpatel.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.displayfort.mvpatel.Model.Attachable;
import com.displayfort.mvpatel.Model.AttachmentListDao;
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
        ArrayList<CategoryDao> categoryDaos = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableCategory' where catId In (SELECT catId FROM 'MasterRelation' where ID=1);
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_CATEGORY + " where " + DbCons.CAT_ID + " In " +
                    "(SELECT " + DbCons.CAT_ID + " FROM " + DbCons.TABLE_MASTER_CATEGORY + " where ID=" + MID + ")", null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    CategoryDao categoryDao = new CategoryDao();
                    categoryDao.id = cursor.getInt(cursor.getColumnIndex(DbCons.CAT_ID));
                    categoryDao.name = cursor.getString(cursor.getColumnIndex(DbCons.CAT_NAME));
                    categoryDao.attachable = getAttachmeent(categoryDao);
                    if (categoryDao != null) {
                        categoryDaos.add(categoryDao);
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
        return categoryDaos;
    }

    /*get attachment*/
    public Attachable getAttachmeent(CategoryDao categoryDao) {
        ShowLog("getCategoryListByMaster");
        Attachable attachable = new Attachable();
        ArrayList<AttachmentListDao> listDaos = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableAttachment' where attach_ID=(SELECT attach_ID FROM 'AttachableRelation' where ID=1) AND type='COVERPIC';
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_ATTACHMENT + " where " + DbCons.ATTACHABLE_ID +
                            "=(SELECT " + DbCons.ATTACHABLE_ID + " FROM " + DbCons.TABLE_ATTACHABLE_REL + " where ID=" + categoryDao.id + ") AND type='COVERPIC'"
                    , null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    AttachmentListDao attachmentListDao = new AttachmentListDao();
                    attachmentListDao.id = cursor.getInt(cursor.getColumnIndex(DbCons._ID));
                    attachmentListDao.type = cursor.getString(cursor.getColumnIndex(DbCons.TYPE));
                    attachmentListDao.attachmentURL = cursor.getString(cursor.getColumnIndex(DbCons.URL));
                    attachmentListDao.attachableid = cursor.getInt(cursor.getColumnIndex(DbCons.ATTACHABLE_ID));
                    try {
                        attachmentListDao.colorID = cursor.getInt(cursor.getColumnIndex(DbCons.COLOR_ID));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (attachmentListDao != null) {
                        listDaos.add(attachmentListDao);
                    }
                }
                attachable.attachmentList = listDaos;
            }
        } catch (Exception e) {
            ShowLog("Exception in getAllCategories" + e.getStackTrace());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
//            databaseClose(db);
        }
        return attachable;
    }

}
