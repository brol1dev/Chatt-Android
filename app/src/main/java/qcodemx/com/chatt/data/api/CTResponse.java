package qcodemx.com.chatt.data.api;

/**
 * Created by Eric Vargas on 8/6/14.
 *
 * General response from the server API.
 */
public class CTResponse {
    protected boolean success;
    protected String message;

    public CTResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccessful() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
