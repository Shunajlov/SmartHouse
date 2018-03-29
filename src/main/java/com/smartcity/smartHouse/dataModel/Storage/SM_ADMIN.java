package com.smartcity.smartHouse.dataModel.Storage;

import org.mongodb.morphia.annotations.Entity;

@Entity("SM_ADMIN")
public class SM_ADMIN extends BaseEntity {

    public String login;
    public String password;
    public String token;
}
