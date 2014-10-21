package qcodemx.com.chatt.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import qcodemx.com.chatt.R;

/**
 * Created by Eric Vargas on 10/20/14.
 *
 * Dialog to invite a person to a private chat.
 */
public class InviteDialogFragment extends DialogFragment {

    private ChatDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (ChatDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ChatDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_chat_invite, null);

        builder.setTitle(R.string.invite)
                .setView(view)
                .setPositiveButton(R.string.invite, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (null != listener) {
                            EditText editText = (EditText) view.findViewById(R.id.text_invite);
                            listener.onInviteUser(editText.getText().toString());
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    public interface ChatDialogListener {
        void onInviteUser(String username);
    }
}
