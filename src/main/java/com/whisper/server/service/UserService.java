package com.whisper.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.whisper.server.constants.Person;
import netscape.javascript.JSObject;
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

    public Person getUser(String uuid) throws InterruptedException, ExecutionException, JsonProcessingException {
        DocumentReference docRef = getUsersCollection().document(uuid);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();

        if(document.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(document.getData());
            JsonNode jsonNode = mapper.readTree(json);
            return  mapper.treeToValue(jsonNode,Person.class);
        }else {
            return null;
        }
    }

    public boolean createUser(Person user) {
       getUsersCollection().document(user.getUuid()).set(user);
       return true;
    }
}
