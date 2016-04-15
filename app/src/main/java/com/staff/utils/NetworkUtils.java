package com.staff.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;


public class NetworkUtils {

    /**
     * 判断网络是否畅通
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (null == context) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return (info != null && info.isAvailable() && info.isConnected());
        }
        return false;
    }


    /**
     * 是否网络可用
     *
     * @param context
     *            上下文
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo network = cm.getActiveNetworkInfo();
        if (network != null) {
            return network.isConnected() || network.isAvailable();
        } else {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState();
            return wifiManager.isWifiEnabled()
                    && wifiState.equals(NetworkInfo.State.CONNECTED);
        }
    }
    /**
     * 是否网络可用
     *
     * @param context
     *            上下文
     * @return
     */
    public static boolean isNetworkConnected(Context context,boolean showToast) {
        boolean connected = isNetworkConnected(context);
        if (showToast&&!connected) {
           // ToastUtils.showCenterToast(context.getResources().getString(R.string.network_no_connection_tip),context);
        }
        return connected;
    }

}
