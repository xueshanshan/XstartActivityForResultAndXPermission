package com.star.xstartactivityforresult;

import android.content.Intent;

/**
 * @author xueshanshan
 * @date 2018/9/18
 */
public class ActivityResultInfo {
    public int requestCode;
    public int resultCode;
    public Intent data;

    public ActivityResultInfo(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }
}
