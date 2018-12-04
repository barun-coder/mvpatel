package com.displayfort.mvpatel.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.displayfort.mvpatel.Model.Project;
import com.displayfort.mvpatel.Model.Room;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pc on 19/11/2018 11:12.
 * MVPatel
 */
public class ProjectHandler extends AllJsonListHandler {

    public ProjectHandler(Context context) {
        super(context);
    }

    /**/
    public ArrayList<Project> getProjectList() {
        ShowLog("getProjectList");
        ArrayList<Project> projectArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableProject' where catId =1;
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_PROJECT, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Project project = new Project();
                    project.projectId = cursor.getLong(cursor.getColumnIndex(DbCons.PROJECT_ID));
                    project.name = cursor.getString(cursor.getColumnIndex(DbCons.TITLE));
                    project.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);
                    try {
                        long milis = Long.parseLong(cursor.getString(cursor.getColumnIndex(DbCons.CREATION)));
                        project.created = new Date(milis);
                    } catch (NumberFormatException e) {
                        project.created = new Date();
                    }
                    if (project != null) {
                        projectArrayList.add(project);
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
        return projectArrayList;
    }


    public Project getProjectDetail(long PRID) {
        ShowLog("getProjectList");
        Project project=new Project();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableProject' where catId =1;
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_PROJECT +" Where "+DbCons.PROJECT_ID+"="+PRID, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                     project = new Project();
                    project.projectId = cursor.getLong(cursor.getColumnIndex(DbCons.PROJECT_ID));
                    project.name = cursor.getString(cursor.getColumnIndex(DbCons.TITLE));
                    project.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);
                    try {
                        long milis = Long.parseLong(cursor.getString(cursor.getColumnIndex(DbCons.CREATION)));
                        project.created = new Date(milis);
                    } catch (NumberFormatException e) {
                        project.created = new Date();
                    }
                    project.discountValue = cursor.getDouble(cursor.getColumnIndex(DbCons.DISCOUNT_VALUE));
                    project.discountType = cursor.getString(cursor.getColumnIndex(DbCons.DISCOUNT_TYPE));
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
        return project;
    }

    /*Add Project*/
    public long AddProject(Project project) {
        long count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                db.beginTransaction();
                count = db.insert(DbCons.TABLE_PROJECT, "", ConstantValues.getProjectValues(project));
                if (count > -1) {
                    Room room = new Room("Master Room", count);
                    db.insert(DbCons.TABLE_ROOM, "", ConstantValues.geRoomValues(room));
                    room = new Room("Guest Room", count);
                    db.insert(DbCons.TABLE_ROOM, "", ConstantValues.geRoomValues(room));
                    room = new Room("Children Room", count);
                    db.insert(DbCons.TABLE_ROOM, "", ConstantValues.geRoomValues(room));
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

    /*Delete Project*/
    public long DeleteProject(long projectId) {
        long count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                count = db.delete(DbCons.TABLE_PROJECT, DbCons.PROJECT_ID + "=?", new String[]{String.valueOf(projectId)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    /*Update Project*/
    public long UpdateProject(Project project) {
        long count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                count = db.update(DbCons.TABLE_PROJECT,
                        ConstantValues.getProjectValues(project), DbCons.PROJECT_ID + "=?", new String[]{String.valueOf(project.projectId)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }

    /************************************************************************/
    /**/
    public ArrayList<Room> getRoomList(long PRID) {
        ShowLog("getRoomList");
        ArrayList<Room> roomArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //SELECT * FROM 'TableRoom' where catId =1;
            cursor = db.rawQuery("SELECT * FROM " + DbCons.TABLE_ROOM + " Where " + DbCons.PROJECT_ID + "=" + PRID, null);
            ShowLog(db.toString());
            if (null != cursor && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Room room = new Room();
                    room.projectID = cursor.getLong(cursor.getColumnIndex(DbCons.PROJECT_ID));
                    room.id = cursor.getLong(cursor.getColumnIndex(DbCons.ROOM_ID));
                    room.name = cursor.getString(cursor.getColumnIndex(DbCons.TITLE));
                    room.status = (cursor.getInt(cursor.getColumnIndex(DbCons.STATUS)) == 1);
                    try {
                        long milis = Long.parseLong(cursor.getString(cursor.getColumnIndex(DbCons.CREATION)));
                        room.created = new Date(milis);
                    } catch (NumberFormatException e) {
                        room.created = new Date();
                    }
                    if (room != null) {
                        roomArrayList.add(room);
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
        return roomArrayList;
    }

    /*Add Room*/
    public long AddRoom(Room room) {
        long count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = this.getWritableDatabase();
                }
                count = db.insert(DbCons.TABLE_ROOM, "", ConstantValues.geRoomValues(room));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseClose(db);
        }

        return count;
    }
}
