package com.smartcity.smartHouse.dataModel.Storage;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_USER")
public class SM_USER extends BaseEntity {

    public String houseId;
    public String name;
//    public UserType type;
    public String login;
    public String password;
    public String token;
}
