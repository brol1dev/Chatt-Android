package qcodemx.com.chatt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Vargas on 8/5/14.
 *
 * List of modules that are build the dependency graph for dagger.
 */
public final class Modules {

    private Modules() {}

    public static Object[] getGlobalModules(CTApplication application) {
        List<Object> modules = new ArrayList<>();
        modules.add(new CTMainModule(application));
        return modules.toArray();
    }

    public static Object[] getActivityModules(CTActivity activity) {
        List<Object> modules = new ArrayList<>();
        modules.add(new CTUIModule(activity));
        return modules.toArray();
    }
}
