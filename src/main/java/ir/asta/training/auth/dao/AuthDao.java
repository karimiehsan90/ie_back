package ir.asta.training.auth.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import ir.asta.training.auth.entities.UserEntity;
import ir.asta.training.auth.fixed.Role;
import ir.asta.training.auth.fixed.UserMongo;
import ir.asta.wise.core.response.UserResponse;
import ir.asta.wise.core.response.UserResponseOthers;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Named("authDao")
public class AuthDao {

    @Inject
    private MongoDatabase database;

    @PersistenceContext
    private EntityManager manager;

    public boolean containsUser(String email) {
        MongoCollection<Document> users = database.getCollection("users");
        return users.find(Filters.eq(UserMongo.email, email)).iterator().hasNext();
    }

    // manager and teacher check
    public UserEntity containsUserAndValid(long id) {
        Query query = manager.createQuery("select e from UserEntity e where e.id=:id");
        List<UserEntity> list = query.setParameter("id", id).getResultList();
        if (list.size() > 0) {
            UserEntity entity = list.get(0);
            FindIterable<Document> users = database.getCollection("users").find(Filters.and(
                    Filters.eq(UserMongo.objectId, new ObjectId(entity.getMongoId())),
                    Filters.eq(UserMongo.isAccept, true),
                    Filters.eq(UserMongo.isActive, true),
                    Filters.or(
                            Filters.eq(UserMongo.role, Role.manager),
                            Filters.eq(UserMongo.role, Role.teacher)
                    )
            ));
            if (users.iterator().hasNext()) {
                return entity;
            }
        }
        return null;
    }

    public UserEntity getByToken(String token) {
        Query query = manager.createQuery("select e from UserEntity e where e.mongoId=:token")
                .setParameter("token", token);
        List<UserEntity> list = query.getResultList();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public UserResponse authenticate(String email, String hashedPassword) {
        MongoCollection<Document> users = database.getCollection("users");
        FindIterable<Document> documents = users.find(Filters.and(
                Filters.eq(UserMongo.email, email),
                Filters.eq(UserMongo.password, hashedPassword),
                Filters.eq(UserMongo.isAccept, true),
                Filters.eq(UserMongo.isActive, true)));
        if (documents.iterator().hasNext()) {
            Document next = documents.iterator().next();
            return convertDocumentToUserResponse(next);
        }
        return null;
    }

    // get User by token
    public UserResponse authenticate(String token) {
        ObjectId id = new ObjectId(token);
        MongoCollection<Document> users = database.getCollection("users");
        FindIterable<Document> documents = users.find(Filters.and(Filters.eq(UserMongo.objectId, id), Filters.eq(UserMongo.isActive, true),
                Filters.eq(UserMongo.isAccept, true)));
        if (documents.iterator().hasNext()) {
            Document next = documents.iterator().next();
            return convertDocumentToUserResponse(next);
        }
        return null;
    }

    private UserResponse convertDocumentToUserResponse(Document next) {
        UserResponse response = new UserResponse();
        response.setRole(next.getString(UserMongo.role));
        String name = next.getString(UserMongo.firstName);
        String lastName = next.getString(UserMongo.lastName);
        if (lastName != null) {
            name += " " + lastName;
        }
        response.setName(name);
        response.setToken(next.getObjectId(UserMongo.objectId).toHexString());
        return response;
    }

    public UserResponse registerUser(
            String hashPassword,
            String name,
            String email,
            String role
    ) {
        MongoCollection<Document> users = database.getCollection("users");
        Document document = new Document(UserMongo.firstName, name);
        document.append(UserMongo.password, hashPassword).append(UserMongo.email, email);
        document.append(UserMongo.role, role);
        document.append(UserMongo.isActive, true);
        document.append(UserMongo.isAccept, role.equals(Role.student));
        users.insertOne(document);
        String token = document.getObjectId(UserMongo.objectId).toHexString();
        UserResponse response = new UserResponse();
        response.setName(name);
        response.setRole(role);
        response.setToken(token);
        UserEntity entity = new UserEntity(token);
        manager.persist(entity);
        return response;
    }

    public int setAccept(String id) {
        MongoCollection<Document> users = database.getCollection("users");
        UpdateResult result = users.updateMany(
                Filters.eq(UserMongo.objectId, new ObjectId(id)),
                Updates.set(UserMongo.isAccept, true)
        );
        return (int) result.getModifiedCount();
    }

    public int setActive(String id) {
        UpdateResult users = database.getCollection("users").updateOne(
                Filters.eq(UserMongo.objectId, new ObjectId(id)),
                Updates.set(UserMongo.isActive, true)
        );
        return (int) users.getModifiedCount();
    }

    public int setDeactive(String id) {
        UpdateResult users = database.getCollection("users").updateOne(
                Filters.and(
                        Filters.eq(UserMongo.objectId, new ObjectId(id)),
                        Filters.ne(UserMongo.role, Role.manager)
                ),
                Updates.set(UserMongo.isActive, false)
        );

        return (int) users.getModifiedCount();
    }

    public List<UserResponseOthers> getPossibles() {
        FindIterable<Document> users = database.getCollection("users").find(
                Filters.and(Filters.eq(UserMongo.isActive, true),
                        Filters.eq(UserMongo.isAccept, true),
                        Filters.or(
                                Filters.eq(UserMongo.role, Role.teacher),
                                Filters.eq(UserMongo.role, Role.manager)
                        ))
        );
        List<UserResponseOthers> list = new ArrayList<>();
        for (Document user : users) {
            String mongoId = user.getObjectId(UserMongo.objectId).toHexString();
            String name = user.getString(UserMongo.firstName);
            String lastName = user.getString(UserMongo.lastName);
            if (lastName != null) {
                name += " " + lastName;
            }
            List<UserEntity> resultList = manager.createQuery("select e from UserEntity e where e.mongoId=:mongo_id").setParameter("mongo_id", mongoId).getResultList();
            if (resultList.size() > 0) {
                UserEntity entity = resultList.get(0);
                UserResponseOthers response = new UserResponseOthers(entity.getId(), name);
                list.add(response);
            }
        }
        return list;
    }


}
