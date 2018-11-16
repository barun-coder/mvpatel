package com.displayfort.mvpatel.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.Model.Attachable;
import com.displayfort.mvpatel.Model.AttachmentListDao;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.SubCategory;

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

    public CategoryDao getCategoryDetail(int CId) {
        ShowLog("getCategoryListByMaster");
        CategoryDao categoryDao = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableCategory' where catId In (SELECT catId FROM 'MasterRelation' where ID=1);
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_CATEGORY + " where " + DbCons.CAT_ID + "=" + CId, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    categoryDao = new CategoryDao();
                    categoryDao.id = cursor.getInt(cursor.getColumnIndex(DbCons.CAT_ID));
                    categoryDao.name = cursor.getString(cursor.getColumnIndex(DbCons.CAT_NAME));
                    categoryDao.attachable = getAttachmeent(categoryDao);

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
        return categoryDao;
    }


    public ArrayList<CategoryDao> getCategoryListByMaster(int MID) {
        ShowLog("getCategoryListByMaster");
        ArrayList<CategoryDao> categoryDaos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableAttachment' where attach_ID=(SELECT attach_ID FROM 'AttachableRelation' where ID=1) AND type='COVERPIC';
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_ATTACHMENT + " where " + DbCons.ATTACHABLE_ID +
                            "=(SELECT " + DbCons.ATTACHABLE_ID + " FROM " + DbCons.TABLE_CAT_ATTACHABLE_REL + " where ID=" + categoryDao.id + ") AND type='COVERPIC'"
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

    /**/
    public SubCategory getSubCategoryDetail(int SCID) {
        ShowLog("getCategoryListByMaster");
        SubCategory subCategory = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableCategory' where catId In (SELECT catId FROM 'MasterRelation' where ID=1);
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_SUBCATEGORY + " where " + DbCons.SUBCAT_ID + "=" + SCID, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    subCategory = new SubCategory();
                    subCategory.id = cursor.getLong(cursor.getColumnIndex(DbCons.SUBCAT_ID));
                    subCategory.name = cursor.getString(cursor.getColumnIndex(DbCons.SUBCAT_NAME));
                    subCategory.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);
                    subCategory.title = cursor.getString(cursor.getColumnIndex(DbCons.TITLE));
                    subCategory.about = cursor.getString(cursor.getColumnIndex(DbCons.ABOUT));
                    subCategory.newArrival = (cursor.getInt(cursor.getColumnIndex(DbCons.NEW_ARRIVAL)) == 1);

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
        return subCategory;
    }

    /**/
    public ArrayList<SubCategory> getSubCategoryList(int CID) {
        ShowLog("getCategoryListByMaster");
        ArrayList<SubCategory> subCategories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableSubCategory' where catId =1;
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_SUBCATEGORY + " where " + DbCons.CAT_ID + "=" + CID, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SubCategory subCategory = new SubCategory();
                    subCategory.id = cursor.getLong(cursor.getColumnIndex(DbCons.SUBCAT_ID));
                    subCategory.name = cursor.getString(cursor.getColumnIndex(DbCons.SUBCAT_NAME));
                    subCategory.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);
                    subCategory.title = cursor.getString(cursor.getColumnIndex(DbCons.TITLE));
                    subCategory.about = cursor.getString(cursor.getColumnIndex(DbCons.ABOUT));
                    subCategory.newArrival = (cursor.getInt(cursor.getColumnIndex(DbCons.NEW_ARRIVAL)) == 1);
                    subCategory.attachable = getAttachmeent(subCategory);
                    if (subCategory != null) {
                        subCategories.add(subCategory);
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
        return subCategories;
    }


    public Attachable getAttachmeent(SubCategory subCategory) {
        ShowLog("getAttachmeent Sub");
        Attachable attachable = new Attachable();
        ArrayList<AttachmentListDao> listDaos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableAttachment' where attach_ID=(SELECT attach_ID FROM 'AttachableRelation' where ID=1) AND type='COVERPIC';
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_ATTACHMENT + " where " + DbCons.ATTACHABLE_ID +
                            "=(SELECT " + DbCons.ATTACHABLE_ID + " FROM " + DbCons.TABLE_SUBCAT_ATTACHABLE_REL + " where ID=" + subCategory.id + ") AND type='COVERPIC'"
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


    public ArrayList<Product> getProductList(int SID, int filter) {
        ShowLog("getProductList");
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + DbCons.TABLE_PRODUCT + " where " + DbCons.SUBCAT_ID + "=" + SID;
            switch (filter) {
                case Constant.ATOZ:
                    sql = "SELECT * FROM " + DbCons.TABLE_PRODUCT + " where " + DbCons.SUBCAT_ID + "=" + SID + " Order by P_Name ASC";
                    break;
                case Constant.ZTOA:
                    sql = "SELECT * FROM " + DbCons.TABLE_PRODUCT + " where " + DbCons.SUBCAT_ID + "=" + SID + " Order by P_Name DESC";
                    break;
                case Constant.HTOL:
                    sql = "SELECT TableProduct.*, MIN(TableProductType.Price) as Price FROM TableProduct" +
                            " INNER JOIN TableProductType ON TableProduct.P_ID=TableProductType.P_ID " +
                            "where TableProduct.subcatId=" + SID + " AND  TableProductType.color_ID=19" +
                            " group By TableProduct.P_ID " +
                            "Order BY  TableProductType.Price ASC";
                    break;
                case Constant.LTOH:
                    sql = "SELECT TableProduct.*, MIN(TableProductType.Price) as Price FROM TableProduct" +
                            " INNER JOIN TableProductType ON TableProduct.P_ID=TableProductType.P_ID " +
                            "where TableProduct.subcatId=" + SID + " AND  TableProductType.color_ID=19" +
                            " group By TableProduct.P_ID " +
                            "Order BY  TableProductType.Price DESC";
                    break;

            }
            cursor = db.rawQuery(sql, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Product product = new Product();
                    product.id = cursor.getLong(cursor.getColumnIndex(DbCons.PRODUCT_ID));
                    product.name = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_NAME));
                    product.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);
                    product.detail = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_DETAIL));
                    product.code = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_CODE));
                    product.subcatid = cursor.getLong(cursor.getColumnIndex(DbCons.SUBCAT_ID));
                    product.newArrival = (cursor.getInt(cursor.getColumnIndex(DbCons.NEW_ARRIVAL)) == 1);
                    product.attachable = getAttachmeent(product);
                    if (product != null) {
                        products.add(product);
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
        return products;
    }

    private Attachable getAttachmeent(Product product) {
        ShowLog("getAttachment product");
        Attachable attachable = new Attachable();
        ArrayList<AttachmentListDao> listDaos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableAttachment' where attach_ID=(SELECT attach_ID FROM 'AttachableRelation' where ID=1) AND type='COVERPIC';
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_ATTACHMENT + " where " + DbCons.ATTACHABLE_ID +
                            "=(SELECT " + DbCons.ATTACHABLE_ID + " FROM " + DbCons.TABLE_PRODUCT_ATTACHABLE_REL + " where ID=" + product.id + ") AND type='THUMBNAIL'"
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
