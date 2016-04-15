package com.staff.activities.base;

import android.os.Bundle;
import android.view.View;

/**
 * 名称: BaseActivity <br/>
 * 描述: 自定义基类Activity <br/>
 */
public abstract class BaseActivity extends AbsBaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
