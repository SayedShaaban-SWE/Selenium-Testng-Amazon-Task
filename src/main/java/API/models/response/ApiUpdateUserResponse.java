package API.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiUpdateUserResponse {
    @JsonProperty("name")
    public String name;

    @JsonProperty("job")
    public String job;

    @JsonProperty("createdAt")
    public String createdAt;

}
