package viewControllers.interfaces;

import org.json.JSONObject;
import utils.WebService.socketio.SocketEvent;

/**
 * Created by Andres on 12/8/16.
 */
public interface AuthListener {
    void onEvent(AuthEvent event);
    void registerForAuthEvents();
}
