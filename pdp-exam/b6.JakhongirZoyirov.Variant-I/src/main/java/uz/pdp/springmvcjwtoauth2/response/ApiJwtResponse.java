package uz.pdp.springmvcjwtoauth2.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiJwtResponse {

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

}
