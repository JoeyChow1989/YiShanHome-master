package strore.yjy.sshy.com.mate.util.callback;

import io.reactivex.annotations.Nullable;

/**
 * create date：2018/4/23
 * create by：周正尧
 */
public interface IGlobalCallBack<T> {

    void executeCallback(@Nullable T args);

}
