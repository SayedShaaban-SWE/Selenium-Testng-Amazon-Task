package API.objects;

import API.models.request.ApiCreateUserRequest;
import API.utils.CleanObject;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CreateUserRequestObject {
    static ApiCreateUserRequest apiCreateUserRequest;
    public static String createUserObjectMapping(int userId, String UserName, String job, int age) throws JsonProcessingException {
        apiCreateUserRequest = new ApiCreateUserRequest();
        apiCreateUserRequest.id = userId;
        apiCreateUserRequest.name = UserName;
        apiCreateUserRequest.job = job;
        apiCreateUserRequest.age = age;
        return CleanObject.getCleanObject(apiCreateUserRequest);
    }
}
