package com.smartcity.smartHouse.dataModel.Storage;

import org.mongodb.morphia.annotations.Entity;

import java.util.List;

@Entity("SM_SCENARIO")
public class SM_SCENARIO extends BaseEntity  {

    public String name;
    public String houseId;
    public List<SM_SCENARIO_ITEM> scenario_items;
}
