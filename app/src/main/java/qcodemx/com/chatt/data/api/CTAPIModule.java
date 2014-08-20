package qcodemx.com.chatt.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static retrofit.RestAdapter.LogLevel.FULL;

/**
 * Created by Eric Vargas on 8/6/14.
 *
 * Module to provide dependencies to communicate to the server API.
 */
@Module(
        complete = false,
        library = true
)
public final class CTAPIModule {
    public static final String BASE_URL = "http://107.170.213.39:3000";
//    public static final String BASE_URL = "http://192.168.1.69:3000";

    @Provides @Singleton Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(BASE_URL);
    }

    @Provides @Singleton Client provideClient(OkHttpClient okHttpClient) {
        return new OkClient(okHttpClient);
    }

    @Provides @Singleton RestAdapter provideRestAdapter(Endpoint endpoint, Client client) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();

        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(FULL)
                .build();
    }

    @Provides @Singleton UserService provideUserService(RestAdapter restAdapter) {
        return restAdapter.create(UserService.class);
    }

    @Provides @Singleton EventService provideEventService(RestAdapter restAdapter) {
        return restAdapter.create(EventService.class);
    }
}
