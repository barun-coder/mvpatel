package com.displayfort.mvpatel.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pc on 13/11/2018 17:45.
 * MVPatel
 */
public class AttachmentListDao {

    public Integer attachableid;
    public String attachment;
    public String attachmentURL;
    public Color color;
    public Integer colorID;
    public Long created;
    public String detail;
    public Integer id;
    public Boolean status;
    public String title;
    public String type;
    public Long updated;
    public ArrayList<AttachmentListDao> attachmentList = new ArrayList<>();

    public AttachmentListDao(JSONArray jsonArray) {
        if (jsonArray != null) {
            this.attachmentList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                this.attachmentList.add(new AttachmentListDao(jsonObject));
            }
        }

    }

    public AttachmentListDao(JSONObject jsonObject) {
        this.attachableid = jsonObject.optInt("attachableid", 0);
        this.attachment = jsonObject.optString("attachment", "");
        this.attachmentURL = jsonObject.optString("attachmentURL", "");
        this.color = new Color(jsonObject.optJSONObject("color"));
        this.created = jsonObject.optLong("created", 0);
        this.detail = jsonObject.optString("detail", "");
        this.id = jsonObject.optInt("id", 0);
        this.status = jsonObject.optBoolean("status", true);
        this.title = jsonObject.optString("title", "");
        this.type = jsonObject.optString("type", "");
        this.updated = jsonObject.optLong("updated", 0);
    }

    public AttachmentListDao() {


    }
}
