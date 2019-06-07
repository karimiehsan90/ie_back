package ir.asta.wise.core.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMSTokenRequest {
    @JsonProperty("UserApiKey")
    private String userApiKey;

    @JsonProperty("SecretKey")
    private String secretKey;

    public SMSTokenRequest(String userApiKey, String secretKey) {
        this.userApiKey = userApiKey;
        this.secretKey = secretKey;
    }

    public SMSTokenRequest(){}

    public String getUserApiKey() {
        return userApiKey;
    }

    public void setUserApiKey(String userApiKey) {
        this.userApiKey = userApiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
