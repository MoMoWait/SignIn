package momo.cn.edu.fjnu.videoclient.activity;

import android.support.v4.app.Fragment;

import momo.cn.edu.fjnu.videoclient.fragment.EditExerciseFragment;

/**
 * 编辑已建活动
 * Created by GaoFei on 2016/3/30.
 */
public class EditExerciseActivity extends SingleFragmentActivity{

    @Override
    public Fragment createFragment() {
        return new EditExerciseFragment();
    }
}
