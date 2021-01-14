package com.epam.esm.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TagControllerTest {
    private final String CONTEXT_PATH = "/api";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void test() {
        Map<String, Object> tag = new HashMap<>();
        tag.put("name", "tagTestName2");

        Response response = given()
                .contentType("application/json")
                .accept("application/json")
                .body(tag)
                .when()
                .post(CONTEXT_PATH + "/tags")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract()
                .response();

        Long generatedId = response.jsonPath().getLong("id");

        assertNotNull(generatedId);
    }
}
