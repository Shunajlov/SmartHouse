package com.smartcity.smartHouse.dataModel.Storage;

import com.smartcity.smartHouse.Enums.UserType;
import org.mongodb.morphia.annotations.Entity;

@Entity("SM_USER")
public class SM_USER extends BaseEntity {

    public String houseId;
    public String name;
    public String login;
    public String password;
    public String token;
    public UserType type;
}
