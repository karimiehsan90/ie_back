package ir.asta.training.auth.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import ir.asta.training.auth.entities.UserEntity;
import ir.asta.training.auth.fixed.Role;
import ir.asta.training.auth.fixed.UserMongo;
import ir.asta.wise.core.response.UserResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("authDao")
public class AuthDao {
    @Autowired
    private MongoClient client;

    @PersistenceContext
    private EntityManager manager;

    public boolean containsUser(String email){
        MongoDatabase database = client.getDatabase("ticketing");
        MongoCollection<Document> users = database.getCollection("users");
        return users.find(Filters.eq(UserMongo.email, email)).iterator().hasNext();
    }

    public UserResponse authenticate(String email, String hashedPassword){
        MongoDatabase database = client.getDatabase("ticketing");
        MongoCollection<Document> users = database.getCollection("users");
        FindIterable<Document> documents = users.find(Filters.and(Filters.eq(UserMongo.email, email), Filters.eq(UserMongo.password, hashedPassword),
                Filters.eq(UserMongo.isAccept, true), Filters.eq(UserMongo.isActive, true)));
        if (documents.iterator().hasNext()){
            Document next = documents.iterator().next();
            return convertDocumentToUserResponse(next);
        }
        return null;
    }

    public UserResponse authenticate(String token){
        ObjectId id = new ObjectId(token);
        MongoDatabase database = client.getDatabase("ticketing");
        MongoCollection<Document> users = database.getCollection("users");
        FindIterable<Document> documents = users.find(Filters.and(Filters.eq(UserMongo.objectId, id), Filters.eq(UserMongo.isActive, true),
                Filters.eq(UserMongo.isAccept, true)));
        if (documents.iterator().hasNext()){
            Document next = documents.iterator().next();
            return convertDocumentToUserResponse(next);
        }
        return null;
    }

    private UserResponse convertDocumentToUserResponse(Document next){
        UserResponse response = new UserResponse();
        response.setRole(next.getString(UserMongo.role));
        String name = next.getString(UserMongo.firstName);
        String lastName = next.getString(UserMongo.lastName);
        if (lastName != null){
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
    ){
        MongoDatabase database = client.getDatabase("ticketing");
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
}
