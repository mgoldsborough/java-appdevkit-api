package com.mg2.adk;

/**
 * 
 * @author MG2 Innovations LLC
 *
 */
public enum AppDevKitMethods {
    AUTH_GET_TOKEN("auth/get_token?"),
    ANDROID_GET_PRODUCT("android/get_product?");
    
    /**
     * The API method to invoke.
     */
    String method;
    
    private AppDevKitMethods(String method) {
	this.method = method;
    }
    
    @Override
    public String toString() {
	return this.method;
    }
}
