package com.star.xstartactivityforresult;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import io.reactivex.Observable;

/**
 * 供外界使用
 *
 * @author xueshanshan
 * @date 2018/12/9
 */
public class XStartActivityForResult {
    /**
     * 在fragment内部startActivityForResult
     *
     * @param fragment    当前fragment
     * @param intent      要跳转的intent
     * @param requestCode 请求的requestCode
     * @param callback    回调
     */
    public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode, OnResultCallback callback) {
        startActivityForResult(fragment.getActivity(), intent, requestCode, callback);
    }

    /**
     * 在activity内部startActivityForResult
     *
     * @param activity    当前activity
     * @param intent      要跳转的intent
     * @param requestCode 请求的requestCode
     * @param callback    回调
     */
    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, OnResultCallback callback) {
        OnResultManagerByFragment.startActivityForResult(activity, intent, requestCode, callback);
    }

    /**
     * 在fragment内部使用Rxjava方式startActivityForResult
     *
     * @param fragment    当前fragment
     * @param intent      要跳转的intent
     * @param requestCode 请求的requestCode
     */
    public static Observable<ActivityResultInfo> startActivityForResult(Fragment fragment, Intent intent, int requestCode) {
        return startActivityForResultRx(fragment.getActivity(), intent, requestCode);
    }

    /**
     * 在activity内部使用Rxjava方式startActivityForResult
     *
     * @param activity    当前activity
     * @param intent      要跳转的intent
     * @param requestCode 请求的requestCode
     */
    public static Observable<ActivityResultInfo> startActivityForResultRx(Activity activity, Intent intent, int requestCode) {
        return OnResultManagerByFragment.startActivityForResultRx(activity, intent, requestCode);
    }
}
