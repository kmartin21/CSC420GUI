package utils.WebService.socketio;

import io.socket.client.Socket;

/**
 * Created by lwdthe1 on 12/4/16.
 */
public enum SocketEvent {
    CONNECTED(Socket.EVENT_CONNECT),
    DISCONNECTED(Socket.EVENT_DISCONNECT),
    NUM_CLIENTS("numClients"),
    CHAT_MESSAGE("message"),
    NOTIFICATION_REQUEST_TO_CONTRIBUTE_DECISION("notification_requestToContributeDecision");

    private String value;
    SocketEvent(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
