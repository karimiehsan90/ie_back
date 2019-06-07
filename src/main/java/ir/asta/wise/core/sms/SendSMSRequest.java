package ir.asta.wise.core.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendSMSRequest {
    @JsonProperty("Messages")
    private String[] texts;

    @JsonProperty("MobileNumbers")
    private String[] numbers;

    @JsonProperty("LineNumber")
    private String from;

    @JsonProperty("SendDateTime")
    private String datetime;

    @JsonProperty("CanContinueInCaseOfError")
    private String caseOfError;

    public SendSMSRequest(String text, String numbers, String from) {
        this.texts = new String[]{text};
        this.numbers = new String[]{numbers};
        this.from = from;
    }

    public SendSMSRequest(){}

    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
    }

    public String[] getNumbers() {
        return numbers;
    }

    public void setNumbers(String[] numbers) {
        this.numbers = numbers;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCaseOfError() {
        return caseOfError;
    }

    public void setCaseOfError(String caseOfError) {
        this.caseOfError = caseOfError;
    }
}
