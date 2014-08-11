package qcodemx.com.chatt.data;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import qcodemx.com.chatt.data.api.User;
import qcodemx.com.chatt.data.api.UserToken;

/**
 * Created by Eric Vargas on 8/8/14.
 *
 * Access the Shared Preferences to obtain info about a user, if it is authenticated.
 */
@Singleton
public class PreferencesManager {
    private static final String USER_EMAIL = "email";
    private static final String USER_TOKEN = "token";
    private static final String REGISTRATION_ID = "reg_id";

    private final SharedPreferences preferences;

    @Inject
    public PreferencesManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public boolean storeUser(UserToken userToken) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(USER_EMAIL, userToken.getUser().getEmail())
                .putString(USER_TOKEN, userToken.getToken());
//                .putString(REGISTRATION_ID, userToken.getRegistrationId());

        return editor.commit();
    }

    public UserToken retrieveCurrentUser() {
        String email = preferences.getString(USER_EMAIL, "");
        String token = preferences.getString(USER_TOKEN, "");
        String regId = preferences.getString(REGISTRATION_ID, "");

        if (email.isEmpty() || token.isEmpty())
            return null;

        User user = new User(email);
        return new UserToken(token, regId, user);
    }

    public boolean clearUser() {
        return preferences.edit().clear().commit();
    }
}
