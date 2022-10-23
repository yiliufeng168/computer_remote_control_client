package net.yiliufeng.windows_control.MyUtils;

import android.content.Context;

public class DensityUtil {
    /**
     * dp转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale+0.5f);
    }

    /**
     * px转dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context,float pxValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);
    }

    /**
     * px转换为sp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context,float pxValue){
        final float scale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/scale+0.5f);
    }

    /**
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context,float spValue){
        final float scale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*scale+0.5f);
    }


}
