package momo.cn.edu.fjnu.videoclient.activity;

import android.support.v4.app.Fragment;

import momo.cn.edu.fjnu.videoclient.fragment.InitFragment;

/**
 * 初始化Activity
 * Created by GaoFei on 2016/3/24.
 */
public class InitActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        return new InitFragment();
    }
}
