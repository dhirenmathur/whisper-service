package com.whisper.server.service;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whisper.server.constants.ActionMessage;
import com.whisper.server.constants.ActionType;
import com.whisper.server.constants.Person;
import config.WhisperConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Import(WhisperConfig.class)
public class SocketTextHandler extends TextWebSocketHandler {

    private CallInitiatorService callInitiatorService = new CallInitiatorService();

    HashMap<String, WebSocketSession> usernameToSession = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        String payload = message.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(payload);
        ActionMessage msg =  mapper.treeToValue(jsonNode,ActionMessage.class);

        switch (msg.getActionType()){
            case connect:
                if(!usernameToSession.containsKey(msg.getInitiator())){
                    usernameToSession.put(msg.getInitiator(),session);
                } else {
                    usernameToSession.replace(msg.getInitiator(),session);
                    System.out.println(usernameToSession.toString());
                }
                break;
            case disconnect:
                usernameToSession.remove(msg.getInitiator());
                System.out.println(usernameToSession.toString());
                break;
            case start:
                callInitiatorService.routeToInvitee(msg,usernameToSession);
                break;
            case whisper:
                callInitiatorService.routeToInvitee(msg,usernameToSession);
                break;
            case accept:
                //todo: update invitee list after persisting room details
                break;
            case decline:
                //todo: update invitee list after persisting room details
                break;
        }

    }

}
