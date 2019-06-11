package strore.yjy.sshy.com.mate.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import strore.yjy.sshy.com.mate.app.Mate;

/**
 * Created by zzy on 2018/3/2/002.
 */

public class DimenUtil {

    public static int getScreenWidth(){
        final Resources resources = Mate.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Mate.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}
