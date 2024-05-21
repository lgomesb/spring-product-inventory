package com.barbosa.ms.productinventory.productinventory.controllers;

import com.barbosa.ms.productinventory.productinventory.ProductInventoryApplicationTests;
import com.barbosa.ms.productinventory.productinventory.controller.ProductInventoryController;
import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import com.barbosa.ms.productinventory.productinventory.services.ProductInventoryService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ActiveProfiles(value = "test")
@SpringBootTest(classes = {ProductInventoryApplicationTests.class}, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class ProductInventoryControllerTest {

    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final Integer QUANTITY = 1;
    private static final UUID STATIC_UUID = UUID.randomUUID();
    private static final String STATIC_URI = "/product-inventory/";

    @LocalServerPort
    private int port;

    @MockBean
    private ProductInventoryService service;

    @InjectMocks
    private ProductInventoryController controller;

    private ProductInventoryRecord productinventoryRecord;

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    @BeforeEach
    void setup() {
        productinventoryRecord = ProductInventoryRecord.builder()
                .id(UUID.randomUUID())
                .productId(PRODUCT_ID)
                .quantity(QUANTITY)
                .build();
    }

    @Test
    void shouldSucceededWhenCallCreate() {
        String request = given.productInventoryRequest();
        given.createProductInventoryByService();
        Response response = when.createProductInventoryByAPI(request);
        then.shouldBeSuccessCreatedProductInventoryValidation(response);
    }

    @Test
    void shouldSucceededWhenCallFindById() {
        when(service.findById(any(UUID.class))).thenReturn(productinventoryRecord);

        Response response = given()
            .port(port)
            .contentType(ContentType.JSON)
            .pathParam("id", STATIC_UUID.toString())
            .when()
            .get(STATIC_URI + "{id}")
            .then()
            .log().all()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .response();

        assertNotNull(response);
        assertNotNull(response.getBody().jsonPath().getString("id"));

    }

    @Test
    void shouldSucceededWhenCallUpdate() {
        String request = given.productInventoryRequest();
        when(service.findById(any(UUID.class))).thenReturn(productinventoryRecord);

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .pathParam("id", STATIC_UUID.toString())
            .body(request)
            .log().all()
            .when()
            .put(STATIC_URI + "{id}")
            .then()
            .assertThat()
            .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Test
    @Order(3)
    void shouldSucceededWhenCallDelete() {

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .pathParam("id", STATIC_UUID.toString())
            .log().all()
            .when()
            .delete(STATIC_URI + "{id}")
            .then()
            .assertThat()
            .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    void shouldSucceededWhenCallListAll() {
        when(service.listAll()).thenReturn(
                Collections.singletonList(new ProductInventoryRecord(STATIC_UUID, PRODUCT_ID, QUANTITY)));

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(STATIC_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

    }


    private class Given {
        public String productInventoryRequest() {
            return String.format("{\"productId\": \"%s\", \"quantity\": \"%s\"}", productinventoryRecord.productId(), productinventoryRecord.quantity());
        }

        public void createProductInventoryByService() {
            when(service.create(any(ProductInventoryRecord.class))).thenReturn(productinventoryRecord);

        }
    }

    private class When {
        public Response createProductInventoryByAPI(String request) {
            return given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .log().all()
                    .when()
                    .post(STATIC_URI)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .response();
        }
    }

    private class Then {
        public void shouldBeSuccessCreatedProductInventoryValidation(Response response) {
            assertNotNull(response);
            String idProductInventory = response.getHeader("Location");
            idProductInventory = idProductInventory.substring(idProductInventory.lastIndexOf("/")+1);
            assertNotNull(idProductInventory);
            assertFalse(idProductInventory.isEmpty());
        }
    }
}
