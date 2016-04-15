package com.staff.activities.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * 名称: BaseActivity <br/>
 * 描述: 自定义基类Activity <br/>
 */
public abstract class BaseActivity extends AbsBaseActivity implements View.OnClickListener {

    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    /**
     * 提示框
     * @param str
     */
    public void showTip(final String str) {
        if (!TextUtils.isEmpty(str)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(str);
                    mToast.show();
                }
            });
        }
    }

    /**
     * 取消提示框
     */
    public void cancelShowTip() {
        if (mToast!=null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.cancel();
                }
            });
        }
    }
}
