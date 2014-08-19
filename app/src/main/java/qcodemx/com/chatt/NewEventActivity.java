package qcodemx.com.chatt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.data.PreferencesManager;
import qcodemx.com.chatt.data.api.EventService;
import qcodemx.com.chatt.data.api.UserToken;
import qcodemx.com.chatt.model.Event;
import qcodemx.com.chatt.model.EventUser;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.view.View.OnClickListener;

/**
 * Created by Eric Vargas on 8/19/14.
 *
 * Activity to create a new event.
 */
public class NewEventActivity extends CTActivity {
    private static final String LOG_TAG = "NewEventActivity";

    @InjectView(R.id.text_title) EditText titleEditText;
    @InjectView(R.id.toggle_public) ToggleButton publicToggleButton;
    @InjectView(R.id.btn_create) ImageButton createButton;

    @Inject EventService eventService;
    @Inject PreferencesManager preferencesManager;

    // TODO: MainActivity should pass the userToken as a dependency
    UserToken userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);
        ButterKnife.inject(this);

        userToken = preferencesManager.retrieveCurrentUser();

        createButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!publicToggleButton.isChecked()) {
                    Toast.makeText(NewEventActivity.this,
                            "In this demo you can only make public events.",
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                String title = titleEditText.getText().toString();
                if (title.trim().isEmpty()) {
                    Toast.makeText(NewEventActivity.this,
                            "Please put a title.",
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                EventUser authorUser = new EventUser(
                        userToken.getUser().getId(), userToken.getUser().getEmail());

                // TODO: Add more elements to the event and allow private chats.
                Event event = new Event(title, authorUser, null, true);
                eventService.createEvent("Bearer " + userToken.getToken(), event,
                        new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.w(LOG_TAG, "failure: " + error.getMessage());
                    }
                });
            }
        });
    }

}
