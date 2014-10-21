package qcodemx.com.chatt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.data.PreferencesManager;
import qcodemx.com.chatt.data.api.CTRestClient;
import qcodemx.com.chatt.data.api.EventService;
import qcodemx.com.chatt.model.Event;
import qcodemx.com.chatt.model.EventUser;
import qcodemx.com.chatt.model.User;
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

//    @Inject EventService eventService;
    @Inject CTRestClient restClient;
    @Inject PreferencesManager preferencesManager;

    // TODO: MainActivity should pass the userToken as a dependency
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);
        ButterKnife.inject(this);

        user = preferencesManager.retrieveCurrentUser();

        createButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean pubEvent = publicToggleButton.isChecked();

                String title = titleEditText.getText().toString();
                if (title.trim().isEmpty()) {
                    Toast.makeText(NewEventActivity.this,
                            "Please put a title.",
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }


                try {
                    JSONObject params = new JSONObject();
                    params.put("title", title);
                    params.put("pub", pubEvent);
                    restClient.post("/api/user/" + user.getId() + "/event", user, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d(LOG_TAG, "created event: " + response);
                            finish();
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

//                eventService.createEvent("Bearer " + user.getToken(), event,
//                        new Callback<Event>() {
//                            @Override
//                            public void success(Event event, Response response) {
//                                finish();
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

}
