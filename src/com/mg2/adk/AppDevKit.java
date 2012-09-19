package com.mg2.adk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.mg2.adk.response.AndroidGetProductResponse;
import com.mg2.adk.response.AuthGetTokenResponse;

/**
 * Creates an easy to use interface for interacting with the AppDevKit.com API. <br>
 * <br>
 * API Documentation: http://AppDevKit.com/api/docs<br>
 * Source: https://github.com/MG2Innovations/java-appdevkit-api
 * 
 * @author MG2 Innovations LLC
 * 
 */
public class AppDevKit {

    private static final String API_URL = "http://appdevkit.com/api/";

    // TODO: Encrypt
    private String appKey;
    private String appSecret;

    private boolean debug;

    /**
     * Creates an instance of the AppDevKit class. Does NOT print any debug
     * logging.
     * 
     * @param appKey
     *            Your specific application key.
     * @param appSecret
     *            Your specific application secret.
     */
    public AppDevKit(String appKey, String appSecret) {
	this.appKey = appKey;
	this.appSecret = appSecret;
	this.debug = false;
    }

    /**
     * Creates an instance of the AppDevKit class.
     * 
     * @param appKey
     *            Your specific application key.
     * @param appSecret
     *            Your specific application secret.
     * @param debug
     *            True to enable debug logging. False to disable.
     */
    public AppDevKit(String appKey, String appSecret, boolean debug) {
	this.appKey = appKey;
	this.appSecret = appSecret;
	this.debug = debug;
    }

    /**
     * Obtains an authorization token from AppDevKit.com. Hits the
     * /auth/get_token endpoint.
     * 
     * @return The response object containing the authorization token.
     * @throws AppDevKitApiException
     */
    public AuthGetTokenResponse getAuthToken() throws AppDevKitApiException {
	// Map to hold values. Sorted by keys in ABC order as per API.
	SortedMap<String, String> map = new TreeMap<String, String>();

	// Generate the appropriate URN.
	String urn = genUrn(map);

	if (debug)
	    System.out.println("getAuthToken URN: " + urn);

	// Perform GET
	String response = "";

	response = httpGET(AppDevKitMethod.AUTH_GET_TOKEN, urn);

	// Use GSON to serialize into Auth object
	Gson gson = new Gson();
	AuthGetTokenResponse auth = gson.fromJson(response,
		AuthGetTokenResponse.class);

	if (debug)
	    System.out.println("Auth Token: " + auth.getAuthToken());

	return auth;
    }

    /**
     * 
     * 
     * http://developer.android.com/guide/google/play/billing/billing_reference.
     * html#billing-intents
     * 
     * @param authToken
     *            An authorization token obtained from the
     *            {@link #getAuthToken()} method.
     * @param inAppData
     *            The 'inapp_signed_data' which Google sends upon a valid
     *            purchase.
     * @param inAppSig
     *            The 'inapp_signature' which Google sends upon a valid
     *            purchase.
     * @return The response object containing the list of products.
     * @throws AppDevKitApiException
     */
    public AndroidGetProductResponse getAndroidProduct(String authToken,
	    String inAppData, String inAppSig) throws AppDevKitApiException {
	SortedMap<String, String> map = new TreeMap<String, String>();

	// Base64 encode inapp data
	String encInAppData = Base64.encodeBase64String(inAppData.getBytes());
	String encInAppSig = Base64.encodeBase64String(inAppSig.getBytes());

	map.put("auth_token", authToken);
	map.put("inapp_data", encInAppData);
	map.put("inapp_sig", encInAppSig);

	String urn = genUrn(map);

	String response = "";

	response = httpGET(AppDevKitMethod.ANDROID_GET_PRODUCT, urn);

	// Use GSON to parse into GetProduct object
	Gson gson = new Gson();
	AndroidGetProductResponse getProduct = gson.fromJson(response,
		AndroidGetProductResponse.class);

	return getProduct;
    }

    public void rawApiCall(AppDevKitMethod method) throws AppDevKitApiException {
	httpGET(method, "");
    }

