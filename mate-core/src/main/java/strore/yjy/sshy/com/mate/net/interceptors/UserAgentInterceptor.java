package strore.yjy.sshy.com.mate.net.interceptors;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * create date：2018/12/14
 * create by：周正尧
 */
public class UserAgentInterceptor extends BaseInterceptor {

    private final String KEY;
    private final String VALUE;

    public UserAgentInterceptor(String key, String value) {
        this.KEY = key;
        this.VALUE = value;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .addHeader(KEY, VALUE)
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }
}
