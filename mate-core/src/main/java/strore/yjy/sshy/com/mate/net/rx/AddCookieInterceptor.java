package strore.yjy.sshy.com.mate.net.rx;

import android.annotation.SuppressLint;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import strore.yjy.sshy.com.mate.util.storage.MatePreference;

/**
 * Created by 周正尧 on 2018/4/2 0002.
 * Description:
 * <p>
 * Email:100360258@qq.com
 */
public final class AddCookieInterceptor implements Interceptor {

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        Observable.just(MatePreference.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {
                        //给原生api请求附带上WebView拦截下来的Cookie
                        builder.addHeader("cookie", cookie);
                    }
                });

        return chain.proceed(builder.build());
    }
}
