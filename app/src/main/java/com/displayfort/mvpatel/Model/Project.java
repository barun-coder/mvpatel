package com.displayfort.mvpatel.Model;
//G:\DisplayFort\hack\Jaquar-com.agbe.jaquar-19-v1.18_source_from_JADX\sources\com\agbe\jaquar\model

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Project implements Serializable {

    public Date created;
    public Long projectId;
    public String name;
    public Boolean status;
    public double discountValue;
    public String discountType;
    public Project(String name) {
        this.name = name;
    }

    public Project() {


    }

    public Project(Long projectId, String name) {
        this.projectId = projectId;
        this.name = name;
    }
}
