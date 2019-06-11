package strore.yjy.sshy.com.mate.ui.camera;


import android.net.Uri;

import strore.yjy.sshy.com.mate.delegates.BaseDelegate;
import strore.yjy.sshy.com.mate.delegates.PermissionCheckerDelegate;
import strore.yjy.sshy.com.mate.util.file.FileUtil;

/**
 * 照相机调用类
 */
public class MateCamera {

    public static Uri createCropFile() {
        return Uri.parse(FileUtil.createFile("crop_image",
                FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(BaseDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
