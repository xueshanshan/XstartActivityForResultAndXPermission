package com.star.xstartactivityforresult;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;

import io.reactivex.Observable;

/**
 * 通过fragment来进行result请求，方式更加简单优雅，简化宿主app集成逻辑
 *
 * @author xueshanshan
 * @date 2018/9/17
 */
public class OnResultManagerByFragment {
    private static String TAG = "OnResultFragment";

    private static OnResultFragment getOnResultFragment(Activity activity) {
        OnResultFragment onResultFragment = findOnResultFragment(activity);
        if (onResultFragment == null) {
            onResultFragment = new OnResultFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction().add(onResultFragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return onResultFragment;
    }

    private static OnResultFragment findOnResultFragment(Activity activity) {
        return (OnResultFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }


    protected static void startActivityForResult(Activity activity, Intent intent, int requestCode, OnResultCallback callback) {
        getOnResultFragment(activity).startActivityForResult(intent, requestCode, callback);
    }

    //这个是使用Rxjava方式，作为参考
    protected static Observable<ActivityResultInfo> startActivityForResultRx(Activity activity, Intent intent, int requestCode) {
        return getOnResultFragment(activity).startActivityForResultRx(intent, requestCode);
    }
}
