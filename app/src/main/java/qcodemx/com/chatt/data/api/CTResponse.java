package qcodemx.com.chatt.data.api;

import java.io.Serializable;

/**
 * Created by Eric Vargas on 8/6/14.
 *
 * General response from the server API.
 */
public class CTResponse implements Serializable {
    private static final long serialVersionUID = 0L;

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
