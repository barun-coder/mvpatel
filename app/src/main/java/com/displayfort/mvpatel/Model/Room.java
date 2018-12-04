package com.displayfort.mvpatel.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Room implements Serializable {

    public Date created;
    public long id;
    public String name;
    public long projectID;
    public Boolean status;
    public boolean isSelected = false;
    public ArrayList<OrderDetailDao> orderDetailDaos = new ArrayList<>();

    public Room(String name, long projectID) {
        this.name = name;
        this.projectID = projectID;
    }


    public Room() {

    }
}

   
