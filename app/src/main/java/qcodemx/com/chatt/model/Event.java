package qcodemx.com.chatt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import qcodemx.com.chatt.data.api.CTResponse;

/**
 * Created by Eric Vargas on 8/17/14.
 *
 * Data structure for an event.
 */
public class Event implements Serializable {
    private static final long serialVersionUID = 0L;

    private String id;
    private String title;
    private EventUser author;
    private String date;
    private boolean pub;
    private List<EventUser> users;
    private List<ChatMessage> chat;

    public Event(String id, String title, EventUser author, String date, boolean pub) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.date = date;
        this.pub = pub;
        this.users = new ArrayList<>();
        this.chat = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public boolean isPublic() {
        return pub;
    }

    public void setPublic(boolean pub) {
        this.pub = pub;
    }

    public List<EventUser> getUsers() {
        return users;
    }

    public void setUsers(List<EventUser> users) {
        this.users = users;
    }

    public List<ChatMessage> getChat() {
        return chat;
    }

    public void setChat(List<ChatMessage> chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "Event{" +
                "_id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", date='" + date + '\'' +
                ", pub=" + pub +
                ", users=" + users +
                ", chat=" + chat +
                '}';
    }
}
