package com.displayfort.mvpatel.Model;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pc on 13/11/2018 17:44.
 * MVPatel
 */
public class Attachable {
    public ArrayList<AttachmentListDao> attachmentList = null;
    public Long created;
    public Integer id;
    public Long updated;

    public Attachable(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.created = jsonObject.optLong("created", 0);
            this.id = jsonObject.optInt("id", 0);
            this.updated = jsonObject.optLong("updated", 0);
            this.attachmentList = new AttachmentListDao(jsonObject.optJSONArray("attachmentList")).attachmentList;
        }
    }

    public Attachable() {

    }
}
