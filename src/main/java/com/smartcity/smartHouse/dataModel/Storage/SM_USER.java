package com.smartcity.smartHouse.dataModel.Storage;

import com.smartcity.smartHouse.Enums.UserType;
import com.smartcity.smartHouse.dataModel.apiResults.AuthResult;
import com.smartcity.smartHouse.dataModel.model.Integrator;
import org.mongodb.morphia.annotations.Entity;

@Entity("SM_USER")
public class SM_USER extends BaseEntity {

    public String houseId;
    public String name;
    public UserType type;
    public String login;
    public String password;
    public String token;

    public static SM_USER fromAuthResult(AuthResult result) {
        SM_USER user = new SM_USER();
        user.login = result.getLogin();
        user.password = result.getPassword();
        user.token = result.getToken();
        return user;
    }
}
