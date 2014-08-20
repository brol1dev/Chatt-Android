package qcodemx.com.chatt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.data.PreferencesManager;
import qcodemx.com.chatt.data.api.EventService;
import qcodemx.com.chatt.data.api.UserToken;
import qcodemx.com.chatt.model.Chat;
import qcodemx.com.chatt.model.ChatMessage;
import qcodemx.com.chatt.model.Event;
import qcodemx.com.chatt.ui.widget.ChatAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.view.View.OnClickListener;

/**
 * Created by Eric Vargas on 8/19/14.
 *
 * Activity that shows the chat for a given Event.
 *
 */
public class EventChatActivity extends CTActivity {
    private static final String LOG_TAG = "EventChatActivity";

    public static final String EXTRA_EVENT = "event";

    @InjectView(R.id.chat_list) ListView chatListView;
    @InjectView(R.id.text_compose_msg) EditText composeEditText;
    @InjectView(R.id.btn_send) Button sendButton;

    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @Inject EventService eventService;
    @Inject PreferencesManager preferencesManager;

    // TODO: MainActivity should pass the userToken as a dependency
    UserToken userToken;

    Event event;

    ChatAdapter adapter;
    ChatThread chatThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_chat);
        ButterKnife.inject(this);

        event = (Event) getIntent().getSerializableExtra(EXTRA_EVENT);
        userToken = preferencesManager.retrieveCurrentUser();

        adapter = new ChatAdapter(this, userToken.getUser());
        chatListView.setAdapter(adapter);

        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = composeEditText.getText().toString();

                if (msg.trim().isEmpty()) return;

                //TODO: Send message to server
                ChatMessage chatMessage = new ChatMessage(userToken.getUser().getEmail(),
                        msg, dateFormat.format(new Date()));

                eventService.sendMessage("Bearer " + userToken.getToken(), event.getId(),
                        chatMessage, new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        Log.d(LOG_TAG, "success: " + response.getStatus());
                        composeEditText.setText("");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.w(LOG_TAG, "failure: " + error.getMessage());
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        chatThread = new ChatThread();
        chatThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatThread.stopThread();
    }

    private void loadChat() {
        eventService.getChat("Bearer " + userToken.getToken(), event.getId(),
                new Callback<Chat>() {
            @Override
            public void success(Chat chat, Response response) {
                Log.d(LOG_TAG, "chat msgs: " + chat.getChat().size());
                adapter.update(chat.getChat());
                chatListView.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "failure: " + error.getMessage());
            }
        });
    }

    private class ChatThread extends Thread {

        private boolean run = true;

        private static final int POLL_TIME = 3 * 1000;

        @Override
        public void run() {
            while (run) {
                loadChat();
                try {
                    sleep(POLL_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopThread() {
            run = false;
        }
    }
}
