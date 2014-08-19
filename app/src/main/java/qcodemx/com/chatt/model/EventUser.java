package qcodemx.com.chatt.model;

/**
 * Created by Eric Vargas on 8/18/14.
 *
 * Data structure for user entities for an event.
 */
public class EventUser {
    private String id;
    private String username;

    public EventUser(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
