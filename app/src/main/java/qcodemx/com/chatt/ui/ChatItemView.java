package qcodemx.com.chatt.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.R;
import qcodemx.com.chatt.model.ChatMessage;

/**
 * Created by Eric Vargas on 8/19/14.
 *
 * View representing a chat message.
 */
public class ChatItemView extends LinearLayout {

    @InjectView(R.id.text_date) TextView dateTextView;
    @InjectView(R.id.text_msg) TextView messageTextView;
    @InjectView(R.id.text_author) TextView authorTextView;

    public ChatItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public void bindTo(ChatMessage chatMessage) {
        dateTextView.setText(chatMessage.getDate());
        messageTextView.setText(chatMessage.getMessage());
        authorTextView.setText(chatMessage.getUser());
    }
}
