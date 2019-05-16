package ir.asta.wise.core.response;

public class CaseResponse {
    private String title;
    private String body;
    private UserResponse to;
    private UserResponse from;
    private String importance;


    public CaseResponse() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTo(UserResponse to) {
        this.to = to;
    }

    public void setFrom(UserResponse from) {
        this.from = from;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public UserResponse getTo() {
        return to;
    }

    public UserResponse getFrom() {
        return from;
    }

    public String getImportance() {
        return importance;
    }
}
