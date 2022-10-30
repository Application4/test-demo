package com.javatechie;

import com.javatechie.demo.BusinessLogic;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DemoTest {


    @Test(expected = RuntimeException.class)
    public void testSum1() {
        assertEquals(18, new BusinessLogic().sum1(10, 8));
        assertEquals(2, new BusinessLogic().sum1(10, -8));
    }
    @Test
    public void testSum() {
        int a = 10;
        int b = 8;
        int expectedResult = 18;
        assertEquals(expectedResult, new BusinessLogic().sum(a, b));
    }

    @Test(expected = RuntimeException.class)
    public void testSumNegativeValue() {
        int a = 10;
        int b = -8;
        int expectedResult = 18;
        assertEquals(expectedResult, new BusinessLogic().sum(a, b));
    }
}
