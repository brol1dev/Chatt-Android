package qcodemx.com.chatt.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import qcodemx.com.chatt.LoginActivity;

/**
 * Created by Eric Vargas on 8/5/14.
 *
 * Module to inject dependencies to activities.
 */
@Module(
        injects = {
                LoginActivity.class
        },
        library = true,
        complete = false
)
public final class CTUIModule {

    private final CTActivity activity;

    public CTUIModule(CTActivity activity) {
        this.activity = activity;
    }

    @Provides @Singleton CTActivity provideActivity() {
        return activity;
    }
}
