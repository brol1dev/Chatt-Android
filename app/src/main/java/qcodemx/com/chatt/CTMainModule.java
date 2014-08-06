package qcodemx.com.chatt;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric Vargas on 8/5/14.
 *
 * First module in the dependency graph.
 */

@Module(
        injects = CTApplication.class,
        library = true
)
public final class CTMainModule {

    private final CTApplication application;

    public CTMainModule(CTApplication application) {
        this.application = application;
    }

    @Provides @Singleton Application provideApplication() {
        return application;
    }
}
