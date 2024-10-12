package com.dhimahi;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import org.junit.BeforeClass;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SetUp {

    public static String BASE_URL;
    public static String BASE_PATH;

    @BeforeClass
    public static void setUp(){
        Properties properties = new Properties();
        try (InputStream input = SetUp.class.getClassLoader().getResourceAsStream("api-config.properties")) {
            properties.load(input);
            BASE_URL = properties.getProperty("base.url");
            BASE_PATH = properties.getProperty("base.path");
        } catch (IOException e) {
            e.printStackTrace();
        }

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(BASE_PATH)
                .setContentType("application/json")
                .setAccept("application/json")
                .addFilter(new RequestLoggingFilter())
                .build();
    }
}
