package qcodemx.com.chatt.model;

import java.util.List;

import qcodemx.com.chatt.data.api.CTResponse;

/**
 * Created by Eric Vargas on 8/18/14.
 *
 * Stores the public and private events.
 */
public class EventManager extends CTResponse {

    private List<Event> pub;
    private List<Event> priv;

    public EventManager(boolean success, String message, List<Event> pub, List<Event> priv) {
        super(success, message);
        this.pub = pub;
        this.priv = priv;
    }

    public List<Event> getPub() {
        return pub;
    }

    public List<Event> getPriv() {
        return priv;
    }
}
