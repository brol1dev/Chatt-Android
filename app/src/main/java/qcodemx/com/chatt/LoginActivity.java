package qcodemx.com.chatt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.data.api.CTResponse;
import qcodemx.com.chatt.data.api.UserService;
import qcodemx.com.chatt.ui.CTActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.view.View.OnClickListener;
import static qcodemx.com.chatt.data.api.User.UserCredentials;


public class LoginActivity extends CTActivity {

    @InjectView(R.id.btn_register) Button registerButton;
    @InjectView(R.id.btn_login) Button loginButton;
    @InjectView(R.id.edit_email) EditText emailEditText;
    @InjectView(R.id.edit_password) EditText passwordEditText;

    @Inject UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                userService.signUp(new UserCredentials(email, password), new Callback<CTResponse>() {
                    @Override
                    public void success(CTResponse ctResponse, Response response) {
                        if (ctResponse.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "User created", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        CTResponse errorResponse = (CTResponse) error.getBody();
                        Toast.makeText(LoginActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });
    }
}
