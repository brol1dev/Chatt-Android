package qcodemx.com.chatt.data.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import qcodemx.com.chatt.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.loopj.android.http.RequestParams.APPLICATION_JSON;

/**
 * Created by Eric Vargas on 10/9/14.
 *
 * Makes HTTP REST calls to the server.
 */
@Singleton
public class CTRestClient {

//    private static final String BASE_URL = "http://10.12.1.221:3000";      // Work
//    private static final String BASE_URL = "http://192.168.1.68:3000";      // Home
    private static final String BASE_URL = "http://104.131.148.97:3000";    // Cloud

    private static final String HEADER_AUTH = "Authorization";

    private final AsyncHttpClient client;

    @Inject
    public CTRestClient(AsyncHttpClient client) {
        this.client = client;
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void get(String url, User user, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        this.addAuthorizationHeader(user);
        this.get(url, params, responseHandler);
    }

    public void post(String url, JSONObject params, AsyncHttpResponseHandler responseHandler)
            throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(params.toString());
        client.post(null, getAbsoluteUrl(url), entity, APPLICATION_JSON, responseHandler);
    }

    public void post(String url, User user, JSONObject params, AsyncHttpResponseHandler responseHandler)
            throws UnsupportedEncodingException {
        this.addAuthorizationHeader(user);
        this.post(url, params, responseHandler);
    }

    public void put(String url, User user, JSONObject params, AsyncHttpResponseHandler responseHandler)
            throws UnsupportedEncodingException {
        this.addAuthorizationHeader(user);
        StringEntity entity = new StringEntity(params.toString());
        client.put(null, getAbsoluteUrl(url), entity, APPLICATION_JSON, responseHandler);
    }

    private void addAuthorizationHeader(User user) {
        client.addHeader(HEADER_AUTH, "Bearer " + user.getToken());
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
