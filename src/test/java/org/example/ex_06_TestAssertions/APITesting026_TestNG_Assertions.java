package org.example.ex_06_TestAssertions;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class APITesting026_TestNG_Assertions {

    @Test(enabled = false)
    public void test_hardAssertExample() {
        System.out.println("Start of program");
        Boolean is_neeru_male = false;
        Assert.assertEquals("prateek", "Prateek");
        //here the code is broken so the next line will not be executed
        System.out.println("End of the program");

    }
    //Soft Assertion

    @Test
    public void test_softAssertExample()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("prateek","Prateek");
        System.out.println("End of program");
    }
}
