package com.mg2.adk.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.mg2.adk.AppDevKit;
import com.mg2.adk.AppDevKitApiException;
import com.mg2.adk.AppDevKitMethod;
import com.mg2.adk.response.AndroidGetProductResponse;
import com.mg2.adk.response.AuthGetTokenResponse;
import com.mg2.adk.response.Product;

/**
 * Tests for AppDevKit API library.
 * 
 * @author MG2 Innovations LLC
 * 
 */
public class AppDevKitTest extends TestCase {

    /**
     * The API object.
     */
    AppDevKit api;

    /**
     * Properties to help tests.
     */
    Properties prop;

    /**
     * Sets up properties and API object.
     */
    @Override
    protected void setUp() {
	prop = new Properties();
	try {
	    prop.load(new FileInputStream("adk.properties"));
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	String appKey = prop.get("APP_KEY").toString();
	String appSecret = prop.get("APP_SECRET").toString();

	// Dummy values - replace with your own in adk.properties.
	// Pass true in as debug to enable logging
	api = new AppDevKit(appKey, appSecret, true);
    }

    /**
     * Test the /auth/get_token endpoint.
     */
    public void testAuthenticate() {
	AuthGetTokenResponse authToken = null;
	try {
	    authToken = api.getAuthToken();
	} catch (AppDevKitApiException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	assertNotNull(authToken);
	assertNotNull(authToken.getAuthToken());
	assertNotSame(authToken.getAuthToken(), "");
    }

    /**
     * Test the /android/get_product endpoint. <br>
     * Be sure to set the necessary properties in adk.properties.
     */
    public void testAndroidGetProduct() {

	String inAppData = prop.getProperty("INAPP_DATA");
	String inAppSig = prop.getProperty("INAPP_SIG");

	AuthGetTokenResponse authToken = null;
	AndroidGetProductResponse products = null;
	try {
	    authToken = api.getAuthToken();

	    products = api.getAndroidProduct(authToken.getAuthToken(),
		    inAppData, inAppSig);
	} catch (AppDevKitApiException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	for (Product p : products.getProducts()) {
	    System.out.println(p.toString());
	    p.getContent();
	}
    }

    /**
     * Tests serialization of the response from the /android/get_product
     * endpoint.
     */
    public void testAndroidGetProductSerialization() {

	String json = prop.getProperty("TEST_ANDROID_GET_PRODUCT_RESPONSE");

	Gson gson = new Gson();
	AndroidGetProductResponse response = gson.fromJson(json,
		AndroidGetProductResponse.class);

	System.out.println("Num Products: " + response.getProducts().length);

	for (Product p : response.getProducts()) {
	    System.out.println(p.toString());
	}
    }

    /**
     * Tests creation proper creation of an invalid request method exception.
     */
    public void testExceptions() {
	try {
	    api.rawApiCall(AppDevKitMethod.TEST_INVALID_REQUEST_METHOD);
	} catch (AppDevKitApiException e) {
	    assertEquals(e.getResponseCode(), 101);
	}

	try {
	    api.rawApiCall(AppDevKitMethod.TEST_INVALID_ARGUMENTS);
	} catch (AppDevKitApiException e) {
	    assertEquals(e.getResponseCode(), 102);
	}

	try {
	    api.rawApiCall(AppDevKitMethod.TEST_INVALID_AUTH_TOKEN);
	} catch (AppDevKitApiException e) {
	    assertEquals(e.getResponseCode(), 103);
	}

	try {
	    api.rawApiCall(AppDevKitMethod.TEST_INVALID_APP_KEY);
	} catch (AppDevKitApiException e) {
	    assertEquals(e.getResponseCode(), 104);
	}

	try {
	    api.rawApiCall(AppDevKitMethod.TEST_INVALID_API_SIGNATURE);
	} catch (AppDevKitApiException e) {
	    assertEquals(e.getResponseCode(), 105);
	}

	try {
	    api.rawApiCall(AppDevKitMethod.TEST_INAPP_SIGNATURE_VERIFICATION_EXCEPTION);
	} catch (AppDevKitApiException e) {
	    assertEquals(e.getResponseCode(), 120);
	}

	try {
	    api.rawApiCall(AppDevKitMethod.TEST_PRODUCT_ID_NOT_FOUND);
	} catch (AppDevKitApiException e) {
	    assertEquals(e.getResponseCode(), 130);
	}
    }
}
