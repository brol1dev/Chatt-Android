package qcodemx.com.chatt.data;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import qcodemx.com.chatt.model.User;

/**
 * Created by Eric Vargas on 8/8/14.
 *
 * Access the Shared Preferences to obtain info about a user, if it is authenticated.
 */
@Singleton
public class PreferencesManager {
    private static final String USER_ID = "user_id";
    private static final String USER_EMAIL = "email";
    private static final String USER_NAME = "name";
    private static final String USER_IMAGE = "img";
    private static final String USER_TOKEN = "token";
    private static final String DEVICE_ID = "device_id";

    private final SharedPreferences preferences;

    @Inject
    public PreferencesManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public boolean storeUser(User user) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(USER_EMAIL, user.getEmail())
                .putString(USER_TOKEN, user.getToken())
                .putString(USER_ID, user.getId())
                .putString(USER_NAME, user.getName())
                .putString(USER_IMAGE, user.getImageUrl())
                .putString(DEVICE_ID, user.getDeviceId());

        return editor.commit();
    }

    public User retrieveCurrentUser() {
        String userId = preferences.getString(USER_ID, "");
        String email = preferences.getString(USER_EMAIL, "");
        String username = preferences.getString(USER_NAME, "");
        String token = preferences.getString(USER_TOKEN, "");
        String deviceId = preferences.getString(DEVICE_ID, "");
        String imageUrl = preferences.getString(USER_IMAGE, "");

        if (email.isEmpty() || token.isEmpty()
                || userId.isEmpty() || deviceId.isEmpty() || username.isEmpty())
            return null;

        return new User(userId, deviceId, username, email, imageUrl, token);
    }

    public boolean clearUser() {
        return preferences.edit().clear().commit();
    }
}
