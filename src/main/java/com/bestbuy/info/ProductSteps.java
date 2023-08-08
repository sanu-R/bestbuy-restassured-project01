package com.bestbuy.info;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.junit.runner.RunWith;

import java.util.HashMap;

@RunWith(SerenityRunner.class)
public class ProductSteps {
    @Step
    public ValidatableResponse createProduct(String name, String type, int price, String upc,
                                             int shipping, String description, String manufacturer,
                                             String model, String url, String image) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);
        return SerenityRest.given()
                .basePath(Path.PRODUCTS)
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post()
                .then();
    }

    @Step("Getting the product information with name : {0}")
    public HashMap<String, Object> getProductInfoByProductName(String name) {
        String p1 = "findAll{it.name == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given()
                .basePath(Path.PRODUCTS)
                .queryParam("name",name)
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);

    }

    @Step("Updating Product information with ProductID: {0}, name : {1}, type: {2}, price: {3},  upc: {4},shipping: {5},description: {6}, manufacturer: {7},model: {8},url: {9},image:{10}")
    public ValidatableResponse updateProduct(int productId, String name, String type, int price, int shipping, String upc, String description, String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);
        return SerenityRest.given()
                .basePath(Path.PRODUCTS)
                .header("Content-Type", "application/json")
                .pathParam("id", productId)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();

    }

    @Step("Deleting product information with productId: {0}")
    public ValidatableResponse deleteProduct(int productId) {
        return SerenityRest.given()
                .basePath(Path.PRODUCTS)
                .pathParam("id", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();

    }
    @Step("Getting product information with productId : {0}")
    public ValidatableResponse getProductById(int productId) {
        return SerenityRest.given()
                .basePath(Path.PRODUCTS)
                .pathParam("id", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }


}
