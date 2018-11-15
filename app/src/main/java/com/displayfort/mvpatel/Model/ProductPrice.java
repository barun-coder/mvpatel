package com.displayfort.mvpatel.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pc on 14/11/2018 13:09.
 * MVPatel
 */
public class ProductPrice {
    public Color color;
    public Long created;
    public Integer id;
    public Integer price;
    public Integer productId;
    public Boolean status;
    public Long updated;
    public ArrayList<ProductPrice> productPriceList = new ArrayList<>();

    public ProductPrice(JSONArray jsonArray) {
        if (jsonArray != null) {
            this.productPriceList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                ProductPrice productPrice = new ProductPrice(jsonObject);
                this.productPriceList.add(productPrice);
            }
        }
    }

    public ProductPrice() {

    }

    public ProductPrice(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.color = new Color(jsonObject.optJSONObject("color"));
            this.price = jsonObject.optInt("price", 0);
            this.created = jsonObject.optLong("created", 0);
            this.productId = jsonObject.optInt("productId", 0);
            this.id = jsonObject.optInt("id", 0);
            this.status = jsonObject.optBoolean("status", true);
            this.updated = jsonObject.optLong("updated", 0);
        }
    }
}
