package com.displayfort.mvpatel.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.displayfort.mvpatel.DB.TrackerDbHandler;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.MvPatelApplication;

import androidx.annotation.Nullable;

/**
 * Created by pc on 15/11/2018 17:39.
 * MVPatel
 */
public class SaveJsonDateInDbService extends Service {
    @Nullable
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CategoryDao categoryDao = CategoryDao.getCategoryDao(this);
        TrackerDbHandler dbHandler = MvPatelApplication.getDatabaseHandler();
        dbHandler.AddCategory(categoryDao.categoryDaos);
        return super.onStartCommand(intent, flags, startId);
    }
}
