package qcodemx.com.chatt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.data.PreferencesManager;
import qcodemx.com.chatt.data.api.CTRestClient;
import qcodemx.com.chatt.model.ChatMessage;
import qcodemx.com.chatt.model.Event;
import qcodemx.com.chatt.model.EventParser;
import qcodemx.com.chatt.model.User;
import qcodemx.com.chatt.ui.InviteDialogFragment;
import qcodemx.com.chatt.ui.util.UIUtils;
import qcodemx.com.chatt.ui.widget.ChatAdapter;

import static android.view.View.OnClickListener;
import static qcodemx.com.chatt.ui.InviteDialogFragment.ChatDialogListener;


/**
 * Created by Eric Vargas on 8/19/14.
 *
 * Activity that shows the chat for a given Event.
 *
 */
public class EventChatActivity extends CTActivity implements ChatDialogListener {
    private static final String LOG_TAG = "EventChatActivity";

    public static final String EXTRA_EVENT = "event";

    @InjectView(R.id.chat_list) ListView chatListView;
    @InjectView(R.id.text_compose_msg) EditText composeEditText;
    @InjectView(R.id.btn_send) Button sendButton;

    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm");

//    @Inject EventService eventService;
    @Inject CTRestClient restClient;
    @Inject PreferencesManager preferencesManager;

    // TODO: MainActivity should pass the userToken as a dependency
    User user;

    Event event;

    ChatAdapter adapter;
    ChatThread chatThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_chat);
        ButterKnife.inject(this);

        ChatBroadcastReceiver receiver = new ChatBroadcastReceiver();
        IntentFilter receiverFilter = new IntentFilter();
        receiverFilter.addAction("com.google.android.c2dm.intent.RECEIVE");
        receiverFilter.addCategory("qcodemx.com.chatt");
        registerReceiver(receiver, receiverFilter, "com.google.android.c2dm.permission.SEND", null);

        event = (Event) getIntent().getSerializableExtra(EXTRA_EVENT);
        user = preferencesManager.retrieveCurrentUser();

        adapter = new ChatAdapter(this, user);
        chatListView.setAdapter(adapter);

        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = composeEditText.getText().toString();

                if (msg.trim().isEmpty()) return;

                //TODO: Send message to server
                ChatMessage chatMessage = new ChatMessage(user.getEmail(),
                        msg, dateFormat.format(new Date()));


                try {
                    JSONObject params = new JSONObject();
                    params.put("from", user.getId());
                    params.put("msg", msg);
                    restClient.post("/api/event/" + event.getId() + "/chat", user, params, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d(LOG_TAG, "posted a new message");
                            composeEditText.setText("");
                            loadChat();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            try {
                                Log.w(LOG_TAG, "failure: " + errorResponse.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

//                eventService.sendMessage("Bearer " + user.getToken(), event.getId(),
//                        chatMessage, new Callback<Event>() {
//                            @Override
//                            public void success(Event event, Response response) {
//                                Log.d(LOG_TAG, "success: " + response.getStatus());
//                                composeEditText.setText("");
//                            }
//
//                            @Override
//                            public void failure(RetrofitError error) {
//                                Log.w(LOG_TAG, "failure: " + error.getMessage());
//                            }
//                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        chatThread = new ChatThread();
//        chatThread.start();
        loadChat();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        chatThread.stopThread();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!event.isPublic()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.chat, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_invite) {
            openInviteDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadChat() {

        restClient.get("/api/event/" + event.getId() + "/chat", user, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(LOG_TAG, "chat messages: " + response);
                try {
                    event.setChat(new ArrayList<ChatMessage>());
                    EventParser.parseJSONChat(response, event.getChat());
                    adapter.update(event.getChat());
                    chatListView.setSelection(adapter.getCount() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.w(LOG_TAG, "failure: " + errorResponse.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        eventService.getChat("Bearer " + user.getToken(), event.getId(),
//                new Callback<Chat>() {
//                    @Override
//                    public void success(Chat chat, Response response) {
//                        Log.d(LOG_TAG, "chat msgs: " + chat.getChat().size());
//                        adapter.update(chat.getChat());
//                        chatListView.setSelection(adapter.getCount() - 1);
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Log.w(LOG_TAG, "failure: " + error.getMessage());
//                    }
//                });
    }

    private void openInviteDialog() {
        InviteDialogFragment dialogFragment = new InviteDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "InviteDialogFragment");
    }

    /***** ChatDialogListener methods *****/

    @Override
    public void onInviteUser(String username) {
        if (username.isEmpty()) return;

        try {
            JSONObject params = new JSONObject();
            params.put("username", username);
            restClient.put("/api/event/" + event.getId(), user, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d(LOG_TAG, "added a user: " + response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        Log.w(LOG_TAG, "failed adding user: " + errorResponse.getString("message"));
                        UIUtils.createToast(EventChatActivity.this, errorResponse.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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

    public class ChatBroadcastReceiver extends BroadcastReceiver {

        public ChatBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Received notification");
            loadChat();
        }
    }
}
