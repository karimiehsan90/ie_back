package ir.asta.training.auth.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "mongo_id", nullable = false)
    private String mongoId;

    @OneToMany(mappedBy = "from")
    private Set<CaseEntity> sends;

    @OneToMany(mappedBy = "to")
    private Set<CaseEntity> receives;

    @OneToMany(mappedBy = "from")
    private List<ActionEntity> actions;

    public UserEntity(String mongoId) {
        this.mongoId = mongoId;
    }

    public UserEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
}
