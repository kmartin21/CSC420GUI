package utils.WebService.socketio;

import org.json.JSONObject;

/**
 * Created by lwdthe1 on 12/4/16.
 */
public interface SocketListener {
    void onEvent(SocketEvent event, JSONObject obj);
    void registerForEvents();
}
