package org.example.ex_01_RA_Basics;

import io.restassured.RestAssured;

import java.util.Scanner;

public class APITesting_Lab02_RA {
    public static void main(String[] args) {

        //Gherkin Syntax
        //Given()->Pre Req.  -URL, Headers, Auth, Body.....
        //When() -> HTTP method? -GET/POST/PUT/PATCH, DELETE...
        //Then() ->Validation ->200 ok , firstname == Prateek
        //This is called Builder Pattern

        //Full URL - https://api.zippopotam.us/IN/560016
        //base URI- https://api.zippopotam.us
        //base Path - /IN/560016
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the pincode");
        String pincode = sc.next();

        RestAssured.given()
                 .baseUri("https://api.zippopotam.us")
                 .basePath("/IN/"+pincode)
                .when()
                .get()
                            .then().log().all()
                .statusCode(200);



    }
}

