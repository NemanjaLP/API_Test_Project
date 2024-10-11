package com.dhimahi.crudtest;

import com.dhimahi.SetUp;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


public class RetrieveBrandTest extends SetUp {
    public String getExistingID(){
        Response searchResponseID = given()
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        return searchResponseID.jsonPath().getString("[0].id");
    }


    @Test
    @DisplayName("Valid Brand Retrieval")
    @Description("Test for API: Retrieve a valid brand by ID and check if it returns status 200 and correct response")
    public void testValidBrandRetrieval() {
        String brandID = getExistingID();

        when()
                .get("/" + brandID)
        .then()
                .statusCode(200)
                .body("id", equalTo(brandID))
                .body("name", notNullValue())
                .body("slug", notNullValue())
                .log().all();
    }


    @Test
    @DisplayName("Non-existing Brand Retrieval")
    @Description("Test for API: Attempt to retrieve a non-existing brand by ID, expect 404 Not Found")
    public void testNonExistingBrandRetrieval() {
            String nonExistingBrandId = "non-existing-id";

            when()
                    .get("/" + nonExistingBrandId)
            .then()
                    .statusCode(404);
    }


    @Test
    @DisplayName("Retrieve All Brands - Valid Scenario")
    @Description("Test for API: Retrieve all brands and check if it returns status 200 and a valid list")
    public void testGetAllBrandsValid() {
            when()
                    .get()
            .then()
                    .statusCode(200)
                    .body("$", notNullValue())
                    .body("size()", greaterThanOrEqualTo(1))
                    .body("[0].id", notNullValue())
                    .body("[0].name", notNullValue())
                    .body("[0].slug", notNullValue())
                    .log().all();
        }



    }

