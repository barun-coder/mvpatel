package com.displayfort.mvpatel.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.displayfort.mvpatel.Model.OrderDetailDao;

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
            cursor = db.rawQuery("SELECT TableOrderDetail.*,TableOrder." + DbCons.ROOMID + " ,Count (*) AS Qty" +
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
                    detailDao.Qty = cursor.getInt(cursor.getColumnIndexOrThrow("Qty"));
                    detailDao.created = cursor.getLong(cursor.getColumnIndex(DbCons.CREATION));
                    detailDao.roomId = cursor.getLong(cursor.getColumnIndexOrThrow(DbCons.ROOMID));
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
    public long AddOrderDetail(OrderDetailDao orderDao) {
        long count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                count = db.insert(DbCons.TABLE_ORDER_DETAIL, "", ConstantValues.geOrderDetailValues(orderDao));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    /*Is avail*/

    public int getOrderDetailCount(long productId, Integer colorId, Integer productTypeId, Integer price) {
        int orderId = 0;
        ShowLog("getOrderDetailCount");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableOrderDetailDao' where catId =1;
            cursor = db.rawQuery("SELECT " + DbCons.ORDER_ID + " FROM " + DbCons.TABLE_ORDER_DETAIL + " Where " +
                            DbCons.PRODUCT_ID + "=" + productId + " AND " +
                            DbCons.PRODUCT_TYPE_ID + "=" + productTypeId + " AND " +
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
    public long AddOrder(long roomId, long OID) {
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
                count = db.insert(DbCons.TABLE_ORDER, "", values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    /*Update Price*/

    public int updateOrder(double Price, int OID) {
        int count = 0;
        long id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                ContentValues values = new ContentValues();
                values.put(DbCons.PRODUCT_PRICE, Price);
                count = db.update(DbCons.TABLE_ORDER_DETAIL, values, DbCons.ORDER_ID + "=" + OID, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    /**/
    public int deleteOrder(long roomId, long OID) {
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
                        " where t.OrderId=" + OID + " AND t.RoomId=" + roomId + " " +
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
            cursor = db.rawQuery(" SELECT ((Count (*))*TableOrderDetail.Price) As Total, TableOrderDetail.Price ,Count (*) AS Qty " +
                            " FROM TableOrder " +
                            " INNER JOIN TableOrderDetail ON TableOrder.OrderId = TableOrderDetail.OrderId " +
                            " where  RoomId=" + roomId + " Group By TableOrder.OrderId"
                    , null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    amount = amount + cursor.getDouble(cursor.getColumnIndex("Total"));
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
            cursor = db.rawQuery(" SELECT ((Count (*))*TableOrderDetail.Price) As Total, TableOrderDetail.Price ,Count (*) AS Qty " +
                            " FROM TableOrder " +
                            " INNER JOIN TableOrderDetail ON TableOrder.OrderId = TableOrderDetail.OrderId " +
                            " where RoomId IN(SELECT RoomID FROM 'TableRoom' where ProjectId=" + ProjectId + ")" +
                            " Group By TableOrder.OrderId"
                    , null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (isGrandTotal) {
                        amount = amount + cursor.getDouble(cursor.getColumnIndex("Total"));
                    } else {
                        amount = amount + cursor.getDouble(cursor.getColumnIndex("Qty"));
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

    public int updateProject(double discount ,String type, long PRID) {
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
                count = db.update(DbCons.TABLE_PROJECT, values, DbCons.PROJECT_ID+ "=" + PRID, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }
}
