package com.github.IamNatri;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


@QuarkusTest
class ExampleResourceTest {
    @Test
    void testPostEndpoint() {
        given()
                .when().post("/users/1/posts")
                .then()
                .statusCode(200);
    }

}