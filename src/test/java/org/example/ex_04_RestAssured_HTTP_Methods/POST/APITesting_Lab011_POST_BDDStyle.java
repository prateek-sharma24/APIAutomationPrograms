package org.example.ex_04_RestAssured_HTTP_Methods.POST;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class APITesting_Lab011_POST_BDDStyle {
    @Test
    public void test_POST_Auth()
    {
        //https://restful-booker.herokuapp.com/auth
        //"username" : "admin",
        //    "password" : "password123"
        String payload ="{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        RestAssured
                .given()
                            .baseUri("https://restful-booker.herokuapp.com")
                            .basePath("/auth")
                            .contentType(ContentType.JSON)
                            .log().all().body(payload)
                .when()
                            .log().all()//used to displaying console messages in order to use debugging
                            .post()
                .then()
                            .log().all()
                            .statusCode(200);

    }
}
