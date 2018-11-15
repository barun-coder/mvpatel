package com.displayfort.mvpatel.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.Utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 12/11/2018 18:47.
 * MVPatel
 */
public class CategoryDao {
    public String categoryName = "";
    public String description = "";
    public String imageString = "";
    public int ImageInt;
    public static CategoryDao categoryDao;
    public Attachable attachable;
    public ArrayList<SubCategory> subCategories = null;
    public Long created;
    public Integer id;
    public String name;
    public Boolean status;
    public Long updated;
    public ArrayList<CategoryDao> categoryDaos = new ArrayList<>();
    public HashMap<Integer, ArrayList<CategoryDao>> categoryMap = new HashMap<>();
    public HashMap<Integer, CategoryDao> categoryDetailMap = new HashMap<>();

    public CategoryDao(String categoryName, String description, int imageInt, int Id) {
        this.categoryName = categoryName;
        this.description = description;
        this.ImageInt = imageInt;
        this.id = Id;
    }

    public CategoryDao() {

    }

    public CategoryDao getCategoryDetail(int id) {
        if (categoryDetailMap == null) {
            return new CategoryDao();
        } else {
            return categoryDetailMap.get(id);
        }
    }

    public CategoryDao(String categoryName, String description, int imageInt) {
        this.categoryName = categoryName;
        this.description = description;
        this.ImageInt = imageInt;

    }


    public CategoryDao(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray != null) {
                this.categoryDaos = new ArrayList<>();
                this.categoryMap = new HashMap<>();
                this.categoryDetailMap = new HashMap<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    CategoryDao categoryDao = new CategoryDao(jsonObject);
                    this.categoryDaos.add(categoryDao);
                    setToHashMap(categoryDao, 1);
                    categoryDetailMap.put(categoryDao.id, categoryDao);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setToHashMap(CategoryDao categoryDao, int j) {
        int id = categoryDao.id;
        int resultKey = 0;
        switch (j) {
            case 1:
                if (id == 22 || id == 10 || id == 19 || id == 20) {
                    resultKey = 1;
                }
                break;
            case 2:
                if (id == 1 || id == 11) {
                    resultKey = 2;
                }
                break;
            case 3:
                if (id == 11 || id == 12) {
                    resultKey = 3;
                }
                break;
            case 4:
                if (id == 17 || id == 14 || id == 15 || id == 16 || id == 18 || id == 19 || id == 20) {
                    resultKey = 4;
                }
                break;
            case 5:
                if (id == 21) {
                    resultKey = 5;
                }
                break;
        }
        if (resultKey != 0) {
            ArrayList<CategoryDao> arrayList = this.categoryMap.get(resultKey);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            arrayList.add(categoryDao);
            this.categoryMap.put(resultKey, arrayList);
        }
        if (j < 5) {
            setToHashMap(categoryDao, ++j);
        }
    }

    public CategoryDao(JSONObject jsonObject) {
        this.created = jsonObject.optLong("created", 0);
        this.id = jsonObject.optInt("id", 0);
        this.name = jsonObject.optString("name", "NA");
        this.status = jsonObject.optBoolean("status", true);
        this.updated = jsonObject.optLong("updated", 0);
        this.attachable = new Attachable(jsonObject.optJSONObject("attachable"));
        this.subCategories = new SubCategory(jsonObject.optJSONArray("subCategories")).subCategoryList;
    }

    public static CategoryDao getCategoryDao(Context context) {
        if (categoryDao == null) {
            categoryDao = new CategoryDao(loadJSONFromAsset("categories.json", context));
        }
        return categoryDao;
    }

    public static String loadJSONFromAsset(String filename, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}

