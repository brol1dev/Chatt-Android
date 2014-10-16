package qcodemx.com.chatt.ui.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Eric Vargas on 10/14/14.
 *
 * Utilities to construct Toast or dialogs.
 */
public class UIUtils {

    public static void createToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
