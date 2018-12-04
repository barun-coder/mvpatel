package com.displayfort.mvpatel.Model;

import android.content.Context;

import com.displayfort.mvpatel.Utils.Utility;

import java.util.ArrayList;

/**
 * Created by pc on 17/11/2018 16:36.
 * MVPatel
 */
public class Master {
    public static ArrayList<CategoryDao> categoryDaosMaster = new ArrayList<>();
    public static ArrayList<SubCategory> subCategoriesMaster = new ArrayList<>();
    public static ArrayList<Attachable> attachablesCategoryMaster = new ArrayList<>();
    public static ArrayList<Attachable> attachablesSubCategoryMaster = new ArrayList<>();
    public static ArrayList<Attachable> attachablesProductMaster = new ArrayList<>();
    public static ArrayList<AttachmentListDao> attachmentListDaosMaster = new ArrayList<>();
    public static ArrayList<Color> colorArrayListMaster = new ArrayList<>();
    public static ArrayList<Product> productsMaster = new ArrayList<>();
    public static ArrayList<ProductPrice> productPriceArrayListMaster = new ArrayList<>();

    public static void getSIze(Context context) {
        Utility.showLog("categoryDaosMaster" + " : " + categoryDaosMaster.size());
        Utility.showLog("subCategoriesMaster" + " : " + subCategoriesMaster.size());
        Utility.showLog("attachablesCategoryMaster" + " : " + attachablesCategoryMaster.size());
        Utility.showLog("attachablesSubCategoryMaster" + " : " + attachablesSubCategoryMaster.size());
        Utility.showLog("attachablesProductMaster" + " : " + attachablesProductMaster.size());
        Utility.showLog("attachmentListDaosMaster" + " : " + attachmentListDaosMaster.size());
        Utility.showLog("colorArrayListMaster" + " : " + colorArrayListMaster.size());
        Utility.showLog("productsMaster" + " : " + productsMaster.size());
        Utility.showLog("productPriceArrayListMaster" + " : " + productPriceArrayListMaster.size());
    }
}
