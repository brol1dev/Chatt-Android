package qcodemx.com.chatt.model;

import java.io.Serializable;
import java.util.List;

import qcodemx.com.chatt.data.api.CTResponse;

/**
 * Created by Eric Vargas on 8/17/14.
 *
 * Data structure for an event.
 */
public class Event extends CTResponse {

    private String _id;
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

    public Event(boolean success, String message, String _id, String title, EventUser author,
                 String date, boolean pub, List<EventUser> users, List<ChatMessage> chat) {
        super(success, message);
        this._id = _id;
        this.title = title;
        this.author = author;
        this.date = date;
        this.pub = pub;
        this.users = users;
        this.chat = chat;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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

    @Override
    public String toString() {
        return "Event{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", date='" + date + '\'' +
                ", pub=" + pub +
                ", users=" + users +
                ", chat=" + chat +
                '}';
    }
}
