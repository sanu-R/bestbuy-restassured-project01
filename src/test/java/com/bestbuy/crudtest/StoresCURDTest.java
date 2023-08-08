package com.bestbuy.crudtest;

import com.bestbuy.info.StoreSteps;
import com.bestbuy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

//@RunWith(SerenityRunner.class)
public class StoresCURDTest extends TestBase {
    static String name = "StarStore";
    static String type = "Smallbox";
    static String address = "234,Downing Street";
    static String address2 = "privet Road";
    static String city = "London";
    static String state = "WS";
    static String zip = "4545";
    static int lat = 32;
    static int lng = 43;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-7; Fri: 10-9; Sat: 10-9; Sun: 10-5";
    static int storeId;
    @Steps
    StoreSteps storeSteps;
    @Title("This will create a new Store")
    @Test
    public void test001() {
        ValidatableResponse response = storeSteps.createStore( name,  type,  address,  address2,  city,
                 state,  zip,  lat,  lng,  hours);
        response.log().all().statusCode(200);
    }
    @Title("Verify if the Store has added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByStoreName(name);
        storeSteps.getStoreInfoByStoreName(name);
        Assert.assertThat(storeMap, hasValue(name));
       storeId = (int) storeMap.get("id");

    }
    @Title("Update the store information and verify the updated information")
    @Test
    public void test003() {
        name = name + "- updated";
        storeSteps.updateStore(storeId, name, type, address, address2, city, state, zip, lat, lng, hours)
                .log().all().statusCode(200);
        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByStoreName(name);
        Assert.assertThat(storeMap, hasValue(name));

    }
    @Title("Delete the store and verify if the store is deleted!")
    @Test
    public void test004() {
       storeSteps.deleteStore(storeId).statusCode(200);
       storeSteps.getStoreById(storeId).statusCode(404);
    }

}
