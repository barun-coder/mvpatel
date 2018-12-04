package com.displayfort.mvpatel.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.Model.Attachable;
import com.displayfort.mvpatel.Model.AttachmentListDao;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.Color;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.ProductPrice;
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

    public ArrayList<CategoryDao> getAllCategoryList() {
        ShowLog("getAllCategoryList");
        ArrayList<CategoryDao> categoryDaos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableCategory' where catId In (SELECT catId FROM 'MasterRelation' where ID=1);
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_CATEGORY, null);
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

    /**/
    public Product getProductDetail(int PID) {
        ShowLog("getProductDetail");
        Product product = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + DbCons.TABLE_PRODUCT + " where " + DbCons.PRODUCT_ID + "=" + PID;
            cursor = db.rawQuery(sql, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    product = new Product();
                    product.id = cursor.getLong(cursor.getColumnIndex(DbCons.PRODUCT_ID));
                    product.name = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_NAME));
                    product.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);
                    product.detail = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_DETAIL));
                    product.code = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_CODE));
                    product.subcatid = cursor.getLong(cursor.getColumnIndex(DbCons.SUBCAT_ID));
                    product.newArrival = (cursor.getInt(cursor.getColumnIndex(DbCons.NEW_ARRIVAL)) == 1);
//                    product.productPrices = getProductList(product.id);
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
        return product;
    }

    public ArrayList<ProductPrice> getProductList(Long pid, boolean OnlyImage) {
        ShowLog("getAttachment product");
        ArrayList<ProductPrice> listDaos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT TableProductType.*, TableAttachment.attach_ID,TableAttachment.type,TableAttachment.attachmentURL
            // FROM TableProductType
            // left JOIN TableAttachment ON TableProductType.color_ID = TableAttachment.color_ID
            // where TableProductType.P_ID=224 AND TableAttachment.attach_ID=
            // (SELECT attach_ID FROM 'AttachableProductRelation' where ID=224)
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT " + DbCons.TABLE_PRODUCT_PRICE_TYPE + ".*, " + DbCons.TABLE_ATTACHMENT + "." + DbCons.ATTACHABLE_ID + "," + DbCons.TABLE_ATTACHMENT + "." + DbCons.TYPE + "," + DbCons.TABLE_ATTACHMENT + "." + DbCons.URL);
            sql.append(" FROM " + DbCons.TABLE_PRODUCT_PRICE_TYPE);
            sql.append(" left JOIN " + DbCons.TABLE_ATTACHMENT + " ON " + DbCons.TABLE_PRODUCT_PRICE_TYPE + "." + DbCons.COLOR_ID + " = " + DbCons.TABLE_ATTACHMENT + "." + DbCons.COLOR_ID);
            sql.append(" where " + DbCons.TABLE_PRODUCT_PRICE_TYPE + "." + DbCons.PRODUCT_ID + "=" + pid + " AND " + DbCons.TABLE_ATTACHMENT + "." + DbCons.ATTACHABLE_ID + "=(SELECT " + DbCons.ATTACHABLE_ID + " FROM " + DbCons.TABLE_PRODUCT_ATTACHABLE_REL + " where " + DbCons._ID + "=" + pid + ")");
            sql.append(" Order BY TableProductType.Price");
            cursor = db.rawQuery(sql.toString(), null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ProductPrice productPrice = new ProductPrice();
                    try {
                        productPrice.colorID = cursor.getInt(cursor.getColumnIndex(DbCons.COLOR_ID));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    productPrice.price = cursor.getInt(cursor.getColumnIndex(DbCons.PRODUCT_PRICE));
                    productPrice.productId = cursor.getInt(cursor.getColumnIndex(DbCons.PRODUCT_ID));
                    productPrice.id = cursor.getInt(cursor.getColumnIndex(DbCons._ID));
                    productPrice.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);

                    AttachmentListDao attachmentListDao = new AttachmentListDao();
                    attachmentListDao.type = cursor.getString(cursor.getColumnIndex(DbCons.TYPE));
                    attachmentListDao.attachmentURL = cursor.getString(cursor.getColumnIndex(DbCons.URL));
                    attachmentListDao.attachableid = cursor.getInt(cursor.getColumnIndex(DbCons.ATTACHABLE_ID));
                    try {
                        attachmentListDao.colorID = cursor.getInt(cursor.getColumnIndex(DbCons.COLOR_ID));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    productPrice.attachmentListDao = attachmentListDao;
                    String type = cursor.getString(cursor.getColumnIndex(DbCons.TYPE));
                    if (OnlyImage && (!(type.equalsIgnoreCase("PDF")) && (!type.equalsIgnoreCase("CAD")))) {
                        if (attachmentListDao != null) {
                            listDaos.add(productPrice);
                        }
                    }

                    if (!OnlyImage && ((type.equalsIgnoreCase("PDF")) || (type.equalsIgnoreCase("CAD")))) {
                        if (attachmentListDao != null) {
                            listDaos.add(productPrice);
                        }
                    }

                }

            }
        } catch (Exception e) {
            ShowLog("Exception in getAllCategories" + e.getStackTrace());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
//            databaseClose(db);
        }
        return listDaos;
    }

    /*get Frequent Product*/
    public ArrayList<Product> getFrequentProductList() {
        ShowLog("getAttachment product");
        ArrayList<Product> listDaos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            StringBuffer sql = new StringBuffer();
//            sql.append("SELECT TableProduct.P_ID,TableProduct.P_Name,TableProduct.Code,TableProduct.subcatId,TableSubCategory.catId,TableProductType.Price, TableAttachment.attach_ID,TableAttachment.type,TableAttachment.attachmentURL FROM TableProduct INNER JOIN TableSubCategory ON TableProduct.subcatId = TableSubCategory.subcatId  INNER JOIN TableProductType ON TableProduct.P_ID = TableProductType.P_ID left JOIN TableAttachment ON TableProductType.color_ID = TableAttachment.color_ID   Group BY  TableSubCategory.catId");
            sql.append("SELECT TableProduct.P_ID,TableProduct.P_Name,TableProduct.Code,TableProduct.subcatId,TableSubCategory.catId,TableProductType.Price FROM TableProduct INNER JOIN TableSubCategory ON TableProduct.subcatId = TableSubCategory.subcatId INNER JOIN TableProductType ON TableProduct.P_ID = TableProductType.P_ID Group BY  TableSubCategory.catId");
            cursor = db.rawQuery(sql.toString(), null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Product product = new Product();
                    product.id = cursor.getLong(cursor.getColumnIndex(DbCons.PRODUCT_ID));
                    product.name = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_NAME));
                    product.categoryid = cursor.getInt(cursor.getColumnIndex(DbCons.CAT_ID));
                    product.code = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_CODE));
                    product.subcatid = cursor.getLong(cursor.getColumnIndex(DbCons.SUBCAT_ID));

                    ProductPrice productPrice = new ProductPrice();
                    productPrice.price = cursor.getInt(cursor.getColumnIndex(DbCons.PRODUCT_PRICE));
                    productPrice.productId = cursor.getInt(cursor.getColumnIndex(DbCons.PRODUCT_ID));

                    AttachmentListDao attachmentListDao = new AttachmentListDao();
