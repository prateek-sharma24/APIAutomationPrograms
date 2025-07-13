package org.example.ex_02_RA_Concepts;

public class APITesting_Lab03_NP {
    // NoDesignPattern
    public void step1()
    {
        System.out.println("Step1");
    }
    public void step2()
    {
        System.out.println("Step2");
    }
    public void step3()
    {
        System.out.println("Step3");
    }

    public static void main(String[] args) {
        APITesting_Lab03_NP np =new APITesting_Lab03_NP();
        np.step1();
        np.step2();
        np.step3();
    }
}
