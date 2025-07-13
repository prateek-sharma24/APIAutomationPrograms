package org.example.ex_04_RestAssured_HTTP_Methods.PATCH;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.Test;

public class APITesting_Lab013_PATCH_NonBDDStyle {


    @Test
    public void test_PATCH_NonBDD()
    {
        String token ="67581b9f8744421";
        String bookingid = "2627";
        String payloadPATCH ="{\n" +
                "    \"firstname\" : \"Sahil\",\n" +
                "    \"lastname\" : \"Brown\"\n" +
                "}";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/"+bookingid);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token", token);

        requestSpecification.body(payloadPATCH).log().all();
        Response response =requestSpecification.when().patch();
        ValidatableResponse validatableResponse =response.then().log().all();
        validatableResponse.statusCode(200);


    }
}
