package com.github.IamNatri.quarkussocial.rest;

import com.github.IamNatri.quarkussocial.rest.dto.CreateUserRequest;
import com.github.IamNatri.quarkussocial.rest.dto.ResponseError;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@QuarkusTest
@TestHTTPEndpoint(UserResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    @Test
    @DisplayName("should create an user successfully")
    @Order(1)
    public void createUserTest(){
        var user = new CreateUserRequest();
        user.setUserName("ace");
        user.setAge(30);

        var response =
                given()
                        .contentType(ContentType.JSON)
                        .body(user)
                .when()
                        .post()
                        .then()
                        .extract().response();

        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));

    }

    @Test
    @DisplayName("should return error when json is not valid")
    @Order(2)
    public void createUserValidationErrorTest(){
        var user = new CreateUserRequest();
        user.setAge(null);
        user.setUserName("");

        var response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post()
                .then()
                .extract().response();

        assertEquals(ResponseError.UNPROCESSABLE_ENTITY, response.statusCode());
        assertEquals("Invalid fields", response.jsonPath().getString("message"));
        List<Map<String, String>> errors = response.jsonPath(). getList("fieldErrors");
        assertNotNull(errors.get(0).get("message"));
        assertNotNull(errors.get(1).get("message"));


    }

    @Test
    @DisplayName("should list all users")
    @Order(3)
    public void listAllUsersTest(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }
}
