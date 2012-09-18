package com.mg2.adk.response;

/**
 * 
 * @author MG2 Innovations LLC
 *
 */
public class AndroidGetProductResponse {

    /**
     * The list of products
     */
    Product[] products;

    /**
     * Initializes a new instance of the AndroidGetProductResponse class.
     * 
     * Empty constructor for needed for GSON.
     */
    public AndroidGetProductResponse() {
    }
    
    public Product[] getProducts() {
	return this.products;
    }

}
