package com.smartcity.smartHouse.utils;

import java.util.UUID;

public class Utils {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
