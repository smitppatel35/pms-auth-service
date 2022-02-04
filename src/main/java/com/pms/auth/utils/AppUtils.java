package com.pms.auth.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppUtils {

    public String generateRandomUUID(){
        return UUID.randomUUID().toString();
    }
}