//                    attachmentListDao.type = cursor.getString(cursor.getColumnIndex(DbCons.TYPE));
//                    attachmentListDao.attachmentURL = cursor.getString(cursor.getColumnIndex(DbCons.URL));
//                    attachmentListDao.attachableid = cursor.getInt(cursor.getColumnIndex(DbCons.ATTACHABLE_ID));

                    productPrice.attachmentListDao = attachmentListDao;
                    product.productPrice = productPrice;
                    if (attachmentListDao != null) {
                        listDaos.add(product);
                    }
                }

            }
        } catch (Exception e) {
            ShowLog("Exception in getAllCategories" + e.getStackTrace());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
//            databaseClose(db);
        }
        return listDaos;
    }

    public ArrayList<Product> getSearchProductList(String searhText, int offset, int limit) {
        ShowLog("getSearchProductList");
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableProduct' WHERE Code LIKE '%aquamax%' OR P_Name LIKE '%aquamax%'
            String sql = "SELECT " + DbCons.TABLE_PRODUCT + ".*," + DbCons.TABLE_SUBCATEGORY + "." + DbCons.SUBCAT_NAME + " FROM " + DbCons.TABLE_PRODUCT +
                    " INNER join TableSubCategory ON TableSubCategory.subcatId = TableProduct.subcatId" +
                    " WHERE "
                    + DbCons.PRODUCT_CODE + " LIKE '%" + searhText + "%' OR "
                    + DbCons.PRODUCT_NAME + " LIKE '%" + searhText + "%'";
            cursor = db.rawQuery(sql, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Product product = new Product();
                    product.id = cursor.getLong(cursor.getColumnIndex(DbCons.PRODUCT_ID));
                    product.name = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_NAME));
                    product.subCatName = cursor.getString(cursor.getColumnIndex(DbCons.SUBCAT_NAME));
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

    /*getRelatedProduct();*/

    /**
     * @return
     */
    public ArrayList<Product> getRelatedCategory(String likestr) {
        ShowLog("getRelatedCategory product");
        ArrayList<Product> listDaos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT " +
                    "TableProduct.*," +
                    "TableSubCategory.*" +
                    " FROM 'TableProduct'" +
                    " Inner join TableSubCategory ON TableSubCategory.subcatId = TableProduct.subcatId" +
                    " WHERE 'TableProduct'.Code LIKE '" + likestr + "%' " +
                    " group by TableProduct.subcatId");
            cursor = db.rawQuery(sql.toString(), null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Product product = new Product();
                    product.id = cursor.getLong(cursor.getColumnIndex(DbCons.PRODUCT_ID));
                    product.name = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_NAME));
                    product.categoryid = cursor.getInt(cursor.getColumnIndex(DbCons.CAT_ID));
                    product.code = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_CODE));
                    product.subcatid = cursor.getLong(cursor.getColumnIndex(DbCons.SUBCAT_ID));

                    SubCategory subCategory = new SubCategory();
                    subCategory.id = cursor.getLong(cursor.getColumnIndex(DbCons.SUBCAT_ID));
                    subCategory.name = cursor.getString(cursor.getColumnIndex(DbCons.SUBCAT_NAME));
                    subCategory.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);
                    subCategory.title = cursor.getString(cursor.getColumnIndex(DbCons.TITLE));
                    subCategory.about = cursor.getString(cursor.getColumnIndex(DbCons.ABOUT));
                    subCategory.newArrival = (cursor.getInt(cursor.getColumnIndex(DbCons.NEW_ARRIVAL)) == 1);

                    product.subCategory = subCategory;
                    if (product != null) {
                        listDaos.add(product);
                    }
                }

            }
        } catch (Exception e) {
            ShowLog("Exception in getAllCategories" + e.getStackTrace());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
//            databaseClose(db);
        }
        return listDaos;
    }

    public ArrayList<Product> getRelatedSubCategoryProduct(String subCateId) {
        ShowLog("getRelatedSubCategoryProduct product");
        ArrayList<Product> listDaos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT TableProductType.Price,TableAttachment.attachmentURL, TableProduct.*,AttachableProductRelation.attach_ID FROM 'TableProduct' " +
                    " Inner join AttachableProductRelation ON AttachableProductRelation.ID = TableProduct.P_ID" +
                    " Inner join TableAttachment ON TableAttachment.attach_ID = AttachableProductRelation.attach_ID" +
                    " Inner JOIN TableProductType ON TableProductType.P_ID = TableProduct.P_ID" +
                    " WHERE 'TableProduct'.subcatId =" + subCateId + " AND TableAttachment.type = 'WHITE POLYMER'AND TableProductType.P_ID = TableProduct.P_ID AND TableProductType.color_Id=19" +
                    " Group BY TableProduct.P_ID");

            cursor = db.rawQuery(sql.toString(), null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Product product = new Product();
                    product.id = cursor.getLong(cursor.getColumnIndex(DbCons.PRODUCT_ID));
                    product.name = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_NAME));
                    product.code = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_CODE));
                    product.subcatid = cursor.getLong(cursor.getColumnIndex(DbCons.SUBCAT_ID));
                    product.code = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_CODE));
                    product.ImageUrl = cursor.getString(cursor.getColumnIndex(DbCons.URL));
                    ProductPrice productPrice = new ProductPrice();
                    productPrice.price = cursor.getInt(cursor.getColumnIndex(DbCons.PRODUCT_PRICE));
                    product.productPrice = productPrice;

                    if (product != null) {
                        listDaos.add(product);
                    }
                }

            }
        } catch (Exception e) {
            ShowLog("Exception in getAllCategories" + e.getStackTrace());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
//            databaseClose(db);
        }
        return listDaos;
    }
}

/* */
