package ir.asta.training.cases.entities;

import ir.asta.training.auth.entities.UserEntity;
import ir.asta.wise.core.enums.Importance;
import ir.asta.wise.core.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cases")
public class CaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_update")
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name = "from_id")
    public UserEntity from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    public UserEntity to;

    @Column(name = "importance")
    private Importance importance;

    @Column(name = "status")
    private Status status;

    @Column(name = "file")
    private String file;

    public CaseEntity() {
    }

    public CaseEntity(String title, String body, Date createdDate, Date lastUpdate, UserEntity from, UserEntity to, Importance importance, Status status, String file) {
        this.title = title;
        this.body = body;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
        this.from = from;
        this.to = to;
        this.importance = importance;
        this.status = status;
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
