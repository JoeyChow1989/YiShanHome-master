package strore.yjy.sshy.com.mate.delegates;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.zzy.mate.ucrop.UCrop;

import strore.yjy.sshy.com.mate.ui.camera.CameraImageBean;
import strore.yjy.sshy.com.mate.ui.camera.MateCamera;
import strore.yjy.sshy.com.mate.ui.camera.RequestCodes;
import strore.yjy.sshy.com.mate.util.callback.CallBackManager;
import strore.yjy.sshy.com.mate.util.callback.CallBackType;
import strore.yjy.sshy.com.mate.util.callback.IGlobalCallBack;

import static android.app.Activity.RESULT_OK;

/**
 * Created by zzy on 2018/2/28/028.
 */

public abstract class PermissionCheckerDelegate extends BaseDelegate {

    public static PermissionCheckerDelegate activity = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = this;
    }

//    //不是直接调用方法
//    @NeedsPermission(Manifest.permission.CAMERA)
//    void startCamera() {
//        MateCamera.start(this);
//    }
//
//    //这个是真正调用的方法
//    public void startCameraWithCheck() {
//        if (isAdded())
//            PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
//        //startCamera();
//    }
//
//    //扫描二维码(不直接调用)
//    @NeedsPermission(Manifest.permission.CAMERA)
//    void StartScan(BaseDelegate delegate) {
//        //delegate.startForResult(new ScannerDelegate(), RequestCodes.SCAN);
//    }
//
//    public void startScanWithCheck(BaseDelegate delegate) {
//        //PermissionCheckerDelegatePermissionsDispatcher.StartScanWithCheck(this, delegate);
//    }
//
//    @OnPermissionDenied(Manifest.permission.CAMERA)
//    void onCameraDenied() {
//        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_LONG).show();
//    }
//
//    @OnNeverAskAgain(Manifest.permission.CAMERA)
//    void onCameraNever() {
//        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
//    }
//
//    @OnShowRationale(Manifest.permission.CAMERA)
//    void onCameraRationale(PermissionRequest request) {
//        showRationaleDialog(request);
//    }
//
//    private void showRationaleDialog(final PermissionRequest request) {
//        new AlertDialog.Builder(getContext())
//                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        request.proceed();
//                    }
//                })
//                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        request.cancel();
//                    }
//                })
//                .setCancelable(false)
//                .setMessage("权限管理")
//                .show();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionCheckerDelegatePermissionsDispatcher
//                .onRequestPermissionsResult(this, requestCode, grantResults);
//    }
}
