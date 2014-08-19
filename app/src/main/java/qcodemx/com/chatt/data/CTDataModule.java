package qcodemx.com.chatt.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import qcodemx.com.chatt.data.api.CTAPIModule;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Eric Vargas on 8/6/14.
 *
 * Provides general dependencies to manage data.
 */
@Module(
        includes = CTAPIModule.class,
        library = true,
        complete = false
)
public final class CTDataModule {
    private static final String LOG_TAG = "CTDataModule";

    private static final String PREFS_FILE = "chatt_preferences";

    private static final String CACHE_FILE = "cache";
    private static final int DISK_CACHE_SIZE = 20 * 1024 * 1024;

    @Provides @Singleton SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
    }

    @Provides @Singleton OkHttpClient provideOkHttpClient(Application application) {
        return createOkHttpClient(application);
    }

    @Provides @Singleton Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(client))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e(LOG_TAG, "Failed to load image: " + uri);
                    }
                })
                .indicatorsEnabled(true)
                .build();
    }

    static OkHttpClient createOkHttpClient(Application application) {
        OkHttpClient client = new OkHttpClient();

        try {
            File cacheDir = new File(application.getCacheDir(), CACHE_FILE);
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
            client.setConnectTimeout(1, TimeUnit.SECONDS);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to install disk cache.");
        }

        return client;
    }
}
