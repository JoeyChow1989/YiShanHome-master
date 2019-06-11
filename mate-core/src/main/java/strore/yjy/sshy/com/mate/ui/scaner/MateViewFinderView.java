package strore.yjy.sshy.com.mate.ui.scaner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * create date：2018/5/2
 * create by：周正尧
 */
public class MateViewFinderView extends ViewFinderView {

    public MateViewFinderView(Context context) {
        this(context, null);
    }

    public MateViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;
        mBorderPaint.setColor(Color.YELLOW);
        mLaserPaint.setColor(Color.YELLOW);
    }
}
