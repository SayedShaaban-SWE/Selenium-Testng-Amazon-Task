package utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserData {
    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("streetName")
    private String streetName;

    @JsonProperty("buildingNo")
    private String buildingNo;

    @JsonProperty("cityName")
    private String cityName;

    @JsonProperty("districtName")
    private String districtName;
}
