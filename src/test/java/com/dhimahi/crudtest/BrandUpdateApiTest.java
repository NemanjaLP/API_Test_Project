package com.dhimahi.crudtest;

import com.dhimahi.SetUp;
import com.dhimahi.models.Brand;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class BrandUpdateApiTest extends SetUp {
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
    @DisplayName("Valid Brand Update")
    @Description("Test for API: Update a valid brand and check if it returns status 200 and correct response")
    public void testValidBrandUpdate() {
            String brandName = "Updated Brand";
            String brandSlug = "updated-brand";
            Brand updatedBrand = new Brand(brandName, brandSlug);

            String existingID = getExistingID();

            given()
                    .body(updatedBrand)
                    .when()
                    .put("/" + existingID)
                    .then()
                    .statusCode(200)
                    .body("success", equalTo(true))
                    .log().all();
        }


    @Test
    @DisplayName("Update with Invalid Slug Format")
    @Description("Test for API: Update a brand with invalid slug format, expect 422")
    public void testUpdateInvalidSlugFormat() {
            String brandName = "Valid Name";
            String brandSlug = "invalid-slug-@123";
            Brand brand = new Brand(brandName, brandSlug);

            String existingID = getExistingID();

            given()
                    .body(brand)
                    .when()
                    .put("/" + existingID)
                    .then()
                    .statusCode(422)
                    .body("slug", contains("The slug field must only contain letters, numbers, dashes, and underscores."));

        }

    @Test
    @DisplayName("Update Non-existing Brand")
    @Description("Test for API: Attempt to update a non-existing brand")
    public void testUpdateNonExistingBrand() {
            String brandName = "Non-existing Brand";
            String brandSlug = "non-existing-brand";
            Brand brand = new Brand(brandName, brandSlug);


            String nonExistingResourceID = RandomStringUtils.random(10, true, true);

            given()
                    .body(brand)
                    .when()
                    .put("/" + nonExistingResourceID)
                    .then()
                    .statusCode(200)
                    .body("success", equalTo(false));
        }
    }
