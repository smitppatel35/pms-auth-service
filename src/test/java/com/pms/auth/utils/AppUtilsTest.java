package com.pms.auth.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AppUtilsTest {

    /**
     * token -> verify
     *
     *
     */
    @Autowired
    private AppUtils appUtils;

    @Test
    void test_generateRandomUUID(){
        assertEquals(appUtils.generateRandomUUID().length(), 36);
    }
}
