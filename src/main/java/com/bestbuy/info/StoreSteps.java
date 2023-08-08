package com.bestbuy.info;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.junit.runner.RunWith;

import java.util.HashMap;

@RunWith(SerenityRunner.class)
public class StoreSteps {
    @Step
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city,
                                           String state, String zip, int lat, int lng, String hours) {
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);
        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .post()
                .then();
    }

    @Step("Getting the store information with name : {0}")
    public HashMap<String, Object> getStoreInfoByStoreName(String name) {
        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_STORE)
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);

    }

    @Step
    public ValidatableResponse updateStore(int storeId, String name, String type, String address, String address2, String city,
                                           String state, String zip,int lat, int lng, String hours) {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParam("storeID", storeId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }
        @Step("Deleting store information with storeId: {0}")
        public ValidatableResponse deleteStore(int storeId) {
            return SerenityRest.given()
                    .pathParam("storeID", storeId)
                    .when()
                    .delete(EndPoints.DELETE_STORE_BY_ID)
                    .then();

        }
    @Step("Getting store information with storeId : {0}")
    public ValidatableResponse getStoreById(int storeId) {
        return SerenityRest.given()
                .pathParam("storeID", storeId)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }


    }



