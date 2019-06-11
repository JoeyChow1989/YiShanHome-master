package strore.yjy.sshy.com.mate.util.timer;

import java.util.TimerTask;

/**
 * Created by zzy on 2018/3/12/012.
 */

public class BaseTimerTask extends TimerTask{

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null){
            mITimerListener.onTimer();
        }
    }
}
