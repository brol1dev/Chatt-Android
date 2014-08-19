package qcodemx.com.chatt.model;

import java.util.List;

import qcodemx.com.chatt.data.api.CTResponse;

/**
 * Created by Eric Vargas on 8/19/14.
 *
 * Represents a list of messages.
 */
public class Chat extends CTResponse {
    List<ChatMessage> chat;

    public Chat(boolean success, String message, List<ChatMessage> chat) {
        super(success, message);
        this.chat = chat;
    }

    public List<ChatMessage> getChat() {
        return chat;
    }
}
