package com.smartcity.smartHouse.dataModel.Storage;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_HOUSE")
public class SM_HOUSE extends BaseEntity {

    public String name;
}
