package strore.yjy.sshy.com.mate.app;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author jason
 */
public final class AppConfig {

    public static final String API_HOST = "http://www.51ysdj.com/";
    public static final String HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION";

    //微信appId
    public static final String WXAPP_ID = "wxf10066c179e38b8a";
    public static final String WXAPP_SECRET = "69b8ad3e79905e914f481fe03251f38d";

    //detail---id
    public static final String ARG_MERCHANT_ID = "ARG_MERCHANT_ID";
    public static final String ARG_GOODS_ID = "ARG_GOODS_ID";

    //detail
    public static final String ARG_MERCHANT_IMG = "ARG_MERCHANT_IMG";
    public static final String ARG_MERCHANT_NAME = "ARG_MERCHANT_NAME";
    public static final String ARG_MERCHANT_SCORE = "ARG_MERCHANT_SCORE";
    public static final String ARG_MERCHANT_QUITY = "ARG_MERCHANT_QUITY ";
    public static final String ARG_MERCHANT_PUB = "ARG_MERCHANT_PUB";
    public static final String ARG_MERCHANT_ADDR = "ARG_MERCHANT_ADDR";
    public static final String ARG_MERCHANT_DIS = "ARG_MERCHANT_DIS";
    public static final String ARG_MERCHANT_LINK = "ARG_MERCHANT_LINK";
    public static final String ARG_PRODUCT_TAG = "ARG_PRODUCT_TAG";
    public static final String ARG_PRODUCT_CAT = "ARG_PRODUCT_CAT";

    //address
    public static final String ARG_ADDRESS_ID = "ARG_ADDRESS_ID";
    public static final String ARG_ADDRESS_ADDRESS = "ARG_ADDRESS_ADDRESS";
    public static final String ARG_ADDRESS_DEFAULT = "ARG_ADDRESS_DEFAULT";

    //tag
    public static final String ARG_COMMENT_TAG = "ARG_COMMENT_TAG";

    //order
    public static final String ARG_ORDER_NO = "ARG_ORDER_NO";

    //author
    public static final String ARG_AUTHOR_PIC = "ARG_AUTHOR_PIC";
    public static final String ARG_AUTHOR_NICKNAME = "ARG_AUTHOR_NICKNAME";

    //设置图片加载策略
    public static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();
}
