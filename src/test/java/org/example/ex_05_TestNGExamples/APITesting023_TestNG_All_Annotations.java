package org.example.ex_05_TestNGExamples;

import org.testng.annotations.*;

public class APITesting023_TestNG_All_Annotations {
    @BeforeSuite
    void demo1()
    {
        System.out.println("BeforeSuite");
    }
    @BeforeTest
    void demo2()
    {
        System.out.println("BeforeTest");
    }
    @BeforeClass
    void demo3()
    {
        System.out.println("Before Class");
    }
    @BeforeMethod
    void demo4()
    {
        System.out.println("BeforeMethod");
    }
    @Test
    void demo5()
    {
        System.out.println("Test");
    }
    @AfterMethod
    void demo7()
    {
        System.out.println("After method");
    }
    @AfterClass
    void demo8()
    {
        System.out.println("After Class");
    }
    @AfterTest
    void demo9()
    {
        System.out.println("After Test");
    }
    @AfterSuite
    void demo10()
    {
        System.out.println("After suite");
    }

}
