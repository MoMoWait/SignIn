package momo.cn.edu.fjnu.videoclient.activity;

import android.support.v4.app.Fragment;

import momo.cn.edu.fjnu.videoclient.fragment.RegisterFragment;

/**
 * 注册页面
 * Created by GaoFei on 2016/3/24.
 */
public class RegisterActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        return new RegisterFragment();
    }
}
