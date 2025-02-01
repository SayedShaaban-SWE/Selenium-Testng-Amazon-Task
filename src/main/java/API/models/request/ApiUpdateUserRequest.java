package API.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiUpdateUserRequest {
    @JsonProperty("name")
    public String name;

    @JsonProperty("job")
    public String job;
}
