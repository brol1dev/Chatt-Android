package qcodemx.com.chatt.data.api;

import qcodemx.com.chatt.model.Event;
import qcodemx.com.chatt.model.EventManager;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Eric Vargas on 8/18/14.
 *
 * API to manage events in the server.
 */
public interface EventService {

    @POST("/api/event")
    void createEvent(@Header("Authorization") String authorization,
                     @Body Event event, Callback<Event> callback);

    @GET("/api/event")
    void getEvents(@Header("Authorization") String authorization,
                   @Query("user_id") String userId, Callback<EventManager> callback);
}
