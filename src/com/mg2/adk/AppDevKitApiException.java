package com.mg2.adk;

import com.google.gson.annotations.SerializedName;

/**
 * Exception thrown by all AppDevKit API calls. The responseCode field indicates
 * which exception occured.
 * 
 * @author MG2 Innovations LLC
 * 
 */
public class AppDevKitApiException extends Throwable {

    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    @SerializedName("response_code")
    int responseCode;

    public AppDevKitApiException() {
	super();
	// TODO Auto-generated constructor stub
    }

    public AppDevKitApiException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	// TODO Auto-generated constructor stub
    }

    public AppDevKitApiException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }

    public AppDevKitApiException(String message) {
	super(message);
	// TODO Auto-generated constructor stub
    }

    public AppDevKitApiException(Throwable cause) {
	super(cause);
	// TODO Auto-generated constructor stub
    }

    public int getResponseCode() {
	return this.responseCode;
    }
}
