package com.scaler.productservice;

import com.scaler.productservice.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class RandomTest {

    @Test
    public void testAdd(){
        int a = 3;
        int b = 5;
        int actualOutput = a + b;
        assert actualOutput == 8;
//        assertNull(object);
//        assertNotNull(object);
        assertTimeout(Duration.ofMillis(100),()->a+b);

    }
}
