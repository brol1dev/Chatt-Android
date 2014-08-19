package qcodemx.com.chatt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.data.PreferencesManager;
import qcodemx.com.chatt.data.api.EventService;
import qcodemx.com.chatt.data.api.UserToken;
import qcodemx.com.chatt.model.Event;
import qcodemx.com.chatt.model.EventManager;
import qcodemx.com.chatt.ui.widget.EventAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.view.View.OnClickListener;

/**
 * Created by Eric Vargas on 8/9/14.
 *
 * Fragment to list all the chats.
 */
public class EventListFragment extends CTFragment {
    private static final String LOG_TAG = "EventListFragment";

    private static final int PRIVATE_TAB = 0;
    private static final int PUBLIC_TAB = 1;

    @InjectView(R.id.list_event) ListView eventsList;
    @InjectView(R.id.btn_new_event) Button newEventButton;
    @InjectView(R.id.btn_tab_private) Button privateButton;
    @InjectView(R.id.btn_tab_public) Button publicButton;
    @InjectView(R.id.new_event_box) LinearLayout eventBox;
    @InjectView(R.id.empty_box) LinearLayout emptyBox;

    @Inject EventService eventService;
    @Inject PreferencesManager preferencesManager;

    // TODO: MainActivity should pass the userToken as a dependency
    UserToken userToken;
    EventManager eventManager;

    private EventAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list, container, false);
        ButterKnife.inject(this, view);

        adapter = new EventAdapter(getActivity());
        eventsList.setAdapter(adapter);
        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), EventChatActivity.class);
                intent.putExtra(EventChatActivity.EXTRA_EVENT, event);
                startActivity(intent);
            }
        });

        privateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEvents(PRIVATE_TAB);
            }
        });

        publicButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEvents(PUBLIC_TAB);
            }
        });

        newEventButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewEventActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userToken = preferencesManager.retrieveCurrentUser();
    }

    @Override
    public void onResume() {
        super.onResume();

        loadEvents();
    }

    private void loadEvents() {
        eventService.getEvents("Bearer " + userToken.getToken(),
            userToken.getUser().getId(), new Callback<EventManager>() {
                @Override
                public void success(EventManager eventManager, Response response) {
                    EventListFragment.this.eventManager = eventManager;
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w(LOG_TAG, "failure: " + error.getMessage());
                }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void updateEvents(int tabId) {
        switch (tabId) {
            case PRIVATE_TAB:
                publicButton.setEnabled(true);
                privateButton.setEnabled(false);
                updateUI(eventManager.getPriv());
                break;
            case PUBLIC_TAB:
                publicButton.setEnabled(false);
                privateButton.setEnabled(true);
                updateUI(eventManager.getPub());
                break;
        }

    }

    private void updateUI(List<Event> events) {
        if (events.size() > 0) {
            emptyBox.setVisibility(View.GONE);
        } else {
            emptyBox.setVisibility(View.VISIBLE);
        }

        adapter.update(events);
    }
}
