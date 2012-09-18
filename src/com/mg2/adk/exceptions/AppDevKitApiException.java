package com.mg2.adk.exceptions;

/**
 * 
 * @author MG2 Innovations LLC
 *
 */
public class AppDevKitApiException extends Throwable {

    String name;
    String description;
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

    public AppDevKitApiException(String name, String description,
	    int responseCode) {
	super();
	this.name = name;
	this.description = description;
	this.responseCode = responseCode;
    }

    public String getName() {
	return name;
    }

    public String getDescription() {
	return description;
    }

    public int getResponseCode() {
	return responseCode;
    }
}
