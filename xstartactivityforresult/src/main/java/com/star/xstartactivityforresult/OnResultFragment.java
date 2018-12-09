package com.star.xstartactivityforresult;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * @author xueshanshan
 * @date 2018/9/17
 */
public class OnResultFragment extends Fragment {
    private SparseArray<OnResultCallback> mCallbacks = new SparseArray<>();
    private SparseArray<PublishSubject<ActivityResultInfo>> mSubjects = new SparseArray<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置fragment保留，已保留的fragment（在异常情况下）不会随着activity一起销毁，相反，它会一直保留（进程不消亡的前提下）
        //比如在设备旋转的情况下，该fragment可暂时与Activity分离，等activity重新创建后，会将该fragment与activity重新绑定,fragment数据不会丢失
        //如果在恰好在分离的那段时间使用Context信息，就可能会出错，这时可以使用isAdded()判断Fragment是否绑定到Activity
        setRetainInstance(true);
    }

    protected void startActivityForResult(Intent intent, int requestCode, OnResultCallback callback) {
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    protected Observable<ActivityResultInfo> startActivityForResultRx(final Intent intent, final int requestCode) {
        PublishSubject<ActivityResultInfo> publishSubject = PublishSubject.create();
        mSubjects.put(requestCode, publishSubject);
        return publishSubject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PublishSubject<ActivityResultInfo> subject = mSubjects.get(requestCode);
        if (subject != null) {
            mSubjects.remove(requestCode);
            subject.onNext(new ActivityResultInfo(requestCode, resultCode, data));
            subject.onComplete();
        }

        //callback方式的处理
        OnResultCallback onResultCallback = mCallbacks.get(requestCode);
        if (onResultCallback != null) {
            mCallbacks.remove(requestCode);
            onResultCallback.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
