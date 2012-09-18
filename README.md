AppDevKit API for Java
======================

Java library for interacting with the AppDevKit.com API.  This library is open 
source and free to use.  You will need to create an account on http://AppDevKit.com. 

API documentation: 
 - http://AppDevKit.com/api/docs

Authorization
-------------

Example: 

```java
AuthGetTokenResponse authToken = null;
try {
    authToken = api.getAuthToken();
} catch (AppDevKitApiException e) {
    // Handle exception...
}
```

Get Product - Android
---------------------

```java
AuthGetTokenResponse authToken = null;
AndroidGetProductResponse products = null;
try {
    authToken = api.getAuthToken();

    products = api.getAndroidProduct(authToken.getAuthToken(),
	    inAppData, inAppSig);
} catch (AppDevKitApiException e) {
	// Handle exception
}

TODO: 
 - Finalize exception handling and response codes.

Note: This library and the API is NOT final and subject to change.  Please 
consider this a beta release until otherwise noted.