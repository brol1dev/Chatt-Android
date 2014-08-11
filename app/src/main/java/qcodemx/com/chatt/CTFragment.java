package qcodemx.com.chatt;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Eric Vargas on 8/9/14.
 *
 * Base fragment for the app. Helps to inject dependencies.
 */
public class CTFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof CTActivity) {
            ((CTActivity) getActivity()).inject(this);
        }
    }
}
