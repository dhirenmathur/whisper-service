package com.whisper.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whisper.server.constants.ActionMessage;
import com.whisper.server.constants.Person;
import com.whisper.server.constants.UserStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class ActionService {

    @Autowired
    private UserService userService;

    HashMap<String, WebSocketSession> userIdToSessionMap = new HashMap<>();

    ObjectMapper mapper = new ObjectMapper();

    public void routeToInvitee(ActionMessage message) throws IOException {
        List<String> invitees = message.getDestination();
        if (invitees.get(0).equalsIgnoreCase("all")) {
            invitees = new ArrayList<>(userIdToSessionMap.keySet());
        }
        for (String userId : invitees) {
            WebSocketSession session = userIdToSessionMap.get(userId);
            message.setDestination(Collections.singletonList(userId));
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        }
    }

    public void getOnlineList(ActionMessage message) throws IOException, ExecutionException, InterruptedException {
        WebSocketSession sessionId = userIdToSessionMap.get(message.getUser().getUuid());
        Map<String, String> onlineUserIdToUsernameMap = new HashMap<>();
        for (String userId : userIdToSessionMap.keySet()) {
            Person user = userService.getUser(userId);
            onlineUserIdToUsernameMap.put(user.getUuid(), user.getUsername());
        }
        UserStatusMessage userStatusMessage = UserStatusMessage.builder()
                .userList(onlineUserIdToUsernameMap)
                .build();
        sessionId.sendMessage(new TextMessage(mapper.writeValueAsString(userStatusMessage)));
    }

    public void onConnect(ActionMessage message, WebSocketSession session) {
        if (!userIdToSessionMap.containsKey(message.getUser().getUuid())) {
            userIdToSessionMap.put(message.getUser().getUuid(), session);
        } else {
            userIdToSessionMap.replace(message.getUser().getUuid(), session);
        }
        Person newUser = Person.builder().uuid(message.getUser().getUuid()).username(message.getUser().getUsername()).build();
        userService.createUser(newUser);

    }


    public void onAcceptReject(ActionMessage message) throws IOException {
        WebSocketSession sessionId = userIdToSessionMap.get(message.getDestination().get(0));
        sessionId.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
    }

    public void onDisconnect(ActionMessage msg) {
        userIdToSessionMap.remove(msg.getUser().getUuid());
    }

    public void onConnectionClosed(WebSocketSession session) {
        userIdToSessionMap.entrySet().removeIf(entry -> session.equals(entry.getValue()));
    }
}
