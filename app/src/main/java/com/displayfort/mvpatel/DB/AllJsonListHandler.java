package com.displayfort.mvpatel.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.displayfort.mvpatel.Model.Attachable;
import com.displayfort.mvpatel.Model.AttachmentListDao;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.Color;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.ProductPrice;
import com.displayfort.mvpatel.Model.SubCategory;

import java.util.ArrayList;

/**
 * Created by pc on 17/11/2018 16:52.
 * MVPatel
 */
public class AllJsonListHandler extends GetDataHandler {
    private Context mContext;
    private SQLiteDatabase db;
    private int c = 0, s = 0, p = 0, pt = 0, a = 0;

    public AllJsonListHandler(Context context) {
        super(context);
        this.mContext = context;
    }

    /*AddAllCategory*/
    public long MasterAddAllCategory(ArrayList<CategoryDao> categoryDaos) {
        long count = 0;
        c = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                db.beginTransaction();
                for (CategoryDao categoryDao : categoryDaos) {
                    count = db.insert(DbCons.TABLE_CATEGORY, "", ConstantValues.getCatValues(categoryDao));
                    ShowLog("MasterAddAllCategory " + (c++) +" "+ categoryDao.name);
                    db.insert(DbCons.TABLE_CAT_ATTACHABLE_REL, "", ConstantValues.getAttachableValues(categoryDao));
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
            databaseClose(db);
        }
        return count;
    }

    /*addAllSubcategory*/
    public long MasterAddAllSubcategory(ArrayList<SubCategory> subCategories) {
        long count = 0;
        s = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                db.beginTransaction();
                for (SubCategory subCategory : subCategories) {
                    count = db.insert(DbCons.TABLE_SUBCATEGORY, "", ConstantValues.getSubcategoryValues(subCategory));
                    ShowLog("MasterAddAllSubcategory " + (s++) +" "+ subCategory.name);
                    db.insert(DbCons.TABLE_SUBCAT_ATTACHABLE_REL, "", ConstantValues.getAttachableValues(subCategory));
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
            databaseClose(db);
        }
        return count;
    }

    /*Attachment*/
    public long MasterAddAllAttachment(ArrayList<AttachmentListDao> attachmentListDaos) {
        long count = 0;
        a = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                db.beginTransaction();
                for (AttachmentListDao attachmentListDao : attachmentListDaos) {
                    count = db.insert(DbCons.TABLE_ATTACHMENT, "", ConstantValues.getAttachmentValues(attachmentListDao));
                    ShowLog("MasterAddAllAttachment " + (a++) +" "+ attachmentListDao.attachmentURL);
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
            databaseClose(db);
        }
        return count;
    }

    /*Product*/
    public long MasterAddAllProductList(ArrayList<Product> products) {
        long count = 0;
        p = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                db.beginTransaction();
                for (Product product : products) {
                    count = db.insert(DbCons.TABLE_PRODUCT, "", ConstantValues.getProductValues(product));
                    ShowLog("MasterAddAllProductList " + (p++) +" "+ product.name);
                    db.insert(DbCons.TABLE_PRODUCT_ATTACHABLE_REL, "", ConstantValues.getAttachableValues(product));
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
            databaseClose(db);
        }
        return count;
    }

    /*Add Product Price*/
    public long MasterAddAllProductPriceList(ArrayList<ProductPrice> productPrices) {
        long count = 0;
        pt = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                db.beginTransaction();
                for (ProductPrice productPrice : productPrices) {
                    count = db.insert(DbCons.TABLE_PRODUCT_PRICE_TYPE, "", ConstantValues.getProductPriceValues(productPrice));
                    ShowLog("MasterAddAllProductPriceList " + (pt++) +" "+ productPrice.productId);
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
            databaseClose(db);
        }
        return count;
    }

    /*Add COlor list */
    public long MasterAddAllColorList(ArrayList<Color> colors) {
        long count = 0;
        c = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                db.beginTransaction();
                for (Color color : colors) {
                    count = db.insert(DbCons.TABLE_COLOR, "", ConstantValues.getColorValues(color));
                    ShowLog("MasterAddAllColorList " + (c++) +" "+ color.id);
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
            databaseClose(db);
        }
        return count;
    }

    /*Add Attachcable*/

}
