package strore.yjy.sshy.com.mate.util.callback;

import java.util.WeakHashMap;

/**
 * create date：2018/4/23
 * create by：周正尧
 */
public class CallBackManager {

    private static final WeakHashMap<Object, IGlobalCallBack> CALLBACKS = new WeakHashMap<>();

    private static final class Holder {
        private static final CallBackManager INSTANCE = new CallBackManager();
    }

    public static CallBackManager getInstance() {
        return Holder.INSTANCE;
    }

    public CallBackManager addCallBack(Object tag, IGlobalCallBack callBack) {
        CALLBACKS.put(tag, callBack);
        return this;
    }

    public IGlobalCallBack getCallBack(Object tag) {
        return CALLBACKS.get(tag);
    }
}
