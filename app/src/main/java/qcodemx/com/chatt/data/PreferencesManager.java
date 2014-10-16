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
    private static final String USER_ID = "user_id";
    private static final String USER_EMAIL = "email";
    private static final String USER_TOKEN = "token";
    private static final String DEVICE_ID = "device_id";

    private final SharedPreferences preferences;

    @Inject
    public PreferencesManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public boolean storeUser(UserToken userToken) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(USER_EMAIL, userToken.getUser().getEmail())
                .putString(USER_TOKEN, userToken.getToken())
                .putString(USER_ID, userToken.getUser().getId());
//                .putString(REGISTRATION_ID, userToken.getRegistrationId());

        return editor.commit();
    }

    public UserToken retrieveCurrentUser() {
        String userId = preferences.getString(USER_ID, "");
        String email = preferences.getString(USER_EMAIL, "");
        String token = preferences.getString(USER_TOKEN, "");
        String deviceId = preferences.getString(DEVICE_ID, "");

        if (email.isEmpty() || token.isEmpty() || userId.isEmpty())
            return null;

        User user = new User(userId, email);
        return new UserToken(token, deviceId, user);
    }

    public boolean clearUser() {
        return preferences.edit().clear().commit();
    }
}
