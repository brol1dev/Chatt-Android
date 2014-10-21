package qcodemx.com.chatt.model;

import java.io.Serializable;

/**
 * Created by Eric Vargas on 8/18/14.
 *
 * Data structure for user entities for an event.
 */
public class EventUser implements Serializable {
    private static final long serialVersionUID = 0L;

    private String id;
    private String username;
    private String email;

    public EventUser(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
