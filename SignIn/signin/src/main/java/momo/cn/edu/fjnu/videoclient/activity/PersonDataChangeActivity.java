package momo.cn.edu.fjnu.videoclient.activity;


import android.support.v4.app.Fragment;

import momo.cn.edu.fjnu.videoclient.fragment.PersonDataChangeFragment;

/**
 * 个人资料修改页面
 * Created by GaoFei on 2016/3/27.
 */
public class PersonDataChangeActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        return new PersonDataChangeFragment();
    }
}
