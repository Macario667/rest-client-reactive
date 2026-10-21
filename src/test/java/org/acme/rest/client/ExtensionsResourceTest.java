package org.acme.rest.client;

import io.quarkus.test.common.WithTestResource;
import io.quarkus.test.junit.QuarkusTest;

import org.acme.rest.client.resources.WireMockExtensions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@WithTestResource(WireMockExtensions.class)
public class ExtensionsResourceTest {

    @Test
    public void testExtensionsIdEndpoint() {
        given()
            .when().get("/extension/id/io.quarkus:quarkus-rest-client")
            .then()
            .statusCode(200)
            .body("$.size()", is(1),
                "[0].id", is("io.quarkus:quarkus-rest-client"),
                "[0].name", is("REST Client"),
                "[0].keywords.size()", greaterThan(1),
                "[0].keywords", hasItem("rest-client"));
    }

    @Test
    public void testExtensionIdAsyncEndpoint() {
        given()
            .when().get("/extension/id-async/io.quarkus:quarkus-rest-client")
            .then()
            .statusCode(200)
            .body("$.size()", is(1),
                "[0].id", is("io.quarkus:quarkus-rest-client"),
                "[0].name", is("REST Client"),
                "[0].keywords.size()", greaterThan(1),
                "[0].keywords", hasItem("rest-client"));
    }

    @Test
    public void testExtensionIdMutinyEndpoint() {
        given()
            .when().get("/extension/id-uni/io.quarkus:quarkus-rest-client")
            .then()
            .statusCode(200)
            .body("$.size()", is(1),
                "[0].id", is("io.quarkus:quarkus-rest-client"),
                "[0].name", is("REST Client"),
                "[0].keywords.size()", greaterThan(1),
                "[0].keywords", hasItem("rest-client"));
    }

    @Test 
    public void testExtensionServerError() {
        given()
            .when().get("/extension/id/error-test")
            .then()
            .statusCode(500);
    }

    @Test 
    public void testCustomClientHeader() {
        given()
            .when().get("/extension/id/header-test")
            .then()
            .statusCode(200)
            .body("$.size()", is(0));
            
    }

    @Test
    public void testDynamicClientHeaders() {
        given()
            .when().get("/extension/id-header/dynamic-header-test")
            .then()
            .statusCode(200)
            .body("$.size()", is(0));
    }

    @Test 
    public void testFullHttpResponse() {
        given()
            .when()
                .get("/extension/full-response/io.quarkus:quarkus-rest-client")
            .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"));
    }

    
    @Test
    public void testRequestFilter() {
        given()
            .when()
                .get("/extension/id/filter-test")
            .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }

}
