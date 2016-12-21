package models;

/**
 * Created by lwdthe1 on 12/6/16.
 */
public class UserSettingsRestCallResult {
    public static final String new_message_notification_instant = "new_message_notification_instant";
    public static final String contribution_request_decision = "contribution_request_decision";
    private Boolean success = false;
    private Boolean instantMessage = false;
    private Boolean statusRequest = false;

    public UserSettingsRestCallResult(){}

    public UserSettingsRestCallResult(Boolean instantMessage, Boolean statusRequest) {
        this.success = true;
        this.instantMessage = instantMessage;
        this.statusRequest = statusRequest;
    }

    public Boolean getSuccess() {
        return success;
    }
    public Boolean getInstantMessage() {
        return instantMessage;
    }
    public Boolean getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(Boolean statusRequest) {
        this.statusRequest = statusRequest;
    }

    public void setInstantMessage(Boolean instantMessage) {
        this.instantMessage = instantMessage;
    }
}
