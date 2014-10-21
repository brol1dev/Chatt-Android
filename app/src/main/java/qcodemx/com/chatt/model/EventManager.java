package qcodemx.com.chatt.model;

import java.util.Collections;
import java.util.List;

import qcodemx.com.chatt.data.api.CTResponse;

/**
 * Created by Eric Vargas on 8/18/14.
 *
 * Stores the public and private events.
 */
public class EventManager {

    private List<Event> publicEvents = Collections.emptyList();
    private List<Event> privateEvents = Collections.emptyList();

    public EventManager() {
    }

    public EventManager(List<Event> publicEvents, List<Event> privateEvents) {
        this.publicEvents = publicEvents;
        this.privateEvents = privateEvents;
    }

    public List<Event> getPublicEvents() {
        return publicEvents;
    }

    public void setPublicEvents(List<Event> publicEvents) {
        this.publicEvents = publicEvents;
    }

    public List<Event> getPrivateEvents() {
        return privateEvents;
    }

    public void setPrivateEvents(List<Event> privateEvents) {
        this.privateEvents = privateEvents;
    }
}
