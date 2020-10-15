package com.whisper.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whisper.server.constants.ActionMessage;
import com.whisper.server.constants.Person;
import com.whisper.server.constants.UserStatusMessage;
import config.WhisperConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.whisper.server.service.UserService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class ActionService {

    @Autowired
    private UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    public void routeToInvitee(ActionMessage message, Map<String, WebSocketSession> userToSessionMap) throws IOException {
        List<String> invitees = message.getDestination();
        for (String user : invitees) {
            //todo: store room details to display roster etc
            WebSocketSession session = userToSessionMap.get(user);
            String inviteMessage = createInvite(message, user);
            session.sendMessage(new TextMessage(inviteMessage));
        }

    }

    private String createInvite(ActionMessage message, String user) throws JsonProcessingException {
        ActionMessage inviteMessage = message;
        inviteMessage.setDestination(Collections.singletonList(user));
        return mapper.writeValueAsString(inviteMessage);
    }

    public void getOnlineList(ActionMessage message, Map<String, WebSocketSession> userToSessionMap) throws IOException, JsonProcessingException, ExecutionException, InterruptedException {
        WebSocketSession sessionId = userToSessionMap.get(message.getUser().getUuid());
        Map<String, String> onlineUserIdToUserameMap = new HashMap<>();
        for (String userId : userToSessionMap.keySet()) {
            Person user = userService.getUser(userId);
            onlineUserIdToUserameMap.put(user.getUuid(), user.getUsername());
        }
        UserStatusMessage userStatusMessage = UserStatusMessage.builder()
                .userList(onlineUserIdToUserameMap)
                .build();
        sessionId.sendMessage(new TextMessage(mapper.writeValueAsString(userStatusMessage)));
    }

    public void onConnect(ActionMessage message) {
        Person newUser = Person.builder().uuid(message.getUser().getUuid()).username(message.getUser().getUsername()).id(message.getUser().getId()).build();
        userService.createUser(newUser);

    }

}
