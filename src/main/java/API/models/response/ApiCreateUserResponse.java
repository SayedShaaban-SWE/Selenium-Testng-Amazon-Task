package API.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiCreateUserResponse {
    @JsonProperty("name")
    public String name;

    @JsonProperty("job")
    public String job;

    @JsonProperty("id")
    public int id;

    @JsonProperty("age")
    public String age;

    @JsonProperty("createdAt")
    public String createdAt;
}
