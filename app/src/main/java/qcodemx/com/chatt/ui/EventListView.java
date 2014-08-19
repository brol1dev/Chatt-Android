package qcodemx.com.chatt.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.R;
import qcodemx.com.chatt.model.Event;

/**
 * Created by Eric Vargas on 8/17/14.
 *
 * View for an event item in a list.
 */
public class EventListView extends LinearLayout {

    @InjectView(R.id.event_image) ImageView imageView;
    @InjectView(R.id.event_title) TextView titleTextView;
    @InjectView(R.id.event_date) TextView dateTextView;
//    @InjectView(R.id.event_description) TextView descriptionTextView;
//    @InjectView(R.id.event_last_chat) TextView lastMsgTextView;

    public EventListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public void bindTo(Event event) {
        titleTextView.setText(event.getTitle());
        dateTextView.setText(event.getDate());
    }
}
