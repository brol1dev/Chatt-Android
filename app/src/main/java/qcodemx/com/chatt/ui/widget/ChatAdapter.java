package qcodemx.com.chatt.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import qcodemx.com.chatt.R;
import qcodemx.com.chatt.data.api.User;
import qcodemx.com.chatt.model.ChatMessage;
import qcodemx.com.chatt.ui.ChatItemView;

/**
 * Created by Eric Vargas on 8/19/14.
 *
 * Adapter to store the data for the chat messages.
 */
public class ChatAdapter extends BindableAdapter<ChatMessage> {

    private List<ChatMessage> messages = Collections.emptyList();

    private User user;

    public ChatAdapter(Context context, User user) {
        super(context);
        this.user = user;
    }

    public void update(List<ChatMessage> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public ChatMessage getItem(int position) {
        return messages.get(position);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        ChatMessage message = getItem(position);
        if ( message.getUser().equals(user.getEmail())) {
            return inflater.inflate(R.layout.chat_item_sent, container, false);
        }

        return inflater.inflate(R.layout.chat_item_rcv, container, false);
    }

    @Override
    public void bindView(ChatMessage item, int position, View view) {
        ((ChatItemView) view).bindTo(item);
    }
}
