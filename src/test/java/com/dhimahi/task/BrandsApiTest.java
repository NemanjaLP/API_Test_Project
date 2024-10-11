package com.dhimahi.task;

import com.dhimahi.SetUp;
import com.dhimahi.models.Brand;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;


public class BrandsApiTest extends SetUp {
    String randomBrandValues = RandomStringUtils.random(10, true, true);
    String brandName = "Brand" + "-" + randomBrandValues;
    String brandSlug = "brand" + "-" + randomBrandValues;

    public void brandValidate(String resourceID, String brandName, String brandSlug, int expectedStatusCode){
        when()
                .get(resourceID)
        .then()
                .statusCode(200)
                .body("name", equalTo(brandName))
                .body("slug", equalTo(brandSlug));
    }

    @Test
    @DisplayName("Brands API Test")
    @Description("Test for Brands API: Create, Retrieve, Update, and Validate a Brand")
    public void brandsApiTest() {
        Brand brand = new Brand(brandName, brandSlug);

        Response postResponse = given()
                .body(brand)
        .when()
                .post()
        .then()
                .statusCode(201)
                .log().all()
                .extract().response();

        String resourceID = postResponse.jsonPath().getString("id");

        brandValidate(resourceID, brand.getName(), brand.getSlug(), 200);

        Brand modifiedBrand = new Brand(brandName + "-" + "updated", brandSlug + "-" + "updated");

        given()
                .body(modifiedBrand)
        .when()
                .put(basePath + resourceID)
        .then()
                .statusCode(200)
                .log().all()
                .body("success", equalTo(true))
                .extract().response();

        brandValidate(resourceID, modifiedBrand.getName(), modifiedBrand.getSlug(), 200);
    }


    @Test
    @DisplayName("Brand Update API Test")
    @Description("Update and validate of an existing brand")
    public void updateExistingBrand() {
        Brand modifiedBrand = new Brand(brandName + "-" + "updated", brandSlug + "-" + "updated");

        Response searchResponseID = given()
        .when()
                .get(basePath)
        .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        String existingBrandID = searchResponseID.jsonPath().getString("[0].id");

        given()
                .body(modifiedBrand)
        .when()
                .put(basePath + existingBrandID)
        .then()
                .statusCode(200)
                .log().all()
                .body("success", equalTo(true))
                .extract().response();

        brandValidate(existingBrandID, modifiedBrand.getName(), modifiedBrand.getSlug(), 200);
    }
}
