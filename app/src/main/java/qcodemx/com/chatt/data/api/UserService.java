package qcodemx.com.chatt.data.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import static qcodemx.com.chatt.data.api.User.UserCredentials;

/**
 * Created by Eric Vargas on 8/6/14.
 *
 * API for user authentication and authorization.
 */
public interface UserService {

    @POST("/signup")
    void signUp(@Body UserCredentials user, Callback<CTResponse> callback);

    @POST("/signin")
    void signIn(@Body UserCredentials user, Callback<UserToken> callback);
}
