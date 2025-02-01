package API.utils;

import API.models.response.ApiCreateUserResponse;
import API.models.response.ApiGetUsersResponse;
import API.models.response.ApiUpdateUserResponse;
import API.restwrapper.Headers;
import com.fasterxml.jackson.core.JsonProcessingException;
import API.restwrapper.RestHelper;

import java.util.Map;

import static API.objects.CreateUserRequestObject.createUserObjectMapping;
import static API.objects.UpdateUserRequestObject.updateUserObjectMapping;

public class UserUtils {
    public static Map<String, Object> getCreatedUserResponse(int userId, String UserName, String job, int age) throws JsonProcessingException {
        return  RestHelper.restPostWithBodyAndStatus(Urls.getBaseUrl(), Endpoints.CREATE_USERS, Headers.genericHeaders(),
                createUserObjectMapping(userId, UserName, job, age), ApiCreateUserResponse.class);
    }
    public static Map<String, Object> getUserResponse(int userId){
        return RestHelper.restGetWithBodyAndStatus(Urls.getBaseUrl(), Endpoints.GET_USER.withId(userId), Headers.genericHeaders(), ApiGetUsersResponse.class);
    }

    public static Map<String, Object> getUpdatedUserResponse(int userId, String UserName, String job) throws JsonProcessingException {
        return RestHelper.restPutWithBodyAndStatus(Urls.getBaseUrl(), Endpoints.GET_USER.withId(userId), Headers.genericHeaders(),
                updateUserObjectMapping(UserName, job), ApiUpdateUserResponse.class);
    }
}
