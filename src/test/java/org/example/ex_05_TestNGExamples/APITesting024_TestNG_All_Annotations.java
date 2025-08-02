package org.example.ex_05_TestNGExamples;

import org.testng.annotations.*;

public class APITesting024_TestNG_All_Annotations {
    @BeforeSuite
    void demo1()
    {
        System.out.println("Read the data from MySQL");
    }
    @BeforeTest
    void demo2()
    {
        System.out.println("Data in matrix , TC before");
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
    void demo6()
    {
        System.out.println("After method");
    }
    @AfterClass
    void demo7()
    {
        System.out.println("After Class");
    }
    @AfterTest
    void demo8()
    {
        System.out.println("After Test");
    }
    @AfterSuite
    void demo9()
    {
        System.out.println("After suite");
        System.out.println("Close everything, Delete all the temp files");
    }

}
