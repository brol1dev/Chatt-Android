package qcodemx.com.chatt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import qcodemx.com.chatt.data.PreferencesManager;
import qcodemx.com.chatt.data.api.UserService;

import static qcodemx.com.chatt.SocialFragment.SOCIAL_NET_TAG;


public class LoginActivity extends CTActivity {
    private static final String LOG_TAG = "LoginActivity";

    @Inject PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        if (null != preferencesManager.retrieveCurrentUser()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new LoginFragment())
                    .commit();
        }

//        registerButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emailEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//
//                userService.signUp(new UserCredentials(email, password), new Callback<CTResponse>() {
//                    @Override
//                    public void success(CTResponse ctResponse, Response response) {
//                        if (ctResponse.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "User created", Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        CTResponse errorResponse = (CTResponse) error.getBody();
//                        if (error.isNetworkError()) {
//                            Toast.makeText(LoginActivity.this, "Is there network connectivity?", Toast.LENGTH_SHORT)
//                                    .show();
//                        } else if (null != errorResponse && null != errorResponse.getMessage()) {
//                            Toast.makeText(LoginActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    }
//                });
//            }
//        });
//
//        loginButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emailEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//
//                userService.signIn(new UserCredentials(email, password), new Callback<UserToken>() {
//                    @Override
//                    public void success(final UserToken userToken, Response response) {
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d(LOG_TAG, "logged user: " + userToken);
//                                if (preferencesManager.storeUser(userToken)) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                            finish();
//                                        }
//                                    });
//                                }
//                            }
//                        }).start();
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        CTResponse errorResponse = (CTResponse) error.getBody();
//                        if (error.isNetworkError()) {
//                            Toast.makeText(LoginActivity.this, "Is there network connectivity?", Toast.LENGTH_SHORT)
//                                    .show();
//                        } else if (null != errorResponse && null != errorResponse.getMessage()) {
//                            Toast.makeText(LoginActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    }
//                });
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SOCIAL_NET_TAG);
        if (null != fragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
