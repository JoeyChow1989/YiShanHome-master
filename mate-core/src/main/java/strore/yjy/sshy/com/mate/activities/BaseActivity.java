package strore.yjy.sshy.com.mate.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import strore.yjy.sshy.com.mate.ActivityManager.AppManager;
import strore.yjy.sshy.com.mate.util.log.MateLogger;

/**
 * Created by zzy on 2018/2/28/028.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState);

    public <T extends View> T $(@IdRes int viewId) {
        return findViewById(viewId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        if (setLayout() instanceof Integer) {
            setContentView((Integer) setLayout());
        } else if (setLayout() instanceof View) {
            setContentView((View) setLayout());
        } else {
            throw new ClassCastException("setLayout() type must be view or int!");
        }

        onBindView(savedInstanceState);
        //同时请求多个权限
        requestPermissions();
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        //Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.INSTALL_PACKAGES)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            MateLogger.d("requestPermission", permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            MateLogger.d("requestPermission", permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            MateLogger.d("requestPermission", permission.name + " is denied.");
                        }
                    }
                });
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        AppManager.getInstance().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        setStatusBar();
    }

    public abstract void setStatusBar();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
