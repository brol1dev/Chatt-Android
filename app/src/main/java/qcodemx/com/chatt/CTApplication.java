package qcodemx.com.chatt;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by Eric Vargas on 8/5/14.
 *
 * Custom Application object that initialize Dagger injection.
 */
public class CTApplication extends Application {

    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        buildObjectGraphAndInject(Modules.getGlobalModules(this));
    }

    public void buildObjectGraphAndInject(Object... modules) {
        applicationGraph = ObjectGraph.create(modules);
        applicationGraph.inject(this);
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
