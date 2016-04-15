package com.staff.activities.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.staff.utils.ActivityManagerUtils;

import java.util.List;

/**
 *  基类
 */
public class AbsBaseActivity extends FragmentActivity {

    protected boolean isAppInBackground = false;
    protected boolean isDestroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManagerUtils.getActivityManager().add(this);
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppInForeground() {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (!isAppInBackground) {
            // app 从后台唤醒，进入前台
            isAppInBackground = false;
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (!isAppInForeground()) {
            //app 进入后台
            isAppInBackground = true ;//记录当前已经进入后台
        }
    }

    /**
     * 获取当前类名
     * @return 类名
     */
    protected String getTAG(){
        return this.getClass().getName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }

    @Override
    public void finish() {
        super.finish();
        ActivityManagerUtils.getActivityManager().removeActivity(this);
    }

}
