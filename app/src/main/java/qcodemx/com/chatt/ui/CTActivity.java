package qcodemx.com.chatt.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import dagger.ObjectGraph;
import qcodemx.com.chatt.CTApplication;
import qcodemx.com.chatt.Modules;

/**
 * Created by Eric Vargas on 8/5/14.
 *
 * Base activity to allow Dependency injection with Dagger.
 */
public class CTActivity extends ActionBarActivity {

    protected ObjectGraph activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addActivityModules();
    }

    @Override
    protected void onDestroy() {
        activityGraph = null;

        super.onDestroy();
    }

    public <T> void inject(T instance) {
        activityGraph.inject(instance);
    }

    private void addActivityModules() {
        CTApplication application = (CTApplication) getApplication();
        activityGraph = application.getApplicationGraph().plus(Modules.getActivityModules(this));
        activityGraph.inject(this);
    }
}
