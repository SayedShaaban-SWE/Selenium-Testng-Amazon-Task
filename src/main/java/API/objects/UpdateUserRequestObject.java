package API.objects;

import API.models.request.ApiCreateUserRequest;
import API.models.response.ApiUpdateUserResponse;
import API.utils.CleanObject;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UpdateUserRequestObject {
    static ApiUpdateUserResponse apiUpdateUserResponse;
    public static String updateUserObjectMapping(String UserName, String job) throws JsonProcessingException {
        apiUpdateUserResponse = new ApiUpdateUserResponse();
        apiUpdateUserResponse.name = UserName;
        apiUpdateUserResponse.job = job;
        return CleanObject.getCleanObject(apiUpdateUserResponse);
    }
}
