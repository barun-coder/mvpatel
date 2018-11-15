package com.displayfort.mvpatel.DB;

import android.content.ContentValues;

import com.displayfort.mvpatel.Model.AttachmentListDao;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.Color;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.ProductPrice;
import com.displayfort.mvpatel.Model.SubCategory;

/**
 * Created by pc on 15/11/2018 17:20.
 * MVPatel
 */
public class ConstantValues {

    /**
     * CREATE TABLE IF NOT EXISTS AttachableRelation(ID INTEGER, attach_ID Integer)
     *
     * @param categoryDao
     * @return
     */
    public static final ContentValues getAttachableValues(CategoryDao categoryDao) {
        ContentValues values = new ContentValues();
        values.put(DbCons._ID, categoryDao.id);
        values.put(DbCons.ATTACHABLE_ID, categoryDao.attachable.id);
        return values;
    }

    public static final ContentValues getAttachableValues(SubCategory categoryDao) {
        ContentValues values = new ContentValues();
        values.put(DbCons._ID, categoryDao.id);
        values.put(DbCons.ATTACHABLE_ID, categoryDao.attachable.id);
        return values;
    }

    public static ContentValues getAttachableValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(DbCons._ID, product.id);
        values.put(DbCons.ATTACHABLE_ID, product.attachable.id);
        return values;
    }

    /**
     * CREATE TABLE IF NOT EXISTS TableCategory(catId INTEGER, Status BOOLEAN, NewArrival BOOLEAN, catName TEXT)
     *
     * @param categoryDao
     * @return
     */
    public static final ContentValues getCatValues(CategoryDao categoryDao) {
        ContentValues values = new ContentValues();
        values.put(DbCons.CAT_ID, categoryDao.id);
        values.put(DbCons.STATUS, categoryDao.status);
        values.put(DbCons.NEW_ARRIVAL, false);
        values.put(DbCons.CAT_NAME, categoryDao.name);
        return values;
    }

    /**
     * CREATE TABLE IF NOT EXISTS TableAttachment
     * (ID INTEGER, type TEXT, attachmentURL TEXT, color_ID INTEGER, Status BOOLEAN, title TEXT)
     *
     * @param attachmentList
     * @return
     */
    public static final ContentValues getAttachmentValues(AttachmentListDao attachmentList) {
        ContentValues values = new ContentValues();
        values.put(DbCons._ID, attachmentList.id);
        values.put(DbCons.TYPE, attachmentList.type);
        values.put(DbCons.URL, attachmentList.attachmentURL);
        values.put(DbCons.COLOR_ID, attachmentList.color.id);
        values.put(DbCons.STATUS, attachmentList.status);
        values.put(DbCons.TITLE, attachmentList.title);
        return values;
    }

    /**
     * CREATE TABLE IF NOT EXISTS TableSubCategory(
     * subcatId INTEGER,
     * catId INTEGER,
     * subcatName TEXT,
     * Status BOOLEAN,
     * NewArrival BOOLEAN,
     * title TEXT,
     * about TEXT)
     *
     * @param subCategory
     * @return
     */
    public static ContentValues getSubcategoryValues(SubCategory subCategory) {
        ContentValues values = new ContentValues();
        values.put(DbCons.SUBCAT_ID, subCategory.id);
        values.put(DbCons.CAT_ID, subCategory.categoryid);
        values.put(DbCons.SUBCAT_NAME, subCategory.name);
        values.put(DbCons.NEW_ARRIVAL, subCategory.newArrival);
        values.put(DbCons.STATUS, subCategory.status);
        values.put(DbCons.TITLE, subCategory.title);
        values.put(DbCons.ABOUT, subCategory.about);
        return values;
    }

    /**
     * CREATE TABLE IF NOT EXISTS TableProduct(
     * P_ID INTEGER,
     * P_Name TEXT,
     * Code TEXT,
     * Detail TEXT,
     * NewArrival BOOLEAN,
     * Status BOOLEAN,
     * subcatId Integer)
     *
     * @param product
     * @return
     */
    public static ContentValues getProductValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(DbCons.PRODUCT_ID, product.id);
        values.put(DbCons.PRODUCT_NAME, product.name);
        values.put(DbCons.PRODUCT_CODE, product.code);
        values.put(DbCons.PRODUCT_DETAIL, product.detail);
        values.put(DbCons.STATUS, product.status);
        values.put(DbCons.NEW_ARRIVAL, product.newArrival);
        values.put(DbCons.SUBCAT_ID, product.subcatid);
        return values;
    }

    /**
     * CREATE TABLE IF NOT EXISTS TableProductType(
     * ID INTEGER,
     * P_ID INTEGER,
     * Price INTEGER,
     * Status BOOLEAN,
     * color_ID Integer)
     *
     * @param dao
     * @return
     */
    public static ContentValues getProductPriceValues(ProductPrice dao) {
        ContentValues values = new ContentValues();
        values.put(DbCons.PRODUCT_ID, dao.productId);
        values.put(DbCons._ID, dao.id);
        values.put(DbCons.PRODUCT_PRICE, dao.price);
        values.put(DbCons.COLOR_ID, dao.color.id);
        values.put(DbCons.STATUS, dao.status);
        return values;
    }

    /**
     * CREATE TABLE IF NOT EXISTS TableColor(
     * ID INTEGER,
     * Status BOOLEAN,
     * title TEXT)
     *
     * @param color
     * @return
     */
    public static ContentValues getColorValues(Color color) {
        ContentValues values = new ContentValues();
        values.put(DbCons._ID, color.id);
        values.put(DbCons.TITLE, color.name);
        values.put(DbCons.STATUS, color.status);
        return values;
    }


}
