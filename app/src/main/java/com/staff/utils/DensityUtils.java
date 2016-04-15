package com.staff.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

public class DensityUtils {
    private DensityUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

	public static int getScreenWidth(Context context){
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;     // 屏幕宽度（像素）
		return width;
	}
	
	public static int getScreenHeight(Context context){
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		int height = metric.heightPixels;     // 屏幕宽度（像素）
		return height;
	}
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
	public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    
    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {  
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
		try {
          c = Class.forName("com.android.internal.R$dimen");
          obj = c.newInstance();
          field = c.getField("status_bar_height");
          x = Integer.parseInt(field.get(obj).toString());
          sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}  
		 
        return sbar;  
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue, Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * fontScale + 0.5f);
    }

}

