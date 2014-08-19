package qcodemx.com.chatt.model;

import java.util.List;

import qcodemx.com.chatt.data.api.CTResponse;

/**
 * Created by Eric Vargas on 8/17/14.
 *
 * Data structure for an event.
 */
public class Event extends CTResponse {
    private int id;
    private String title;
    private EventUser author;
    private String date;
    private boolean pub;
    private List<EventUser> users;
    private List<ChatMessage> chat;

    public Event(String title, EventUser author, String date, boolean pub) {
        super(true, null);
        this.title = title;
        this.author = author;
        this.date = date;
        this.pub = pub;
    }

    public Event(String title, EventUser author, String date, boolean pub, List<EventUser> users) {
        this(title, author, date, pub);
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public EventUser getAuthor() {
        return author;
    }

    public void setAuthor(EventUser author) {
        this.author = author;
    }

    public boolean isPub() {
        return pub;
    }

    public void setPub(boolean pub) {
        this.pub = pub;
    }

    public List<EventUser> getUsers() {
        return users;
    }

    public List<ChatMessage> getChat() {
        return chat;
    }
}
