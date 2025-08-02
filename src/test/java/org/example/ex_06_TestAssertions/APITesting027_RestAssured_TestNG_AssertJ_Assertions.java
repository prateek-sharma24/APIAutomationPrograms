package org.example.ex_06_TestAssertions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class APITesting027_RestAssured_TestNG_AssertJ_Assertions {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    String token;
    Integer bookingID;


    @Test
            public void Assertions() {
        String payload_POST = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"BookingDates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
        requestSpecification.basePath("/booking");
        requestSpecification.body(payload_POST).log().all();

        Response response =requestSpecification.when().post();

        //Get Validatable response to perform validation
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        validatableResponse.body("booking.firstname", Matchers.equalTo("Jim"));
        validatableResponse.body("booking.lastname", Matchers.equalTo("Brown"));
        validatableResponse.body("booking.depositpaid", Matchers.equalTo(false));
        validatableResponse.body("bookingid", Matchers.notNullValue());

        //TestNG -Extract the details of the firstname, bookingID, lastname from resposnse

        bookingID =response.then().extract().path("bookingid");
        String firstname =response.then().extract().path("booking.firstname");
        String lastname =response.then().extract().path("booking.lastname");

        //TestNG Assertions
        //SoftAssert vs
        //HardAssert -
        //This means that if any assertions fails,
        //the remaining statements in that test method will not be executed
        Assert.assertEquals(firstname,"Jim");
        Assert.assertEquals(lastname,"Brown");
        Assert.assertNotNull(bookingID);


        //AssertJ(3rd Lib to Assertions)-by default hard assertions only
        //This assertions are hard assertions,if one assertions fails then next assertion will not be executed
        assertThat(bookingID).isNotZero().isNotNull().isPositive();
        assertThat(firstname).isEqualTo("Jim").isNotBlank().isNotEmpty().isNotNull().isAlphanumeric();

        //String s = ""; //Empty
        //String s2 =" " //Blank


    }

}
