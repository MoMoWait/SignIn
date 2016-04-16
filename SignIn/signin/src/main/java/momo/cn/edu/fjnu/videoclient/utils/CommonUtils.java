package momo.cn.edu.fjnu.videoclient.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import momo.cn.edu.fjnu.androidutils.data.CommonValues;
import momo.cn.edu.fjnu.androidutils.utils.ActivityExitUtils;
import momo.cn.edu.fjnu.videoclient.activity.LoginActivity;

/**
 * 重启APP
 * Created by GaoFei on 2016/3/28.
 */
public class CommonUtils {
    /**重启APP*/
    public  static  void restartApp(){
        //重启APP
        Intent intent = new Intent(CommonValues.application, LoginActivity.class);
        PendingIntent restartIntent = PendingIntent.getActivity(CommonValues.application, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager mgr = (AlarmManager)CommonValues.application.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
        // 退出程序
        ActivityExitUtils.exitAllActivitys();
        System.exit(0);
    }

}
