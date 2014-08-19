package qcodemx.com.chatt.data.api;

/**
 * Created by Eric Vargas on 8/6/14.
 *
 * Represents authorization token to make secured API calls.
 */
public class UserToken extends CTResponse {
    protected String token;
    protected String registrationId; // Registration id for GCM
    protected User user;

    public UserToken(String token, String registrationId, User user) {
        super(true, null);
        this.token = token;
        this.registrationId = registrationId;
        this.user = user;
    }

    public UserToken(boolean success, String message, String token, User user) {
        super(success, message);
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public String toString() {
        return "User: {id: " + user.getId() + ", email: " + user.getEmail()
                + ", token: " + token + "}";
    }
}
