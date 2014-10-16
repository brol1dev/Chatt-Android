package qcodemx.com.chatt.data.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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

    private static final String BASE_URL = "http://192.168.1.68:3000";

    private final AsyncHttpClient client;

    @Inject
    public CTRestClient(AsyncHttpClient client) {
        this.client = client;
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, JSONObject params, AsyncHttpResponseHandler responseHandler)
            throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(params.toString());
        client.post(null, getAbsoluteUrl(url), entity, APPLICATION_JSON, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
