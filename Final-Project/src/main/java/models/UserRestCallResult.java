package models;

/**
 * Created by lwdthe1 on 12/6/16.
 */
public class UserRestCallResult {
    private Boolean success = false;
    private String errorMessage = "";
    private User user = null;

    public UserRestCallResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public UserRestCallResult(User user) {
        this.user = user;
        this.success = true;
    }

    public User getUser() {
        return user;
    }

    public Boolean getSuccess() {
        return success && errorMessage.isEmpty();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
