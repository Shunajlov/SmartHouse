package com.smartcity.smartHouse.utils;

import com.smartcity.smartHouse.dataModel.AuthResult;
import com.smartcity.smartHouse.dataModel.AuthRoomerResult;

import java.util.Map;

public class Utils {
    public static boolean isTokenValid(String token) {
        for (Map.Entry<String, AuthRoomerResult> entry : Const.tokenRoomersMap.entrySet()) {
            if (entry.getValue().getToken() != null && entry.getValue().getToken().equals(token))
                return true;
        }

        for (Map.Entry<String, AuthResult> entry : Const.tokenUsersMap.entrySet()) {
            if (entry.getValue().getToken() != null && entry.getValue().getToken().equals(token))
                return true;
        }

        return false;
    }
}
