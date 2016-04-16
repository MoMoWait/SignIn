package momo.cn.edu.fjnu.videoclient.activity;

import android.support.v4.app.Fragment;

import momo.cn.edu.fjnu.videoclient.fragment.ExerciseListFragment;

/**
 * 已创建活动列表
 * Created by GaoFei on 2016/3/30.
 */
public class ExerciseListActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        return new ExerciseListFragment();
    }
}
