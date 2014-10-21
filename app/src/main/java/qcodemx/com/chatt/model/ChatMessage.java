package qcodemx.com.chatt.model;

import java.io.Serializable;

/**
 * Created by Eric Vargas on 8/17/14.
 *
 * Data structure for a chat message.
 */
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 0L;

    private String userId;
    private String message;
    private String date;

    public ChatMessage(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public ChatMessage(String userId, String message, String date) {
        this.userId = userId;
        this.message = message;
        this.date = date;
    }

    public String getUserId() {
        return userId;
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
                "userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
