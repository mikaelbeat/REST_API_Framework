package training.statuses;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import training.common.Rest_Utilities;
import training.constants.Endpoints;
import training.constants.Path;
import static org.hamcrest.Matchers.hasItem;

import java.util.ArrayList;

public class User_Timeline_Test {
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	
	@BeforeClass
	public void setup() {
		reqSpec = Rest_Utilities.get_Request_Specification();
		reqSpec.queryParam("user_id", "MikaelBeat");
		reqSpec.basePath(Path.BASE_PATH);
		resSpec = Rest_Utilities.get_Response_Specification();
	}
	
	@Test
	public void read_Tweets() {
		given()
			.spec(reqSpec)
		.when()
			.get(Endpoints.STATUSES_USER_TIMELINE)
		.then()
		.log().all()
			.spec(resSpec)
			.body("user.screen_name", hasItem("MikaelBeat"));
	}
	
	@Test
	public void read_Tweets2() {
		Rest_Utilities.set_Endpoint(Endpoints.STATUSES_USER_TIMELINE);
		Response res = Rest_Utilities.get_Response(
				Rest_Utilities.create_Query_Parameters(reqSpec, "count", "1"), "get");
		ArrayList<String> screenNameList = res.path("user.screen_name");
		System.out.println("Screen name assertion from test 2: " + screenNameList);
		Assert.assertTrue(screenNameList.contains("MikaelBeat"));
	}

}