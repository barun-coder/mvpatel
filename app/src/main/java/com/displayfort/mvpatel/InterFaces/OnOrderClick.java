package com.displayfort.mvpatel.InterFaces;

import com.displayfort.mvpatel.Model.OrderDetailDao;

/**
 * Created by pc on 19/11/2018 11:20.
 * MVPatel
 */
public interface OnOrderClick {

    void OnOrderClick(OrderDetailDao orderDetailDao, int position);

}
