package qcodemx.com.chatt.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Eric Vargas on 10/20/14.
 *
 * Transform Json docs to events.
 */
public class EventParser {

    private static final String PUBLIC = "pub";
    private static final String PRIVATE = "priv";
    private static final String EVT_AUTHOR = "author";
    private static final String AUTHOR_ID = "user_id";
    private static final String AUTHOR_USERNAME = "username";
    private static final String AUTHOR_EMAIL = "email";
    private static final String EVT_TITLE = "title";
    private static final String EVT_ID = "_id";
    private static final String EVT_USERS = "users";
    private static final String EVT_CHAT = "chat";
    private static final String EVT_DATE = "date";
    private static final String EVT_PUBLIC = "pub";

    private static final String CHAT = "chat";
    private static final String CHAT_FROM = "from";
    private static final String CHAT_MSG = "msg";

    public static void parseJSONEvents(JSONObject json, EventManager eventManager) throws JSONException {

        parseJSONEventList(eventManager.getPublicEvents(), json.getJSONArray(PUBLIC));
        parseJSONEventList(eventManager.getPrivateEvents(), json.getJSONArray(PRIVATE));
    }

    public static void parseJSONChat(JSONObject json, List<ChatMessage> chat) throws JSONException {
        JSONArray jsonChat = json.getJSONArray(CHAT);
        for (int i = 0; i < jsonChat.length(); i++) {
            JSONObject jsonMsg = jsonChat.getJSONObject(i);
            String userId = jsonMsg.getString(CHAT_FROM);
            String msg = jsonMsg.getString(CHAT_MSG);

            chat.add(new ChatMessage(userId, msg));
        }
    }

    private static void parseJSONEventList(List<Event> events, JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonEvent = jsonArray.getJSONObject(i);
            String title = jsonEvent.getString(EVT_TITLE);
            String eventId = jsonEvent.getString(EVT_ID);
            String date = jsonEvent.getString(EVT_DATE);
            boolean publicEvent = jsonEvent.getBoolean(EVT_PUBLIC);

            JSONObject jsonAuthor = jsonEvent.getJSONObject(EVT_AUTHOR);
            String authorId = jsonAuthor.getString(AUTHOR_ID);
            String authorName = jsonAuthor.getString(AUTHOR_USERNAME);
            String authorEmail = jsonAuthor.getString(AUTHOR_EMAIL);

            EventUser author = new EventUser(authorId, authorName, authorEmail);
            Event event = new Event(eventId, title, author, date, publicEvent);
            events.add(event);
        }
    }
}
