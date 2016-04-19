package com.staff.activities.base;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.staff.main.R;
import com.staff.utils.StringUtils;

import java.io.PrintStream;

/**
 * 名称: BaseActivity <br/>
 * 描述: 自定义基类Activity <br/>
 */
public abstract class BaseActivity extends AbsBaseActivity implements View.OnClickListener {

    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup rootView = (ViewGroup)LayoutInflater.from(this).inflate(R.layout.activity_base,null,false);
        setContentView(rootView);

        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);


        setTitleLeftView(null);
        setTitleCenterView(null);
        setTitleRightView(null);
        try {
            View view = LayoutInflater.from(this).inflate(getContentLayoutID(),rootView,false);
            if(view!=null)rootView.addView(view);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void setTitleLeftView(View view){
        Button btn_title_back = (Button) findViewById(R.id.btn_title_back);
        if(view == null) {
            btn_title_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else{
            btn_title_back.setVisibility(View.GONE);

            RelativeLayout layout = (RelativeLayout)findViewById(R.id.rLayout_base_title);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            layout.addView(view, params);
        }
    }

    protected void setTitleRightView(View view){
       if(view != null){
           RelativeLayout layout = (RelativeLayout)findViewById(R.id.rLayout_base_title);
           RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
           params.addRule(RelativeLayout.CENTER_VERTICAL);
           params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
           view.bringToFront();
           layout.addView(view, params);
       }
    }

    protected void setTitleCenterView(View view){
        if(view == null) {
            setTitle("测试");
        }else{
            TextView tv_base_title = (TextView) findViewById(R.id.tv_base_title);
            tv_base_title.setVisibility(View.GONE);

            RelativeLayout layout = (RelativeLayout)findViewById(R.id.rLayout_base_title);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            layout.addView(view, params);
        }
    }

    protected void setTitle(String title){
        if(!StringUtils.isEmpty(title)){
            TextView tv_base_title = (TextView)findViewById(R.id.tv_base_title);
            if(tv_base_title.getVisibility() == View.VISIBLE )tv_base_title.setText(title);
        }
    }

    /**
     * 自定义除title以外的view id
     * @return
     */
    public abstract int getContentLayoutID();


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
