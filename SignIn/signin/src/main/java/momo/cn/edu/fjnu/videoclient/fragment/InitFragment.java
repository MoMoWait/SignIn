package momo.cn.edu.fjnu.videoclient.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.concurrent.TimeUnit;

import momo.cn.edu.fjnu.androidutils.base.BaseFragment;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.activity.LoginActivity;
import momo.cn.edu.fjnu.videoclient.data.AppConst;

/**
 * 初始化封面
 * Created by GaoFei on 2016/3/24.
 */
@ContentView(R.layout.fragment_init)
public class InitFragment extends BaseFragment{

    private InitTask mInitTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mInitTask = new InitTask();
        mInitTask.execute();
    }

    @Override
    public void initEvent() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mInitTask.getStatus() == AsyncTask.Status.RUNNING)
            mInitTask.cancel(true);
    }

    public class InitTask extends AsyncTask<String, Integer, Integer>{
        @Override
        protected Integer doInBackground(String... params) {
            try {
                TimeUnit.MILLISECONDS.sleep(AppConst.INIT_TIME);
                return AppConst.RetResult.SUCC;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return AppConst.RetResult.FAILED;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
           if(result == AppConst.RetResult.SUCC){
               startActivity(new Intent(getContext(), LoginActivity.class));
               getActivity().overridePendingTransition(R.anim.activity_enter_right, R.anim.activity_enter_left);
               getActivity().finish();
           }
        }
    }


}
