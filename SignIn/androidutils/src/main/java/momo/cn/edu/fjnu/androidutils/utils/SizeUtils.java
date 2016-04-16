package momo.cn.edu.fjnu.androidutils.utils;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 尺寸转换工具
 * Created by GaoFei on 2016/1/3.
 */
public class SizeUtils {
    private SizeUtils(){
    }

    /**
     * 将dp转化成像素
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(@NonNull Context context, float dpValue){
       return (int)(DeviceInfoUtils.getDenstity(context) * dpValue);
    }

    /**
     * 将sp转化成像素
     * @param context
     * @param spValue
     * @return
     */
    public float sp2px(@NonNull Context context, float spValue){
        return DeviceInfoUtils.getScaleDenstity(context) * spValue;
    }

    /**
     * 将像素转化成dp
     * @return
     */
    public float px2dp(@NonNull Context context, int pxValue){
        return pxValue / DeviceInfoUtils.getDenstity(context);
    }

}
