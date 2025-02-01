package tests;

import API.models.response.ApiCreateUserResponse;
import API.models.response.ApiGetUsersResponse;
import API.models.response.ApiUpdateUserResponse;
import API.utils.UserUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.Logger;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiTests {
    static SoftAssert softAssert = new SoftAssert();
    private static ApiCreateUserResponse apiCreateUserResponse;
    private static int createUserResponseStatusCode;
    private static ApiGetUsersResponse apiGetUsersResponse;
    private static int getUserResponseStatusCode;
    private static ApiUpdateUserResponse apiUpdateUserResponse;
    private static int updatedUserResponseStatusCode;

    @BeforeClass
    public void objectsInitialization(){
        try {
            Map<String, Object> createdUserResponse = UserUtils.getCreatedUserResponse(2,"Sayed", "TAE Engineer", 27);
            apiCreateUserResponse= (ApiCreateUserResponse) createdUserResponse.get("responseBody");
            createUserResponseStatusCode= (int) createdUserResponse.get("responseStatusCode");

            Map<String, Object> getUserResponse = UserUtils.getUserResponse(apiCreateUserResponse.id);
            apiGetUsersResponse= (ApiGetUsersResponse) getUserResponse.get("responseBody");
            getUserResponseStatusCode= (int) getUserResponse.get("responseStatusCode");

            Map<String, Object> updatedUserResponse = UserUtils.getUpdatedUserResponse(apiCreateUserResponse.id,"Sayed2", "TAE Engineer 2");
            apiUpdateUserResponse= (ApiUpdateUserResponse) updatedUserResponse.get("responseBody");
            updatedUserResponseStatusCode= (int) updatedUserResponse.get("responseStatusCode");

        } catch (JsonProcessingException e) {
            Logger.info("The parsing of the api is failed:[%s]", e.getMessage());
        }
    }
    @Test
    public void createUser() {
        softAssert.assertEquals(createUserResponseStatusCode, 201,
                "Validate if the create user request succeeded");
        softAssert.assertEquals(apiCreateUserResponse.id,2,
                "Validate that user id is not null");
        softAssert.assertEquals(apiCreateUserResponse.name,
                "Sayed",
                "Validate the expected and actual name values are equals");
        softAssert.assertEquals(apiCreateUserResponse.job,
                "TAE Engineer",
                "Validate the expected and actual job values are equals");
        softAssert.assertEquals(apiCreateUserResponse.age,
                27,
                "Validate the expected and actual age values are equals");
    }

    @Test
    public void retrieveUser() {
        softAssert.assertEquals(getUserResponseStatusCode, 200,
                "Validate if the get user request succeeded");
        softAssert.assertEquals(apiGetUsersResponse.data.id,2,
                "Validate that user id is equal 2");
    }

    @Test
    public void updateUser() {
        softAssert.assertEquals(updatedUserResponseStatusCode, 200,
                "Validate if the update user request succeeded");
        softAssert.assertEquals(apiUpdateUserResponse.name,
                "Sayed2",
                "Validate the expected and actual name values are equals");
        softAssert.assertEquals(apiUpdateUserResponse.job,
                "TAE Engineer 2",
                "Validate the expected and actual job values are equals");
    }
}
