package com.displayfort.mvpatel.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.displayfort.mvpatel.Model.AttachmentListDao;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.Color;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.ProductPrice;
import com.displayfort.mvpatel.Model.SubCategory;

import java.util.ArrayList;


public class MasterDataHandler extends DatabaseHandler {
    private Context mContext;
    private SQLiteDatabase db;

    public MasterDataHandler(Context context) {
        super(context);
        this.mContext = context;
    }

    /**
     * @param categoryDaos
     * @return
     */
    public void AddCategoryList(ArrayList<CategoryDao> categoryDaos) {
        deleteAllTables();
        db = this.getWritableDatabase();
        ShowLog("Start:   ");
        for (CategoryDao categoryDao : categoryDaos) {
            ShowLog("\n\nStarting Cat:" + categoryDao.name);
            AddCategory(categoryDao);
        }

//        AddCategoryList(categoryDaos.get(0));
        databaseClose(db);


    }

    public long AddCategory(CategoryDao categoryDao) {
        ShowLog("AddCategoryList " + categoryDao.name);
        long count = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                count = db.insertWithOnConflict(DbCons.TABLE_CATEGORY, "", ConstantValues.getCatValues(categoryDao), SQLiteDatabase.CONFLICT_REPLACE);
                AddAttachable(categoryDao);
                AddSubcategoryList(categoryDao.subCategories);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }


    /**
     * @param categoryDao
     * @return
     */
    public long AddAttachable(CategoryDao categoryDao) {
        ShowLog("AddAttachable Cat " + categoryDao.name);
        long count = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
            }
            count = db.insertWithOnConflict(DbCons.TABLE_ATTACHABLE_REL, "", ConstantValues.getAttachableValues(categoryDao), SQLiteDatabase.CONFLICT_REPLACE);
            AddAttachmentList(categoryDao.attachable.attachmentList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * @param subCategory
     * @return
     */
    public long AddAttachable(SubCategory subCategory) {
        ShowLog("AddAttachable Sub " + subCategory.name);
        long count = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
            }
            count = db.insertWithOnConflict(DbCons.TABLE_ATTACHABLE_REL, "", ConstantValues.getAttachableValues(subCategory), SQLiteDatabase.CONFLICT_REPLACE);
            AddAttachmentList(subCategory.attachable.attachmentList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * @param product
     * @return
     */
    public long AddAttachable(Product product) {
        ShowLog("AddAttachable Product " + product.name);
        long count = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
            }
            count = db.insertWithOnConflict(DbCons.TABLE_ATTACHABLE_REL, "", ConstantValues.getAttachableValues(product), SQLiteDatabase.CONFLICT_REPLACE);
            AddAttachmentList(product.attachable.attachmentList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * @param attachmentListDaos
     * @return
     */
    public long AddAttachmentList(ArrayList<AttachmentListDao> attachmentListDaos) {
        ShowLog("AddAttachmentList");
        long count = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                for (AttachmentListDao dao : attachmentListDaos) {
                    count = db.insertWithOnConflict(DbCons.TABLE_ATTACHMENT, "", ConstantValues.getAttachmentValues(dao), SQLiteDatabase.CONFLICT_REPLACE);
                    AddCOLOR(dao.color);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * @param subCategories
     * @return
     */
    public long AddSubcategoryList(ArrayList<SubCategory> subCategories) {
        ShowLog("AddSubcategoryList");
        long count = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                for (SubCategory dao : subCategories) {
                    count = db.insertWithOnConflict(DbCons.TABLE_SUBCATEGORY, "", ConstantValues.getSubcategoryValues(dao), SQLiteDatabase.CONFLICT_REPLACE);
                    AddAttachable(dao);
                    AddProductList(dao.products);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * @param productArrayList
     * @return
     */
    public long AddProductList(ArrayList<Product> productArrayList) {
        ShowLog("AddProductList");
        long count = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                for (Product dao : productArrayList) {
                    count = db.insertWithOnConflict(DbCons.TABLE_PRODUCT, "", ConstantValues.getProductValues(dao), SQLiteDatabase.CONFLICT_REPLACE);
                    AddAttachable(dao);
                    AddProductPriceList(dao.productPrices);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * @param productPriceArrayList
     * @return
     */
    public long AddProductPriceList(ArrayList<ProductPrice> productPriceArrayList) {
        ShowLog("AddProductPriceList");
        long count = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                for (ProductPrice dao : productPriceArrayList) {
                    count = db.insertWithOnConflict(DbCons.TABLE_PRODUCT_PRICE_TYPE, "", ConstantValues.getProductPriceValues(dao), SQLiteDatabase.CONFLICT_REPLACE);
                    AddCOLOR(dao.color);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    /**
     * @param color
     * @return
     */
    public long AddCOLOR(Color color) {
        ShowLog("AddCOLOR " + color.name);
        long count = 0;
        db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    this.getWritableDatabase();
                }
                count = db.insertWithOnConflict(DbCons.TABLE_COLOR, "", ConstantValues.getColorValues(color), SQLiteDatabase.CONFLICT_REPLACE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    private void deleteAllTables() {
        deleteLineTableProduct(DbCons.TABLE_CATEGORY);
        deleteLineTableProduct(DbCons.TABLE_SUBCATEGORY);
        deleteLineTableProduct(DbCons.TABLE_ATTACHABLE_REL);
        deleteLineTableProduct(DbCons.TABLE_ATTACHMENT);
        deleteLineTableProduct(DbCons.TABLE_COLOR);
        deleteLineTableProduct(DbCons.TABLE_PRODUCT);
        deleteLineTableProduct(DbCons.TABLE_PRODUCT_PRICE_TYPE);
    }

    public int deleteLineTableProduct(String TableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = 0;
        StringBuffer buffer = new StringBuffer();
        try {
            db.delete(TableName, null, null);

        } catch (Exception e) {
        } finally {
            databaseClose(db);
        }
        return res;

    }

}