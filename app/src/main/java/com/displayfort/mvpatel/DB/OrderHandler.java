package com.displayfort.mvpatel.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.displayfort.mvpatel.Model.OrderDetailDao;
import com.displayfort.mvpatel.Model.Room;

import java.util.ArrayList;

/**
 * Created by pc on 19/11/2018 11:12.
 * MVPatel
 */
public class OrderHandler extends ProjectHandler {

    public OrderHandler(Context context) {
        super(context);
    }


    public ArrayList<OrderDetailDao> getOrderListByRoom(int roomID) {
        ShowLog("getOrderListByRoom");
        ArrayList<OrderDetailDao> orderDetailDaos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableOrderDetailDao' where catId =1;
            cursor = db.rawQuery("SELECT TableOrderDetail.*,TableOrder." + DbCons.ROOMID + " ,TableOrder.QTY " +
                    " FROM TableOrder " +
                    "INNER JOIN TableOrderDetail ON TableOrder.OrderId = TableOrderDetail.OrderId" +
                    " where  " + DbCons.ROOMID + "=" + roomID +
                    " Group By TableOrder.OrderId", null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    OrderDetailDao detailDao = new OrderDetailDao();
                    detailDao.OrderId = cursor.getInt(cursor.getColumnIndex(DbCons.ORDER_ID));
                    detailDao.productId = cursor.getLong(cursor.getColumnIndex(DbCons.PRODUCT_ID));
                    detailDao.name = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_NAME));
                    detailDao.code = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_CODE));
                    detailDao.detail = cursor.getString(cursor.getColumnIndex(DbCons.PRODUCT_DETAIL));
                    detailDao.productTypeId = cursor.getInt(cursor.getColumnIndex(DbCons.PRODUCT_TYPE_ID));
                    detailDao.price = cursor.getInt(cursor.getColumnIndex(DbCons.PRODUCT_PRICE));
                    detailDao.colorId = cursor.getInt(cursor.getColumnIndex(DbCons.COLOR_ID));
                    detailDao.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);
                    detailDao.colorText = cursor.getString(cursor.getColumnIndex(DbCons.TITLE));
                    detailDao.attachId = cursor.getInt(cursor.getColumnIndex(DbCons.ATTACHABLE_ID));
                    detailDao.ImageUrl = cursor.getString(cursor.getColumnIndex(DbCons.URL));
                    detailDao.Qty = cursor.getInt(cursor.getColumnIndex(DbCons.QUANTITY));
                    detailDao.created = cursor.getLong(cursor.getColumnIndex(DbCons.CREATION));
                    detailDao.roomId = cursor.getLong(cursor.getColumnIndexOrThrow(DbCons.ROOMID));
                    detailDao.discountValue = cursor.getDouble(cursor.getColumnIndex(DbCons.DISCOUNT_VALUE));
                    detailDao.discountType = cursor.getString(cursor.getColumnIndex(DbCons.DISCOUNT_TYPE));
                    double grandT = detailDao.price;
                    double finalgTotal = grandT;
                    double dvalue = detailDao.discountValue;
                    detailDao.discountPrice = detailDao.price;
                    if (detailDao.discountType != null && dvalue >= 1) {
                        if (detailDao.discountType.equalsIgnoreCase("R")) {
                            if (dvalue >= 1) {
                                detailDao.discountPrice = grandT - dvalue;

                            }
                        } else {
                            if (dvalue >= 1) {
                                detailDao.discountPrice = grandT - (grandT * (dvalue / 100));
                            }
                        }
                    } else {
                        detailDao.discountPrice = detailDao.price;
                    }

                    if (detailDao != null) {
                        orderDetailDaos.add(detailDao);
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
        return orderDetailDaos;
    }

    /*Add OrderDetailDao*/
    public long AddOrderDetail(OrderDetailDao orderDao, long PRID) {
        long count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                count = db.insert(DbCons.TABLE_ORDER_DETAIL, "", ConstantValues.geOrderDetailValues(orderDao, PRID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    /*Is avail*/

    public int getOrderDetailCount(long productId, Integer colorId, Integer productTypeId, Integer price, long PRID) {
        int orderId = 0;
        ShowLog("getOrderDetailCount");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableOrderDetailDao' where catId =1;
            cursor = db.rawQuery("SELECT " + DbCons.ORDER_ID + " FROM " + DbCons.TABLE_ORDER_DETAIL + " Where " +
                            DbCons.PRODUCT_ID + "=" + productId + " AND " +
                            DbCons.PRODUCT_TYPE_ID + "=" + productTypeId + " AND " +
                            DbCons.PROJECT_ID + "=" + PRID + " AND " +
                            DbCons.COLOR_ID + "=" + colorId + " AND " +
                            DbCons.PRODUCT_PRICE + "=" + price


                    , null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    orderId = cursor.getInt(cursor.getColumnIndex(DbCons.ORDER_ID));
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
        return orderId;
    }

    /*Add Order*/
    public long AddOrder(long roomId, long OID, long PRID, int QTY) {
        long count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                ContentValues values = new ContentValues();
                values.put(DbCons.ORDER_ID, OID);
                values.put(DbCons.ROOMID, roomId);
                values.put(DbCons.PROJECT_ID, PRID);
                values.put(DbCons.QUANTITY, QTY);
                count = db.insert(DbCons.TABLE_ORDER, "", values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    public int updateOrderQty(long roomId, long OID, long PRID, int QTY) {
        int count = 0;
        long id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                ContentValues values = new ContentValues();
                values.put(DbCons.QUANTITY, QTY);
                count = db.update(DbCons.TABLE_ORDER, values,
                        DbCons.ORDER_ID + "=" + OID
                                + " AND " + DbCons.ROOMID + "=" + roomId
                                + " AND " + DbCons.PROJECT_ID + "=" + PRID

                        , null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    public void updateOrderQtyItself(long roomId, long OID, long PRID) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
//                UPDATE TableOrder     SET QTY = QTY + 1    WHERE OrderId = 1
                String sql = "UPDATE " + DbCons.TABLE_ORDER +
                        " SET " + DbCons.QUANTITY + "=" + DbCons.QUANTITY + "+1 " +
                        "where " + DbCons.ORDER_ID + "=" + OID + " AND " + DbCons.ROOMID + "=" + roomId + " AND " + DbCons.PROJECT_ID + "=" + PRID;
                Cursor cursor = null;
                cursor = db.rawQuery(sql, null);
                if (null != cursor && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        cursor.getInt(cursor.getColumnIndex(DbCons.QUANTITY));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

    }

    /*Update Price*/
//
    public int updateOrderDetail(int OID, double discount, String type, long PRID) {
        int count = 0;
        long id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                ContentValues values = new ContentValues();
                values.put(DbCons.DISCOUNT_VALUE, discount);
                values.put(DbCons.DISCOUNT_TYPE, type);
                count = db.update(DbCons.TABLE_ORDER_DETAIL, values, DbCons.ORDER_ID + "=" + OID + " AND " + DbCons.PROJECT_ID + "=" + PRID, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    /**/
    public int deleteOrder(long roomId, long OID, long PRID) {
        int count = 0;
        long id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                ContentValues values = new ContentValues();
                values.put(DbCons.ORDER_ID, OID);
                values.put(DbCons.ROOMID, roomId);
                Cursor cursor = null;
                cursor = db.rawQuery(" select t.ID from 'TableOrder' as t " +
                        " where" +
                        " t.OrderId=" + OID + " AND " +
                        " t.ProjectId=" + PRID + " AND " +
                        "t.RoomId=" + roomId + " " +
                        " order by ID desc Limit 1", null);
                if (null != cursor && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        id = cursor.getInt(cursor.getColumnIndex(DbCons._ID));
                    }
                }
                if (id != 0) {
                    count = db.delete(DbCons.TABLE_ORDER, DbCons._ID + "=" + id, null);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    public double getParticularRoomtotal(long roomId) {
        double amount = 0;
        ShowLog("getOrderDetailCount");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
//           sql=" SELECT ((Count (*))*TableOrderDetail.Price) As Total, TableOrderDetail.Price ,Count (*) AS Qty " +
//                    " FROM TableOrder " +
//                    " INNER JOIN TableOrderDetail ON TableOrder.OrderId = TableOrderDetail.OrderId " +
//                    " where  RoomId=" + roomId + " Group By TableOrder.OrderId";

            String sql = "SELECT TableOrderDetail.DiscountValue,TableOrderDetail.DiscountType, TableOrderDetail.Price,TableOrder.QTY " +
                    "FROM 'TableOrder' " +
                    "INNER JOIN TableOrderDetail ON TableOrder.OrderId = TableOrderDetail.OrderId  " +
                    "where RoomID=" + +roomId;
            cursor = db.rawQuery(sql, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int price = cursor.getInt(cursor.getColumnIndex(DbCons.PRODUCT_PRICE));
                    int Qty = cursor.getInt(cursor.getColumnIndex(DbCons.QUANTITY));
                    double discountValue = cursor.getDouble(cursor.getColumnIndex(DbCons.DISCOUNT_VALUE));
                    String discountType = cursor.getString(cursor.getColumnIndex(DbCons.DISCOUNT_TYPE));
//
                    double totalAmount = price;
                    double grandT = price;
                    double finalgTotal = grandT;
                    double dvalue = discountValue;
                    if (discountType != null && dvalue >= 1) {
                        if (discountType.equalsIgnoreCase("R")) {
                            if (dvalue >= 1) {
                                totalAmount = grandT - dvalue;
                            }
                        } else {
                            if (dvalue >= 1) {
                                totalAmount = grandT - (grandT * (dvalue / 100));
                            }
                        }
                    } else {
                        totalAmount = price;
                    }

                    amount = amount + (Qty * totalAmount);
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
        return amount;
    }

    public double getGrandValues(boolean isGrandTotal, long ProjectId) {
        double amount = 0;
        ShowLog("getOrderDetailCount");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
// SELECT ((Count (*))*TableOrderDetail.Price) As Total, TableOrderDetail.Price ,Count (*) AS Qty " +
//                            " FROM TableOrder " +
//                            " INNER JOIN TableOrderDetail ON TableOrder.OrderId = TableOrderDetail.OrderId " +
//                            " where RoomId IN(SELECT RoomID FROM 'TableRoom' where ProjectId=" + ProjectId + ")" +
//                            " Group By TableOrder.OrderId
            String sql = "";
            if (isGrandTotal) {
                sql = "SELECT TableOrderDetail.DiscountValue,TableOrderDetail.DiscountType, TableOrderDetail.Price,TableOrder.QTY " +
                        " FROM 'TableOrder' " +
                        " INNER JOIN TableOrderDetail ON TableOrder.OrderId = TableOrderDetail.OrderId  " +
                        " where TableOrder.ProjectId=" + ProjectId;
            } else {
                sql = "SELECT SUM(TableOrder.QTY) AS Qty FROM 'TableOrder' " +
                        "INNER JOIN TableOrderDetail ON TableOrder.OrderId = TableOrderDetail.OrderId " +
                        "where TableOrder.ProjectId=" + ProjectId;
            }
            cursor = db.rawQuery(sql, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (isGrandTotal) {
                        /**/
                        int price = cursor.getInt(cursor.getColumnIndex(DbCons.PRODUCT_PRICE));
                        int Qty = cursor.getInt(cursor.getColumnIndex(DbCons.QUANTITY));
                        double discountValue = cursor.getDouble(cursor.getColumnIndex(DbCons.DISCOUNT_VALUE));
                        String discountType = cursor.getString(cursor.getColumnIndex(DbCons.DISCOUNT_TYPE));
//
                        double totalAmount = price;
                        double grandT = price;
                        double finalgTotal = grandT;
                        double dvalue = discountValue;
                        if (discountType != null && dvalue >= 1) {
                            if (discountType.equalsIgnoreCase("R")) {
                                if (dvalue >= 1) {
                                    totalAmount = grandT - dvalue;
                                }
                            } else {
                                if (dvalue >= 1) {
                                    totalAmount = grandT - (grandT * (dvalue / 100));
                                }
                            }
                        } else {
                            totalAmount = price;
                        }

                        amount = amount + (Qty * totalAmount);
                        /**/
                    } else {
                        amount = cursor.getDouble(cursor.getColumnIndex("Qty"));
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
        return amount;
    }

    public int updateProject(double discount, String type, long PRID) {
        int count = 0;
        long id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                ContentValues values = new ContentValues();
                values.put(DbCons.DISCOUNT_VALUE, discount);
                values.put(DbCons.DISCOUNT_TYPE, type);
                count = db.update(DbCons.TABLE_PROJECT, values, DbCons.PROJECT_ID + "=" + PRID, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }
}
