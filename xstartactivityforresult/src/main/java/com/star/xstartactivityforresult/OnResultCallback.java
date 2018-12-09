package com.star.xstartactivityforresult;

import android.content.Intent;

/**
 * @author xueshanshan
 * @date 2018/12/9
 */
public interface OnResultCallback {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
