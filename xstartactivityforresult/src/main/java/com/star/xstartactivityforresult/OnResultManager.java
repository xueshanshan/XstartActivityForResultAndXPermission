package com.star.xstartactivityforresult;

import android.app.Activity;
import android.content.Intent;
import android.util.SparseArray;

import java.lang.ref.WeakReference;

/**
 * @author xueshanshan
 * @date 2018/9/14
 *
 * 管理startActivityResult
 * 优点：
 * - 启动Activity想要回传数据可以直接设置监听，像setOnclickLister一样简单
 * - 不用在onActivityResult里面去进行switch case了
 * - 之所以不将其方法写成static而是实例方法，主要是为了减少内存泄漏，
 * 如果将其方法都写为static，那么缓存的就不应该是SparseArray<OnResultCallback>，
 * 而应该是WeakHashMap<Activity,SparseArray<OnResultCallback>>，因为不同activity里面可能有同样的requestCode，所以要将activity作为key
 */
public class OnResultManager {
    //使用弱引用，减少内存泄漏
    private WeakReference<Activity> mActivityWeakReference;
    private SparseArray<OnResultCallback> mCallbacks = new SparseArray<>();

    public OnResultManager(Activity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
    }

    /**
     * 使用该方法去进行startActivityForResult
     *
     * @param requestCode
     * @param intent
     * @param callback
     */
    public void startActivityForResult(int requestCode, Intent intent, OnResultCallback callback) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        addCallback(requestCode, callback);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 需要在Activity的onActivityResult中调用该方法，可以放在BaseActivity中
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        OnResultCallback onResultCallback = findCallback(requestCode);
        if (onResultCallback != null) {
            onResultCallback.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 添加callback
     *
     * @param requestCode
     * @param callback
     */
    private void addCallback(int requestCode, OnResultCallback callback) {
        mCallbacks.put(requestCode, callback);
    }

    private Activity getActivity() {
        return mActivityWeakReference.get();
    }

    /**
     * 查找callback，如果查找到就移除
     *
     * @param requestCode
     * @return
     */
    private OnResultCallback findCallback(int requestCode) {
        OnResultCallback onResultCallback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);
        return onResultCallback;
    }
}
