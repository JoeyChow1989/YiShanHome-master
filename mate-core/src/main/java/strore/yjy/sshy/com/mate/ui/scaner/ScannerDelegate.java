package strore.yjy.sshy.com.mate.ui.scaner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import strore.yjy.sshy.com.mate.delegates.MateDelegate;
import strore.yjy.sshy.com.mate.util.callback.CallBackManager;
import strore.yjy.sshy.com.mate.util.callback.CallBackType;
import strore.yjy.sshy.com.mate.util.callback.IGlobalCallBack;

/**
 * create date：2018/5/2
 * create by：周正尧
 */
public class ScannerDelegate extends MateDelegate implements ZBarScannerView.ResultHandler {

    private ScanView mScanView = null;

    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView != null) {
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView != null) {
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanView == null) {
            mScanView = new ScanView(getContext());
        }
        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    public void handleResult(Result result) {
        @SuppressWarnings("unchecked") final IGlobalCallBack<String> callBack = CallBackManager
                .getInstance()
                .getCallBack(CallBackType.ON_SCAN);
        if (callBack != null) {
            callBack.executeCallback(result.getContents());
        }
        //getSupportDelegate().pop();
    }
}
