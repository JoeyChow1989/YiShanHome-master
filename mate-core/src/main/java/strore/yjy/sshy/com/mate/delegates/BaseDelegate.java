package strore.yjy.sshy.com.mate.delegates;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import strore.yjy.sshy.com.mate.activities.BaseActivity;

/**
 * Created by Administrator on 2018/2/28/028.
 */

public abstract class BaseDelegate extends Fragment {


    private View mRootView = null;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    public <T extends View> T $(@IdRes int viewId) {
        if (mRootView != null) {
            return mRootView.findViewById(viewId);
        }
        throw new NullPointerException("rootView is null");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("setLayout() type must be view or int!");
        }
        mRootView = rootView;
        onBindView(savedInstanceState, rootView);
        return rootView;
    }

    public final BaseActivity getProxyActivity() {
        return (BaseActivity) _mActivity;
    }

    protected FragmentActivity _mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
