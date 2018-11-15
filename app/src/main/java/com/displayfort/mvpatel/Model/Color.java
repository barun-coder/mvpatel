package com.displayfort.mvpatel.Model;

import org.json.JSONObject;

/**
 * Created by pc on 13/11/2018 17:51.
 * MVPatel
 */
public class Color {

    public Long created;
    public Long id;
    public String name;
    public Boolean status;
    public Long updated;

    public Color(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.created = jsonObject.optLong("created", 0);
            this.id = jsonObject.optLong("id", 0);
            this.updated = jsonObject.optLong("updated", 0);
            this.name = jsonObject.optString("name", "NA");
            this.status = jsonObject.optBoolean("status", true);
        }
    }
}
