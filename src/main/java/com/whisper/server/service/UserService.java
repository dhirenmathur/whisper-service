package com.whisper.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.whisper.server.constants.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    @Autowired
    private Firestore firestore;

    private CollectionReference getUsersCollection(){
        return firestore.collection("Users");
    }

    public Object getUser(String username) throws InterruptedException, ExecutionException, JsonProcessingException {
        DocumentReference docRef = getUsersCollection().document(username);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();
        Person user = null;

        if(document.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(document.getData());
            return json;
        }else {
            return null;
        }
    }

    public String createUser(Person user) throws InterruptedException, ExecutionException, JsonProcessingException{

        ApiFuture<WriteResult> collectionsApiFuture = getUsersCollection().document(user.getUsername()).set(user);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
}
