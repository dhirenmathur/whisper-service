package com.whisper.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whisper.server.constants.ActionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    @Autowired
    private ActionService actionService;


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException, ExecutionException {
        String payload = message.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(payload);
        ActionMessage actionMessage = mapper.treeToValue(jsonNode, ActionMessage.class);
        switch (actionMessage.getActionType()) {
            case connect:
                actionService.onConnect(actionMessage, session);
                break;
            case disconnect:
                actionService.onDisconnect(actionMessage);
                break;
            case start:
            case whisper:
                actionService.routeToInvitee(actionMessage);
                break;
            case online:
                actionService.getOnlineList(actionMessage);
                break;
            case accept:
            case reject:
                actionService.onAcceptReject(actionMessage);
                break;
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        actionService.onConnectionClosed(session);
    }
}
