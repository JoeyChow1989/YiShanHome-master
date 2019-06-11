package strore.yjy.sshy.com.mate.app;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by Administrator on 2018/2/28/028.
 */

public class Configurator {

    private static final HashMap<Object, Object> MATE_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        MATE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
        MATE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    final HashMap<Object, Object> getMateConfigs() {
        return MATE_CONFIGS;
    }

    public final void configure() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        MATE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
        Utils.init(Mate.getApplicationContext());
    }

    public final Configurator withApiHost(String host) {
        MATE_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withWebApiHost(String host) {
        //只留下域名，否则无法同步cookie，不能带http://或末尾的/
        String hostName = host
                .replace("http://", "")
                .replace("https://", "");
        hostName = hostName.substring(0, hostName.lastIndexOf('/'));
        MATE_CONFIGS.put(ConfigKeys.WEB_API_HOST, hostName);
        return this;
    }

    public final Configurator withLoaderDelayed(long delayed) {
        MATE_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        MATE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        MATE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        MATE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        MATE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public Configurator withJavascriptInterface(@NonNull String name) {
        MATE_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    //浏览器加载的host
    public Configurator withWebHost(String host) {
        MATE_CONFIGS.put(ConfigKeys.WEB_HOST, host);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) MATE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = MATE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) MATE_CONFIGS.get(key);
    }

}
