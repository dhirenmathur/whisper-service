package com.whisper.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whisper.server.constants.ActionMessage;
import com.whisper.server.constants.ActionType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class CallInitiatorService {

    public void routeToInvitee(ActionMessage message, Map<String, WebSocketSession> userToSessionMap) throws IOException {
        List<String> invitees = message.getTarget();
        for (String user : invitees) {
            //todo: store room details to display roster etc
            WebSocketSession session = userToSessionMap.get(user);
            String inviteMessage = createInvite(message, user);
            session.sendMessage(new TextMessage(inviteMessage));
        }

    }

    private String createInvite(ActionMessage message, String user) throws JsonProcessingException {
        ActionMessage inviteMessage =  message;
        inviteMessage.setTarget(Collections.singletonList(user));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(inviteMessage);
    }

}
