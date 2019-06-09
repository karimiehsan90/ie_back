package ir.asta.training.auth.entities;

import ir.asta.wise.core.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "action")
public class ActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @JoinColumn(name = "from_id")
    @ManyToOne
    public UserEntity from;

    @JoinColumn(name = "case_id")
    @ManyToOne
    public CaseEntity caseEntity;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private Status status;

    @Column(name = "file")
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
