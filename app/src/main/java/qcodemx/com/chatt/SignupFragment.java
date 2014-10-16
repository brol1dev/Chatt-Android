package qcodemx.com.chatt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.gorbin.asne.core.SocialNetwork;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.data.api.CTRestClient;
import qcodemx.com.chatt.ui.util.UIUtils;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class SignupFragment extends SocialFragment {
    private static final String LOG_TAG = "LoginFragment";

    @InjectView(R.id.btn_facebook) Button facebookButton;
    @InjectView(R.id.btn_google) Button googleButton;
    @InjectView(R.id.btn_register) Button registerButton;
    @InjectView(R.id.btn_login) Button loginButton;
    @InjectView(R.id.edit_username) EditText usernameEditText;
    @InjectView(R.id.edit_email) EditText emailEditText;
    @InjectView(R.id.edit_password) EditText passwordEditText;

    @Inject CTRestClient ctRestClient;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.inject(this, view);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validate fields? The server validates them.
                // In here at least validate that the email has a good email pattern.
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                register(username, email, password, null, null);
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerWithSocialNet(FB_NET_ID);
            }
        });

        return view;
    }

    private void login() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new LoginFragment())
                .commit();
    }

    private void registerWithSocialNet(int networkId) {
        SocialNetwork socialNetwork = socialNetworkManager.getSocialNetwork(networkId);
        if (!socialNetwork.isConnected()) {
            socialNetwork.requestLogin();
        } else {
            onLoginSuccess(socialNetwork.getID());
        }
    }

    protected void register(String username, String email, String password, String imageUrl, String providerId) {

        try {
            JSONObject params = new JSONObject();
            params.put("email", email);
            params.put("username", username);
            params.put("password", password);
            params.put("provider_id", providerId);
            params.put("image_url", imageUrl);
            ctRestClient.post("/signup", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d(LOG_TAG, "onSuccessSignup: " + response);

                    UIUtils.createToast(getActivity(), "Account created.");
                    login();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d(LOG_TAG, "onFailureSignup: " + errorResponse);

                    try {
                        UIUtils.createToast(getActivity(), errorResponse.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
