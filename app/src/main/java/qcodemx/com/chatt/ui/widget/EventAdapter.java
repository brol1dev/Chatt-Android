package qcodemx.com.chatt.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import qcodemx.com.chatt.R;
import qcodemx.com.chatt.model.Event;
import qcodemx.com.chatt.ui.EventListView;

/**
 * Created by Eric Vargas on 8/17/14.
 *
 * Adapter for events.
 */
public class EventAdapter extends BindableAdapter<Event> {

    private List<Event> events = Collections.emptyList();

    public EventAdapter(Context context) {
        super(context);
    }

    public void update(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public Event getItem(int position) {
        return events.get(position);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(R.layout.event_item, container, false);
    }

    @Override
    public void bindView(Event item, int position, View view) {
        ((EventListView) view).bindTo(item);
    }
}
