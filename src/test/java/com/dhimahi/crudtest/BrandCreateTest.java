package com.dhimahi.crudtest;

import com.dhimahi.SetUp;
import com.dhimahi.models.Brand;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class BrandCreateTest extends SetUp {
    public void validatePostRequest(Brand brand, int expectedStatusCode, String errorMessage, String invalidValue ){
     Response response = given()
                .body(brand)
                .when()
                .post()
        .then()
                .statusCode(expectedStatusCode)
                .log().all()
                .extract().response();

        validateResponseMessage(response, errorMessage,invalidValue);
    }

    public void validateResponseMessage(Response response, String errorMessage, String invalidValue) {
        response.then()
                .body(invalidValue, contains(errorMessage));
    }


    @Test
    @DisplayName("Missing Name Field")
    @Description("Test for API: Missing 'name' field, expect 422 status code")
    public void testMissingNameField() {
        String brandName = "";
        String brandSlug = "missing-slug";
        Brand brand = new Brand(brandName, brandSlug);

        validatePostRequest(brand, 422,"The name field is required.", "name");
    }


    @Test
    @DisplayName("Invalid Slug Format")
    @Description("Test for API: Invalid slug format, expect 422 status code")
    public void testInvalidSlugFormat() {
        String brandName = "Invalid Slug Brand";
        String brandSlug = "invalid-slug-@123";
        Brand brand = new Brand(brandName, brandSlug);

        validatePostRequest(brand, 422, "The slug field must only contain letters, numbers, dashes, and underscores.", "slug");
    }


    @Test
    @DisplayName("Minimum Length for Slug")
    @Description("Test for API: Minimum length for slug field")
    public void testMinimumLengthForName() {
        String randomBrandSlugValues = RandomStringUtils.random(1, true, true);
        String brandName = RandomStringUtils.random(5,true, true);

        Brand brand = new Brand(brandName, randomBrandSlugValues);

        given()
                .body(brand)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("name", equalTo(brandName));
    }


    @Test
    @DisplayName("Duplicate Brand")
    @Description("Test for API: Duplicate brand, expect 422 status code")
    public void testDuplicateBrandName() {
        String randomBrandValues = RandomStringUtils.random(10, true, true);
        String brandName = "Brand" + "-" + randomBrandValues;
        String brandSlug = "brand" + "-" + randomBrandValues;

        Brand brand = new Brand(brandName, brandSlug);

        given()
                .body(brand)
                .when()
                .post()
                .then()
                .statusCode(201);


        validatePostRequest(brand, 422, "A brand already exists with this slug.", "slug");
    }
}
