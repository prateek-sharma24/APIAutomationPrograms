package org.example.ex_04_RestAssured_HTTP_Methods.DELETE;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class APITesting_Lab014_DELETE_NonBDDStyle {
    @Test
    public void test_delete_non_bdd()
    {
        String token ="7f525b914fda51b";
        String bookingid ="2763";

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/"+bookingid);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token",token);

        requestSpecification.log().all();
        Response response =requestSpecification.when().delete();
        ValidatableResponse validatableResponse =response.then().log().all();
        validatableResponse.statusCode(201);
    }
}
