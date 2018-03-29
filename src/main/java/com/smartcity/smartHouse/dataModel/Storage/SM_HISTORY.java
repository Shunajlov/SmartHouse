package com.smartcity.smartHouse.dataModel.Storage;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_HISTORY")
public class SM_HISTORY extends BaseEntity {

    public String houseId;
    public String time;
    public String eventString;
}
