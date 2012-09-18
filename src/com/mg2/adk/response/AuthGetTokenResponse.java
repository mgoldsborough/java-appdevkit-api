package com.mg2.adk.response;

/**
 * 
 * @author MG2 Innovations LLC
 *
 */
public class AuthGetTokenResponse {
    
    Auth auth;
    // TODO: DateCreated
    // TODO: ExiprationTime
    
    public AuthGetTokenResponse() {
	
    }
    
    public String getAuthToken() {
	return this.auth.token;
    }
    
    public class Auth {
	public String token;
	
	public Auth() {
	}
    }
}
