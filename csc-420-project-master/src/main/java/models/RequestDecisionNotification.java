package models;

import org.json.JSONObject;

import static java.lang.String.format;

/**
 * Created by lwdthe1 on 12/4/16.
 */
public class RequestDecisionNotification {
    private String userId;
    private String publicationId;
    private Boolean accepted;

    public RequestDecisionNotification(JSONObject payload) {
        this.userId = payload.getString("userId");
        this.publicationId = payload.getString("publicationId");
        this.accepted = payload.getBoolean("decision");
    }

    public String getPublicationId() {
        return publicationId;
    }

    public String getUserId() {
        return userId;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    @Override
    public String toString() {
        return format("[ Request Decision Notification: { userId: %s, publicationId: %s, accepted: %s } ]", userId, publicationId, accepted);
    }
}
