package training.common;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import training.constants.Auth;
import training.constants.Path;

public class Rest_Utilities {

	public static String ENDPOINT;
	public static RequestSpecBuilder REQUEST_BUILDER;
	public static RequestSpecification REQUEST_SPEC;
	public static ResponseSpecBuilder RESPONSE_BUILDER;
	public static ResponseSpecification RESPONSE_SPEC;
	
	public static void set_Endpoint(String endpoint) {
		ENDPOINT = endpoint;
	}
	
	public static RequestSpecification get_Request_Specification() {
		AuthenticationScheme authScheme = 
				RestAssured.oauth(Auth.CONSUMER_KEY, Auth.CONSUMER_SECRET_KEY,
						Auth.ACCESS_TOKEN, Auth.ACCESS_SECRET_TOKEN);
		
		REQUEST_BUILDER = new RequestSpecBuilder();
		REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
		REQUEST_BUILDER.setAuth(authScheme);
		
		REQUEST_SPEC = REQUEST_BUILDER.build();
		return REQUEST_SPEC;
	}
	
}