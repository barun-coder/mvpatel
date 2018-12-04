package com.displayfort.mvpatel.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pc on 14/11/2018 13:08.
 * MVPatel
 */
public class Product {
    public SubCategory subCategory;
    public Attachable attachable;
    public Classification classification;
    public String code;
    public Long created;
    public ProductPrice productPrice;
    public String detail;
    public Long id;
    public String name;
    public Boolean newArrival;
    public ArrayList<ProductPrice> productPrices = null;
    public Boolean status;
    public String subCatName;
    public Long subcatid;
    public Long updated;
    public Integer categoryid;
    public String ImageUrl;
    public ArrayList<Product> productList = new ArrayList<>();
    public static HashMap<Long, Product> productDetailMap = new HashMap<>();
    public boolean isSelected=false;

    public Product(JSONArray jsonArray) {
        if (jsonArray != null) {
            this.productList = new ArrayList<>();
            if (productDetailMap == null) {
                productDetailMap = new HashMap<>();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                Product product = new Product(jsonObject);
                this.productList.add(product);
                Master.productsMaster.add(product);
                productDetailMap.put(product.id, product);
            }
        }
    }

    public Product() {

    }

    public static Product getProductDetail(long id) {
        if (productDetailMap == null) {
            return new Product();
        } else {
            return productDetailMap.get(id);
        }
    }


    public Product(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.attachable = new Attachable(jsonObject.optJSONObject("attachable"), 3);
            this.code = jsonObject.optString("code", "");
            this.created = jsonObject.optLong("created", 0);
            this.detail = jsonObject.optString("detail", "");
            this.id = jsonObject.optLong("id", 0);
            this.newArrival = jsonObject.optBoolean("newArrival", true);
            this.status = jsonObject.optBoolean("status", true);
            this.subcatid = jsonObject.optLong("subcatid", 0);
            this.updated = jsonObject.optLong("updated", 0);
            this.name = jsonObject.optString("name", "NA");
            this.productPrices = new ProductPrice(jsonObject.optJSONArray("productPrices")).productPriceList;
        }
    }
}
