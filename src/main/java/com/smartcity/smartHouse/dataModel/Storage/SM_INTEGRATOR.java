package com.smartcity.smartHouse.dataModel.Storage;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_INTEGRATOR")
public class SM_INTEGRATOR extends BaseEntity {

    public String houseId;
    public String name;
    public String login;
    public String password;
    public String token;

}
