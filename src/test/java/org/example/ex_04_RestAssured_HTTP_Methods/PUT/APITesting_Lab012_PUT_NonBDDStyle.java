package org.example.ex_04_RestAssured_HTTP_Methods.PUT;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class APITesting_Lab012_PUT_NonBDDStyle {
    //PUT

    //token, booking id -A
    //public void get_token(){}
    //public void get_booking_id(){}
    @Test
    public void test_PUT_NON_BDD()
    {
        RequestSpecification r;
        Response response;
        ValidatableResponse vr;

        String token ="34856642b1ea976";
        String bookingid ="1842";

        String payloadPUT ="{\n" +
                "    \"firstname\" : \"Prateek\",\n" +
                "    \"lastname\" : \"Sharma\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        r = RestAssured.given();
        r.baseUri("https://restful-booker.herokuapp.com");
        r.basePath("/booking/"+bookingid);
        r.contentType(ContentType.JSON);
        r.cookie("token", token);
        r.header("Authorization", "Bearer " + token);  // optional but safe

        r.body(payloadPUT).log().all();
        response =r.when().log().all().put();
        vr=response.then().log().all();
        vr.statusCode(200);

    }
}
