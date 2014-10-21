package qcodemx.com.chatt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.gorbin.asne.core.SocialNetwork;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.data.PreferencesManager;
import qcodemx.com.chatt.data.api.CTRestClient;
import qcodemx.com.chatt.model.User;
import qcodemx.com.chatt.ui.util.UIUtils;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LoginFragment extends SocialFragment {
    private static final String LOG_TAG = "LoginFragment";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String SENDER_ID = "1033375515679";

    @InjectView(R.id.btn_facebook) Button facebookButton;
    @InjectView(R.id.btn_google) Button googleButton;
    @InjectView(R.id.btn_register) Button registerButton;
    @InjectView(R.id.btn_login) Button loginButton;
    @InjectView(R.id.edit_email) EditText emailEditText;
    @InjectView(R.id.edit_password) EditText passwordEditText;

    @Inject CTRestClient ctRestClient;
    @Inject PreferencesManager preferencesManager;

    private GoogleCloudMessaging gcm;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                login(email, password, null);
            }
        });
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithSocialNet(FB_NET_ID);
            }
        });

        return view;
    }

    private void signup() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SignupFragment())
                .commit();
    }

    private void loginWithSocialNet(int networkId) {
        SocialNetwork socialNetwork = socialNetworkManager.getSocialNetwork(networkId);
        if (!socialNetwork.isConnected()) {
            socialNetwork.requestLogin();
        } else {
            onLoginSuccess(socialNetwork.getID());
        }
    }

    protected void login(String email, String password, String providerId) {
        if (checkPlayServices()) {
            new LoginAsyncTask().execute(email, password, providerId);
        }
    }

    private String obtainGcmDeviceId() throws IOException {

        if (null == gcm) gcm = GoogleCloudMessaging.getInstance(getActivity());
        return gcm.register(SENDER_ID);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.e(LOG_TAG, "This device is not supported.");
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject reqParams = null;
            try {
                String deviceId = obtainGcmDeviceId();

                reqParams = new JSONObject();
                reqParams.put("email", params[0]);
                reqParams.put("password", params[1]);
                reqParams.put("provider_id", params[2]);
                reqParams.put("device_id", deviceId);

                Log.d(LOG_TAG, "request params: " + reqParams);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return reqParams;
        }

        @Override
        protected void onPostExecute(JSONObject reqParams) {
            if (null != reqParams) {
                try {
                    ctRestClient.post("/signin", reqParams, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                            Log.d(LOG_TAG, "onSuccessSignin: " + response);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonUser = response.getJSONObject("user");
                                    String userId = jsonUser.getString("id");
                                    String username = jsonUser.getString("username");
                                    String imageUrl = jsonUser.getString("image_url");
                                    String email = jsonUser.getString("email");
                                    String deviceId = response.getString("device_id");
                                    String token = response.getString("token");

                                    User user = new User(userId, deviceId, username, email, imageUrl, token);
                                    if (preferencesManager.storeUser(user)) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(getActivity(), MainActivity.class));
                                                getActivity().finish();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d(LOG_TAG, "onFailureSignin: " + errorResponse);

                            try {
                                UIUtils.createToast(LoginFragment.this.getActivity(), errorResponse.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
