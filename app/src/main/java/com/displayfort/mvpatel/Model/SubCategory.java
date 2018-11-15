package com.displayfort.mvpatel.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 13/11/2018 17:45.
 * MVPatel
 */
public class SubCategory {

    public Attachable attachable;
    public String catagoryName;
    public Integer categoryid;
    public ArrayList<Classification> classifications = null;
    public Long created;
    //    public ArrayList<Experience> experiences = null;
    public Long id;
    public String name;
    public boolean newArrival;
    public ArrayList<Product> products = null;
    public Boolean status;
    public String title;
    public Long updated;
    public ArrayList<SubCategory> subCategoryList = new ArrayList<>();
    public static HashMap<Long, SubCategory> subcategoryDetailMap = new HashMap<>();

    public SubCategory(JSONArray jsonArray) {
        if (jsonArray != null) {
            this.subCategoryList = new ArrayList<>();
            if (subcategoryDetailMap == null) {
                subcategoryDetailMap = new HashMap<>();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                SubCategory subCategory = new SubCategory(jsonObject);
                this.subCategoryList.add(subCategory);
                subcategoryDetailMap.put(subCategory.id, subCategory);
            }
        }
    }

    public SubCategory() {

    }

    public static SubCategory getSubCategoryDetail(long id) {
        if (subcategoryDetailMap == null) {
            return new SubCategory();
        } else {
            return subcategoryDetailMap.get(id);
        }
    }

    public SubCategory(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.attachable = new Attachable(jsonObject.optJSONObject("attachable"));
            this.catagoryName = jsonObject.optString("catagoryName", "");
            this.created = jsonObject.optLong("created", 0);
            this.categoryid = jsonObject.optInt("categoryid", 0);
            this.id = jsonObject.optLong("id", 0);
            this.status = jsonObject.optBoolean("status", true);
            this.newArrival = jsonObject.optBoolean("newArrival", true);
            this.title = jsonObject.optString("title", "");
            this.updated = jsonObject.optLong("updated", 0);
            this.name = jsonObject.optString("name", "NA");
            this.products = new Product(jsonObject.optJSONArray("products")).productList;
        }
    }
}
