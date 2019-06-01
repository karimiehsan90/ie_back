package ir.asta.wise.core.response;

import ir.asta.training.auth.entities.UserEntity;
import ir.asta.wise.core.enums.Importance;
import ir.asta.wise.core.enums.Status;

import java.util.Calendar;
import java.util.Date;

public class CaseResponse {
    private String title;
    private String body;
    private UserEntity to;
    private UserEntity from;
    private String file;
    private Importance importance;
    private Status status;
    private String createdDate;
    private String lastUpdate;



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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        JalaliCalendar calendar =new JalaliCalendar();
        calendar.setTime(createdDate);
        String date = "";
        date+=calendar.get(calendar.YEAR);
        date+=" / ";
        date+=calendar.get(calendar.MONTH);
        date+=" / ";
        date+=calendar.get(calendar.DATE);
        this.createdDate = date;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        JalaliCalendar calendar =new JalaliCalendar();
        calendar.setTime(lastUpdate);
        String date = "";
        date+=calendar.get(Calendar.YEAR);
        date+=" / ";
        date+=calendar.get(Calendar.MONTH);
        date+=" / ";
        date+=calendar.get(Calendar.DATE);
        this.lastUpdate = date;
    }
}
