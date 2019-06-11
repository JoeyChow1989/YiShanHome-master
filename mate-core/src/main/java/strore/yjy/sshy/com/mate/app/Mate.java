package strore.yjy.sshy.com.mate.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**
 * Created by zzy on 2018/2/28/028.
 */

public final class Mate {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getMateConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT,
                        context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
            return getConfigurator().getConfiguration(key);
    }

    public static Application getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    public static void test(){
    }



}
