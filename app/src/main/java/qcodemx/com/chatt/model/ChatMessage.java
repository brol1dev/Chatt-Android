package qcodemx.com.chatt.model;

import java.io.Serializable;

/**
 * Created by Eric Vargas on 8/17/14.
 *
 * Data structure for a chat message.
 */
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 0L;

    private String user;
    private String message;
    private String date;

    public ChatMessage(String user, String message, String date) {
        this.user = user;
        this.message = message;
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
