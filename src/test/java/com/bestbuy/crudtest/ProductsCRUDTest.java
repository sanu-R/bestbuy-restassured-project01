package com.bestbuy.crudtest;

import com.bestbuy.info.ProductSteps;
import com.bestbuy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductsCRUDTest extends TestBase {

    static String name = "Powerwell AA Batteries";
    static String type = "clodType";
    static int price = 4;
    static String upc = "292347934";
    static int shipping = 5;
    static String description = "Work with all electronic devices";
    static String manufacturer = "Durona";
    static String model = "MZ23456B";
    static String url = "http://www.bestbuy.com/site/Powerwell";
    static String image = "http://img.bbystatic.com/product/292347934";
    static int productId;
    @Steps
    ProductSteps productSteps;

    @Title("This will create a new product")
    @Test
    public void test001() {

        ValidatableResponse response = productSteps.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
        productId=response.extract().path("id");

    }

    @Title("Verify if the product has added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = productSteps.getProductInfoByProductName(name);
        productId = (int) productMap.get("id");
        Assert.assertThat(productMap, hasValue(name));
    }
    @Title("This will get the Product with id ")
    @Test
    public void test003(){
        productSteps.getProductById(productId).log().all().statusCode(200);
    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test004() {
        name = name + "- updated";
        productSteps.updateProduct(productId, name, type, price, shipping, upc,description, manufacturer, model, url, image)
                .log().all().statusCode(200);
        HashMap<String, Object> productMap = productSteps.getProductInfoByProductName(name);
        Assert.assertThat(productMap, hasValue(name));

    }

    @Title("Delete the product and verify if the product is deleted!")
    @Test
    public void test005() {
        productSteps.deleteProduct(productId).statusCode(204);
        productSteps.getProductById(productId).statusCode(404);
    }


}
