package com.displayfort.mvpatel.InterFaces;

import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.Model.Project;

/**
 * Created by pc on 19/11/2018 11:20.
 * MVPatel
 */
public interface OnProductClick {

    void OnProductClick(Product product, int type);

    void OnProductSelected(Product product, boolean isSelected);

}
