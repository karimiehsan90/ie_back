package ir.asta.wise.core.response;

import ir.asta.training.auth.entities.UserEntity;
import ir.asta.training.cases.entities.CaseEntity;
import ir.asta.wise.core.enums.Importance;
import ir.asta.wise.core.enums.Status;

public class CaseResponse {
    private String title;
    private String body;
    private UserEntity to;
    private UserEntity from;
    private String file;
    private Importance importance;
    private Status status;


    public CaseResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserEntity getTo() {
        return to;
    }

    public void setTo(UserEntity to) {
        this.to = to;
    }

    public UserEntity getFrom() {
        return from;
    }

    public void setFrom(UserEntity from) {
        this.from = from;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
