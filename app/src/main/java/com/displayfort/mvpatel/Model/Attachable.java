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

    public Attachable(JSONObject jsonObject, int type) {
        if (jsonObject != null) {
            this.created = jsonObject.optLong("created", 0);
            this.id = jsonObject.optInt("id", 0);
            this.updated = jsonObject.optLong("updated", 0);
            switch (type) {
                case 1:
                    Master.attachablesCategoryMaster.add(this);
                    break;
                case 2:
                    Master.attachablesSubCategoryMaster.add(this);
                    break;
                default:
                    Master.attachablesProductMaster.add(this);
                    break;
            }
            this.attachmentList = new AttachmentListDao(jsonObject.optJSONArray("attachmentList")).attachmentList;
        }


    }

    public Attachable() {

    }
}
