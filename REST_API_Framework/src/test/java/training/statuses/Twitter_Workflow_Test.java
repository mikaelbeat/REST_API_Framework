package training.statuses;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import training.common.Rest_Utilities;
import training.constants.Endpoints;
import training.constants.Path;

public class Twitter_Workflow_Test {
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String tweetId = "";
	
	@BeforeClass
	public void setup() {
		reqSpec = Rest_Utilities.get_Request_Specification();
		reqSpec.basePath(Path.BASE_PATH);
		resSpec = Rest_Utilities.get_Response_Specification();
	}
	
	@Test
	public  void post_Tweet() {
		Response response =
				given()
					.spec(Rest_Utilities.create_Path_Parameters(reqSpec, "Status", "My first tweet"))
				.when()
					.post(Endpoints.STATUSES_TWEET_POST)
				.then()
					.spec(resSpec)
					.extract()
					.response();
		JsonPath jsPath = Rest_Utilities.get_JsonPath(response);
		tweetId = jsPath.get("id_str");
		System.out.println("The tweet id is: " + tweetId);
	}
	
	@Test (dependsOnMethods = {"post_Tweet"})
	public void read_Tweet() {
		Rest_Utilities.set_Endpoint(Endpoints.STATUSES_TWEET_READ_SINGLE);
		Response res = Rest_Utilities.get_Response(
				Rest_Utilities.create_Query_Parameters(reqSpec, "id", tweetId), "get");
		String text = res.path("text");
		System.out.println("The tweet text is: " + text);
	}
	
	@Test (dependsOnMethods = {"read_Tweet"})
	public void delete_Tweet() {
		given()
			.spec(Rest_Utilities.create_Path_Parameters(reqSpec, "id", tweetId))
		.when()
			.post(Endpoints.STATUSES_TWEET_DESTROY)
		.then()
			.spec(resSpec);
		
	}
	
}