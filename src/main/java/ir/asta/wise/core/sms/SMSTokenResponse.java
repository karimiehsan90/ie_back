package ir.asta.wise.core.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMSTokenResponse {
    @JsonProperty("IsSuccessful")
    private boolean success;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("TokenKey")
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
