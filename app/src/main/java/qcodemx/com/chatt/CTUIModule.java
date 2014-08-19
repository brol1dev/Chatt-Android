package qcodemx.com.chatt;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric Vargas on 8/5/14.
 *
 * Module to inject dependencies to activities.
 */
@Module(
        injects = {
                LoginActivity.class,
                MainActivity.class,
                EventListFragment.class,
                NewEventActivity.class,
                EventChatActivity.class
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
