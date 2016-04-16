package momo.cn.edu.fjnu.videoclient.activity;

import android.support.v4.app.Fragment;

import momo.cn.edu.fjnu.videoclient.fragment.SingListFragment;

/**
 * 已签到列表
 * Created by GaoFei on 2016/3/30.
 */
public class SignListActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        return new SingListFragment();
    }
}
