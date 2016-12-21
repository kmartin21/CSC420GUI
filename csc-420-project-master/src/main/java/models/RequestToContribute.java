package models;

import org.json.JSONObject;
import utils.PublicationsService;
import views.subviews.PublicationContributeButtonCellRenderer;
import views.subviews.RequestStatusButtonCellRenderer;

import java.util.HashMap;

/**
 * Created by lwdthe1 on 12/7/16.
 */
public class RequestToContribute {
    private String publicationId;
    private Boolean accepted = false, rejected = false, retracted = false;

    private HashMap<String, Object> virtuals = new HashMap<>();

    public RequestToContribute(JSONObject obj) {
        this.publicationId = obj.getString("publicationId");
        this.accepted = obj.has("status") && obj.getBoolean("status");
        this.rejected = obj.has("status") && !obj.getBoolean("status");
        this.retracted = obj.has("retracted") && obj.getBoolean("retracted");
    }

    public RequestToContribute(String publicationId) {
        this.publicationId = publicationId;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public Boolean getRetracted() {
        return retracted;
    }

    public Boolean wasAccepted() {
        return accepted && !rejected;
    }

    public Boolean wasRejected() {
        return rejected;
    }

    public Publication getPublication() {
        return PublicationsService.sharedInstance.getById(publicationId);
    }

    public void setFeedTableCell(RequestStatusButtonCellRenderer homeFeedTableCell) {
        this.virtuals.put("feedTableCell", homeFeedTableCell);
    }

    public RequestStatusButtonCellRenderer getFeedTableCell() {
        Object value = this.virtuals.get("feedTableCell");
        return value instanceof RequestStatusButtonCellRenderer?
                (RequestStatusButtonCellRenderer) value : null;
    }

    public boolean isPending() {
        return !wasAccepted() && !wasRejected();
    }

    public void updateAccepted(boolean accepted) {
        this.accepted = accepted;
        this.rejected = !accepted;
    }
}
