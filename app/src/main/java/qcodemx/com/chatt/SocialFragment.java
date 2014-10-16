package qcodemx.com.chatt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;

import java.util.ArrayList;
import java.util.Arrays;

import static com.github.gorbin.asne.core.SocialNetworkManager.OnInitializationCompleteListener;

/**
 * Created by Eric Vargas on 10/9/14.
 *
 * Manages the SocialNetworkManager.
 */
public class SocialFragment extends CTFragment implements OnInitializationCompleteListener,
        OnLoginCompleteListener, OnRequestSocialPersonCompleteListener {
    private static final String LOG_TAG = "SocialFragment";

    public static final String SOCIAL_NET_TAG = "SocialManagerTag";

    public static final int GPLUS_NET_ID = 3;
    public static final int FB_NET_ID = 4;

    public static final String PROVIDER_FB_ID = "facebook";
    public static final String PROVIDER_GPLUS_ID = "google_plus";

    protected SocialNetworkManager socialNetworkManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        socialNetworkManager = (SocialNetworkManager) getFragmentManager()
                .findFragmentByTag(SOCIAL_NET_TAG);

        socialNetworkManager = new SocialNetworkManager();

        ArrayList<String> scope = new ArrayList<>();
        scope.addAll(Arrays.asList("public_profile, email"));

        FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(this, scope);
        socialNetworkManager.addSocialNetwork(fbNetwork);

        getFragmentManager().beginTransaction()
                .add(socialNetworkManager, SOCIAL_NET_TAG)
                .commit();
        socialNetworkManager.setOnInitializationCompleteListener(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /***** OnInitializationCompleteListener methods *****/

    @Override
    public void onSocialNetworkManagerInitialized() {
        Log.d(LOG_TAG, "onSocialNetworkManagerInitialized");
        for (SocialNetwork socialNetwork : socialNetworkManager.getInitializedSocialNetworks()) {
            Log.d(LOG_TAG, "social network initialization: " + socialNetwork.getID());
            socialNetwork.setOnLoginCompleteListener(this);
        }
    }

    /***** OnLoginCompleteListener methods *****/

    @Override
    public void onLoginSuccess(int netId) {
        Log.d(LOG_TAG, "onLoginSuccess: " + netId);
        SocialNetwork socialNetwork = socialNetworkManager.getSocialNetwork(netId);
        socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
        socialNetwork.requestCurrentPerson();
    }

    @Override
    public void onError(int netId, String requestId, String errorMsg, Object data) {
        Log.w(LOG_TAG, "Login error: " + errorMsg);
    }

    /***** OnRequestSocialPersonCompleteListener methods *****/

    @Override
    public void onRequestSocialPersonSuccess(int netId, SocialPerson socialPerson) {
        switch (netId) {
            case FB_NET_ID:
                if (this instanceof SignupFragment) {
                    ((SignupFragment) this).register(socialPerson.name, socialPerson.email,
                            null, socialPerson.avatarURL, PROVIDER_FB_ID);
                } else if (this instanceof LoginFragment) {

                }
                break;
        }

    }
}
