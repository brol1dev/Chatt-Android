package qcodemx.com.chatt.data.api;

/**
 * Created by Eric Vargas on 8/6/14.
 *
 * Represents authorization token to make secured API calls.
 */
public class UserToken extends CTResponse {
    protected String token;
    protected User user;

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
}
