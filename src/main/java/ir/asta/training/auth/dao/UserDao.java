package ir.asta.training.auth.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import ir.asta.training.auth.fixed.UserMongo;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Named;

@Named("userDao")
public class UserDao {
    @Inject
    private MongoDatabase database ;


    public boolean updatePro(String name , String password , String email , String phone , String token){
        ObjectId id = new ObjectId(token);
        MongoCollection<Document> users = database.getCollection("users");
        FindIterable<Document> documents = users.find(Filters.eq(UserMongo.objectId, id));
        users.updateOne(Filters.eq(UserMongo.objectId, id) , Updates.combine(   Updates.set(UserMongo.phone,phone),
                                                                                Updates.set(UserMongo.firstName , name),
                                                                                Updates.set(UserMongo.email , email),
                                                                                Updates.set(UserMongo.password , password))
                                                                            );
        return true ;
    }
    public String getPass(String token){
        ObjectId id = new ObjectId(token);
        MongoCollection<Document> users = database.getCollection("users");
        FindIterable<Document> documents = users.find(Filters.eq(UserMongo.objectId, id));
        return documents.iterator().next().getString(UserMongo.password);
    }

    public FindIterable<Document> getAccepteds(){
        MongoCollection<Document> users = database.getCollection("users");
        return users.find(Filters.eq(
                UserMongo.isAccept, true
        ));
    }

    public FindIterable<Document> getNotAccepteds(){
        MongoCollection<Document> users = database.getCollection("users");
        return users.find(Filters.eq(
                UserMongo.isAccept, false
        ));
    }
}
