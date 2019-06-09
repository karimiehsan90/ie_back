package ir.asta.wise.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.asta.training.auth.entities.UserEntity;
import ir.asta.wise.core.enums.Importance;
import ir.asta.wise.core.enums.Status;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CaseResponse {
    private String id;
    private String title;
    private String body;
    private String to;
    private String from;
    private String file;
    private Importance importance;
    private Status status;
    private Boolean happy;
    @JsonProperty("created_date")
    private String createdDate;
    @JsonProperty("last_answer")
    private String lastUpdate;
    private List<ActionResponse> actions;
    @JsonProperty("answer_count")
    private int answerCount;



    public CaseResponse() {
    }

    public Boolean getHappy() {
        return happy;
    }

    public List<ActionResponse> getActions() {
        return actions;
    }

    public void setActions(List<ActionResponse> actions) {
        this.actions = actions;
        setAnswerCount(actions.size());
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Boolean isHappy() { return happy; }

    public void setHappy(Boolean happy) { this.happy = happy; }

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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