    /**
     * Generates a proper URN based on the map provided.
     * 
     * @param map
     *            The map of key/value pairs
     * @return The URN string.
     * @throws AppDevKitApiException
     */
    private String genUrn(final SortedMap<String, String> map)
	    throws AppDevKitApiException {
	StringBuilder urnBuilder = new StringBuilder();
	StringBuilder signatureBuilder = new StringBuilder();

	// Append secret to sigBuilder and put api_key in map
	map.put("app_key", appKey);

	signatureBuilder.append(appSecret);

	if (debug) {
	    System.out.println("PRINTING KEYS IN ORDER");
	    for (Entry<String, String> entry : map.entrySet()) {
		System.out.println("KEY: " + entry.getKey());
		System.out.println(entry.getValue());
		System.out.println();
	    }
	}

	// Iterate over all entries,
	// Append <key><value> to sigBuiler
	// Append <key>=<value> to urnBuilder
	for (Entry<String, String> entry : map.entrySet()) {

	    urnBuilder.append(String.format("%s=%s&", entry.getKey(),
		    entry.getValue()));

	    signatureBuilder.append(String.format("%s%s", entry.getKey(),
		    entry.getValue()));
	}

	String signature = signatureBuilder.toString();

	// Generate hash and append to URN as api_sig parameter
	String hash = getMD5(signatureBuilder.toString());
	urnBuilder.append("api_sig=");
	urnBuilder.append(hash);

	if (debug) {
	    System.out.println("Pre-Hash Value:" + signature);
	    System.out.println("Hash: " + hash);
	    System.out.println("URN: " + urnBuilder.toString());
	    System.out.println("SIG: " + signatureBuilder.toString());
	}

	return urnBuilder.toString();
    }

    /**
     * Generates an MD5 hash of the provided string.
     * 
     * @param str
     *            The string to hash.
     * @return The generated hash.
     * @throws AppDevKitApiException
     */
    private String getMD5(String str) throws AppDevKitApiException {
	byte[] bytesOfMessage = null;
	try {
	    bytesOfMessage = str.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	    System.err.println(e.getMessage());
	    throw new AppDevKitApiException(e);
	}

	MessageDigest md = null;
	try {
	    md = MessageDigest.getInstance("MD5");
	} catch (NoSuchAlgorithmException e) {
	    System.err.println(e.getMessage());
	    throw new AppDevKitApiException(e);
	}

	byte[] thedigest = md.digest(bytesOfMessage);

	BigInteger hash = new BigInteger(1, thedigest);
	String hashText = hash.toString(16);
	while (hashText.length() < 32) {
	    hashText = "0" + hashText;
	}
	return hashText;
    }

    /**
     * Performs a synchronous HTTP get request to the AppDevKit API.
     * 
     * @param method
     *            The AppDevKit API method to invoke.
     * @param urn
     *            The urn.
     * @return The json response.
     * @throws IOException
     */
    private String httpGET(AppDevKitMethod method, String urn)
	    throws AppDevKitApiException {

	String str = "";
	BufferedReader in = null;

	try {
	    URL url = new URL(API_URL + method + urn);

	    if (debug)
		System.out.println("URL: " + url.toString());

	    in = new BufferedReader(new InputStreamReader(url.openStream()));
	    StringBuilder builder = new StringBuilder();

	    while ((str = in.readLine()) != null) {
		if (debug)
		    System.out.println(str);
		builder.append(str);
	    }

	    str = builder.toString();
	} catch (MalformedURLException e) {
	    System.err.println("MalformedURLException: " + urn);
	    throw new AppDevKitApiException(e.getMessage(), e);
	} catch (IOException e) {
	    throw new AppDevKitApiException(e.getMessage(), e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		    // Ignore...
		}
	    }
	}

	if (debug) {
	    System.out.println("Response: " + str);
	}

	// Check response for error
	if (str.startsWith(("{\"exception\""))) {
	    // API returned an API exception, serialize and throw.
	    Gson gson = new Gson();
	    ExceptionWrapper wrapper = gson.fromJson(str,
		    ExceptionWrapper.class);
	    throw wrapper.exception;
	}

	return str;
    }

    public class ExceptionWrapper {
	public AppDevKitApiException exception;

	public ExceptionWrapper() {
	}
    }
}
