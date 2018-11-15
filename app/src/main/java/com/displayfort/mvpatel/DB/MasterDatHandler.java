package com.displayfort.mvpatel.DB;

import android.content.Context;


public class MasterDatHandler extends DatabaseHandler {
    private Context mContext;

    public MasterDatHandler(Context context) {
        super(context);
        this.mContext = context;
    }


}