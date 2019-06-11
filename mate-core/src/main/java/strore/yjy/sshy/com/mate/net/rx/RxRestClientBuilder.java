package strore.yjy.sshy.com.mate.net.rx;

import android.content.Context;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import strore.yjy.sshy.com.mate.net.RestClient;
import strore.yjy.sshy.com.mate.net.RestClientBuilder;
import strore.yjy.sshy.com.mate.net.RestCreator;
import strore.yjy.sshy.com.mate.net.callback.IError;
import strore.yjy.sshy.com.mate.net.callback.IFailure;
import strore.yjy.sshy.com.mate.net.callback.IRequest;
import strore.yjy.sshy.com.mate.net.callback.ISuccess;
import strore.yjy.sshy.com.mate.ui.loader.LoaderStyle;

/**
 * Created by zzy on 2018/3/2/002.
 */

public class RxRestClientBuilder {

    private String mUrl = null;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private Context mContext = null;
    private String mHeader = null;

    RxRestClientBuilder() {

    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder header(String header) {
        this.mHeader = header;
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS, mBody, mHeader, mContext, mLoaderStyle, mFile);
    }

}
