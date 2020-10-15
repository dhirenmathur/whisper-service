package com.whisper.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whisper.server.constants.ActionMessage;
import config.WhisperConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    @Autowired
    private ActionService actionService;

    HashMap<String, WebSocketSession> usernameToSession = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException, ExecutionException {
        String payload = message.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(payload);
        ActionMessage msg = mapper.treeToValue(jsonNode, ActionMessage.class);
        switch (msg.getActionType()) {
            case connect:
                if (!usernameToSession.containsKey(msg.getUser().getUuid())) {
                    usernameToSession.put(msg.getUser().getUuid(), session);
                } else {
                    usernameToSession.replace(msg.getUser().getUuid(), session);
                }
                actionService.onConnect(msg);
                break;
            case disconnect:
                usernameToSession.remove(msg.getUser().getUuid());
                break;
            case start:
            case whisper:
                actionService.routeToInvitee(msg, usernameToSession);
                break;
            case online:
                actionService.getOnlineList(msg, usernameToSession);
                break;
            case accept:
            case decline:
                actionService.onAcceptReject(msg, usernameToSession);
                break;
        }



    }

}
