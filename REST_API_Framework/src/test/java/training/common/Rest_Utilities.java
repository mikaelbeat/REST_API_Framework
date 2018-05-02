package training.common;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import training.constants.Auth;
import training.constants.Path;
import static org.hamcrest.Matchers.lessThan;
import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.concurrent.TimeUnit;

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
	
	public static ResponseSpecification get_Response_Specification() {
		
		RESPONSE_BUILDER = new ResponseSpecBuilder();
		RESPONSE_BUILDER.expectStatusCode(200);
		RESPONSE_BUILDER.expectResponseTime(lessThan(3L), TimeUnit.SECONDS);
		RESPONSE_SPEC = RESPONSE_BUILDER.build();
		return RESPONSE_SPEC;
	}
	
	public static RequestSpecification create_Query_Parameters(RequestSpecification rspec,
			String param, String value) {
		return rspec.queryParam(param, value);
	}
	
	public static RequestSpecification create_Query_Parameters(RequestSpecification rspec,
			Map<String, String> queryMap) {
		return rspec.queryParams(queryMap);
	}
	
	public static RequestSpecification create_Path_Parameters(RequestSpecification rspec,
			String param, String value) {
		return rspec.pathParam(param, value);
	}
	
	public static Response get_Response() {
		return given().get(ENDPOINT);
	}
	
	public static Response get_Response(RequestSpecification reqSpec, String type) {
		REQUEST_SPEC.spec(reqSpec);
		Response response = null;
		if (type.equalsIgnoreCase("get")) {
			response = given().spec(REQUEST_SPEC).get(ENDPOINT);
		} else if (type.equalsIgnoreCase("post")) {
			response = given().spec(REQUEST_SPEC).post(ENDPOINT);
		} else if (type.equalsIgnoreCase("put")) {
			response = given().spec(REQUEST_SPEC).put(ENDPOINT);
		} else if (type.equalsIgnoreCase("delete")) {
			response = given().spec(REQUEST_SPEC).delete(ENDPOINT);
		} else {
			System.out.println("Type is not supported.");
		}
		response.then().log().all();
		response.then().spec(RESPONSE_SPEC);
		return response;
	}
	
	public static JsonPath get_JsonPath(Response res) {
		String path = res.asString();
		return new JsonPath(path);
	}
	
	public static XmlPath get_XmlPath(Response res) {
		String path = res.asString();
		return new XmlPath(path);
	}
		
}