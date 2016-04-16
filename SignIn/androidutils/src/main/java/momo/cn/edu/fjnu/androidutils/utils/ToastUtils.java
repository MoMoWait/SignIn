package momo.cn.edu.fjnu.androidutils.utils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import momo.cn.edu.fjnu.androidutils.R;
import momo.cn.edu.fjnu.androidutils.data.CommonValues;

/**
 * Toast提示工具
 * Created by GaoFei on 2016/1/3.
 */
public class ToastUtils {
    /**
     * 显示Toast信息
     * @param content   Toast显示的内容
     * @param textSize  显示的文字大小
     */
    public static void showToast(String content,float textSize){
        CommonValues.appToast.setText(content);
        LinearLayout toastLayout = (LinearLayout) CommonValues.appToast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(textSize);
        CommonValues.appToast.show();
    }

    /**
     * 显示Toast信息
     * @param content   Toast显示的内容
     * @param textSize  显示的文字大小
     * @param textColor 文字演示
     */
    public static void showToast(String content,float textSize,int textColor){
        CommonValues.appToast.setText(content);
        LinearLayout toastLayout = (LinearLayout) CommonValues.appToast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(textSize);
        toastTV.setTextColor(textColor);
        CommonValues.appToast.show();
    }


    /**
     * 显示Toast消息
     * @param content
     */
    public static void showToast(String content){
        LinearLayout toastLayout = (LinearLayout) CommonValues.appToast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(ResourceUtils.getDimension(R.dimen.common_toast_text_size));
        CommonValues.appToast.setText(content);
        CommonValues.appToast.setDuration(Toast.LENGTH_SHORT);
        CommonValues.appToast.show();
    }

    /**
     * 显示Toast信息
     */
    public static void showToast(String content,int time){
        LinearLayout toastLayout = (LinearLayout) CommonValues.appToast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(ResourceUtils.getDimension(R.dimen.common_toast_text_size));
        CommonValues.appToast.setText(content);
        CommonValues.appToast.setDuration(time);
        CommonValues.appToast.show();
    }

}
