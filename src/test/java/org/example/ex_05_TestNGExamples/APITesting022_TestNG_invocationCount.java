package org.example.ex_05_TestNGExamples;

import org.testng.annotations.Test;

public class APITesting022_TestNG_invocationCount {
    //basically used to run cases multiple times or looping the same
    @Test(invocationCount = 3)
    public void test01()
    {
        System.out.println("hi");
    }
    @Test(invocationCount = 2)
    public void test02()
    {
        System.out.println("bye");
    }
}
