package com.mg2.adk;

/**
 * 
 * @author MG2 Innovations LLC
 *
 */
public enum AppDevKitMethod {
    AUTH_GET_TOKEN("auth/get_token?"),
    ANDROID_GET_PRODUCT("android/get_product?"),
    
    // Test method endpoints
    TEST_INVALID_REQUEST_METHOD("test/invalid_request_method"),
    TEST_INVALID_ARGUMENTS("test/invalid_arguments"),
    TEST_INVALID_AUTH_TOKEN("test/invalid_auth_token"),
    TEST_INVALID_APP_KEY("test/invalid_app_key"),
    TEST_INVALID_API_SIGNATURE("test/invalid_api_signature"),
    TEST_INAPP_SIGNATURE_VERIFICATION_EXCEPTION("test/inapp_signature_verfication_exception"),
    TEST_PRODUCT_ID_NOT_FOUND("test/product_id_not_found");
    
    /**
     * The API method to invoke.
     */
    String method;
    
    private AppDevKitMethod(String method) {
	this.method = method;
    }
    
    @Override
    public String toString() {
	return this.method;
    }
}
