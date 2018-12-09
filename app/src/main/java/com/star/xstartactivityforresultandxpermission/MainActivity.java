package com.star.xstartactivityforresultandxpermission;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.star.xpermission.OnPermissionCallback;
import com.star.xpermission.PermissionSparseArray;
import com.star.xpermission.XPermission;
import com.star.xstartactivityforresult.ActivityResultInfo;
import com.star.xstartactivityforresult.OnResultCallback;
import com.star.xstartactivityforresult.XStartActivityForResult;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private OnPermissionCallback mStoragePermissionCallback;
    private OnPermissionCallback mCameraAndContactsPermissionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_test1).setOnClickListener(this);
        findViewById(R.id.tv_test2).setOnClickListener(this);
        findViewById(R.id.tv_test3).setOnClickListener(this);
        findViewById(R.id.tv_test4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_test1:
                if (mStoragePermissionCallback == null) {
                    mStoragePermissionCallback = new OnPermissionCallback() {
                        @Override
                        public void onPermissionGranted(int reqCode) {
                            Toast.makeText(MainActivity.this, "存储权限请求成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPermissionDenied(String deniedPermission, int reqCode) {
                            Toast.makeText(MainActivity.this, "存储权限请求失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void shouldShowRequestPermissionTip(String requestPermissionRationale, int reqCode) {
                            XPermission.showTipDialog(MainActivity.this, "设置权限", "提示用户权限的作用");
                        }
                    };
                }
                XPermission.permissionRequest(MainActivity.this, PermissionSparseArray.PERMISSION_STORAGE, mStoragePermissionCallback);
                break;
            case R.id.tv_test2:
                if (mCameraAndContactsPermissionCallback == null) {
                    mCameraAndContactsPermissionCallback = new OnPermissionCallback() {
                        @Override
                        public void onPermissionGranted(int reqCode) {
                            Toast.makeText(MainActivity.this, "相机权限和联系人权限请求成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPermissionDenied(String deniedPermission, int reqCode) {
                            Toast.makeText(MainActivity.this, "相机权限和联系人权限请求失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void shouldShowRequestPermissionTip(String requestPermissionRationale, int reqCode) {
                            XPermission.showTipDialog(MainActivity.this, "设置权限", "提示用户权限的作用");
                        }
                    };
                }
                String[] permissionArrays = PermissionSparseArray.mergePermissionArrays(PermissionSparseArray.PERMISSION_CAMERA, PermissionSparseArray.PERMISSION_CONTACTS);
                XPermission.permissionRequest(MainActivity.this,
                        PermissionSparseArray.PERMISSION_CAMERA + PermissionSparseArray.PERMISSION_CONTACTS,
                        permissionArrays, mCameraAndContactsPermissionCallback);
                break;
            case R.id.tv_test3:
                Intent intent = new Intent(this, OthersActivity.class);
                XStartActivityForResult.startActivityForResult(this, intent, 1, new OnResultCallback() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if (resultCode == RESULT_OK) {
                            Toast.makeText(MainActivity.this, "收到数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.tv_test4:
                Intent intent2 = new Intent(this, OthersActivity.class);
                XStartActivityForResult.startActivityForResultRx(this, intent2, 2)
                        .filter(new Predicate<ActivityResultInfo>() {
                            @Override
                            public boolean test(ActivityResultInfo activityResultInfo) throws Exception {
                                return activityResultInfo.resultCode == RESULT_OK;
                            }
                        })
                        .subscribe(new Consumer<ActivityResultInfo>() {
                            @Override
                            public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                                Toast.makeText(MainActivity.this, "收到数据", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }
}
