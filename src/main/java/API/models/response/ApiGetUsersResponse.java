package API.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiGetUsersResponse {
    @JsonProperty("data")
    public Data data;

    @JsonProperty("support")
    public Support support;

    public static class Data{
        @JsonProperty("id")
        public int id;

        @JsonProperty("email")
        public String email;

        @JsonProperty("first_name")
        public String first_name;

        @JsonProperty("last_name")
        public String last_name;

        @JsonProperty("avatar")
        public String avatar;
    }

    public static class Support{
        @JsonProperty("url")
        public String url;

        @JsonProperty("text")
        public String text;
    }


}
