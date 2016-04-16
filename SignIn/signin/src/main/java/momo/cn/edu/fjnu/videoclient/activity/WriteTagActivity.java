package momo.cn.edu.fjnu.videoclient.activity;

import android.support.v4.app.Fragment;

import momo.cn.edu.fjnu.videoclient.fragment.WriteTagFragment;

/**
 * 写标签页面
 * Created by GaoFei on 2016/3/30.
 */
public class WriteTagActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        return new WriteTagFragment();
    }
}
