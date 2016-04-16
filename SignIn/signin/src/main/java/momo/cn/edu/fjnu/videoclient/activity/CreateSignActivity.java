package momo.cn.edu.fjnu.videoclient.activity;

import android.support.v4.app.Fragment;

import momo.cn.edu.fjnu.videoclient.fragment.CreateSignFragment;

/**
 * 新建签到页面
 * Created by GaoFei on 2016/3/30.
 */
public class CreateSignActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        return new CreateSignFragment();
    }
}
