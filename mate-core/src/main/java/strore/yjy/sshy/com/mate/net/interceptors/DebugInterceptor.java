package strore.yjy.sshy.com.mate.net.interceptors;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import strore.yjy.sshy.com.mate.util.file.FileUtil;

/**
 * Created by zzy on 2018/3/12/012.
 */

public class DebugInterceptor extends BaseInterceptor{

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int debug_rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = debug_rawId;
    }

    private Response getResponse(Chain chain,String json){
        return new Response.Builder()
                .code(200)
                .addHeader("token","application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"),json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private Response debugResponse(Chain chain,@RawRes int rawId){
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain,json);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //获取用户构建的Request对象
        Request request = chain.request();
        final String url = request.url().toString();
        if (url.contains(DEBUG_URL)){
            return debugResponse(chain,DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
