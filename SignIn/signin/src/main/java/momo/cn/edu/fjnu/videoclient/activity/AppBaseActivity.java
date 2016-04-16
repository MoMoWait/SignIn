package momo.cn.edu.fjnu.videoclient.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;

import momo.cn.edu.fjnu.androidutils.base.BaseActivity;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.service.LocationListener;

/**
 * APP基本Activity,所有的Activity'都要继承此类
 * Created by GaoFei on 2016/3/8.
 */
public class AppBaseActivity extends BaseActivity {
    /**
     * 定位客户端
     */
    private AMapLocationClient mLocationClient;
    /**
     * 定位选项
     */
    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化定位
        initLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止定位
        mLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注意资源释放
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }

    /**
     * 初始化定位
     */
    public void initLocation() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位选项
        mLocationClient.setLocationOption(mLocationOption);
        // 设置是否需要显示地址信息
        mLocationOption.setNeedAddress(true);
        //设置定位间隔时间
        mLocationOption.setInterval(AppConst.LOCATE_TIME);
        //设置是否为单次定位
        mLocationOption.setOnceLocation(false);
        //设置定位监听器
        mLocationClient.setLocationListener(new LocationListener());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK ){
            finish();
            overridePendingTransition(R.anim.activity_exit_left, R.anim.activity_exit_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
