package strore.yjy.sshy.com.mate.ui.camera;

import android.net.Uri;

/**
 * create date：2018/4/18
 * create by：周正尧
 */
public final class CameraImageBean {

    private Uri mPath = null;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}