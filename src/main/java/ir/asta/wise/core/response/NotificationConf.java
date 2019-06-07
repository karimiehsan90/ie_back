package ir.asta.wise.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationConf {
    @JsonProperty("email")
    private String email;

    @JsonProperty("email_password")
    private String emailPassword;

    @JsonProperty("sms_number")
    private String smsNumber;

    @JsonProperty("sms_secret")
    private String smsSecret;

    @JsonProperty("sms_key")
    private String smsKey;

    @JsonProperty("default_number")
    private String defaultNumber;

    @JsonProperty("default_text")
    private String defaultText;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }

    public String getSmsSecret() {
        return smsSecret;
    }

    public void setSmsSecret(String smsSecret) {
        this.smsSecret = smsSecret;
    }

    public String getSmsKey() {
        return smsKey;
    }

    public void setSmsKey(String smsKey) {
        this.smsKey = smsKey;
    }

    public String getDefaultNumber() {
        return defaultNumber;
    }

    public void setDefaultNumber(String defaultNumber) {
        this.defaultNumber = defaultNumber;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }
}
