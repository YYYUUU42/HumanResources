package com.utils;

import java.util.UUID;

public class uuid {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "").substring(6);
    }
}
