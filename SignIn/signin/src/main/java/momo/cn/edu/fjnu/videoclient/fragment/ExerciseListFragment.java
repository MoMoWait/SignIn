package momo.cn.edu.fjnu.videoclient.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import momo.cn.edu.fjnu.androidutils.base.BaseFragment;
import momo.cn.edu.fjnu.androidutils.utils.JsonUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.activity.CreateSignActivity;
import momo.cn.edu.fjnu.videoclient.activity.SignInActivity;
import momo.cn.edu.fjnu.videoclient.adapter.AllSignInfosAdapter;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.net.GetAllSignInfoTask;
import momo.cn.edu.fjnu.videoclient.pojo.SignInfo;

/**
 * 活动列表
 * Created by GaoFei on 2016/3/30.
 */
@ContentView(R.layout.fragment_exercise_list)
public class ExerciseListFragment extends BaseFragment{

    @ViewInject(R.id.img_back)
    private ImageView mImgBack;

    @ViewInject(R.id.text_title)
    private TextView mTextTitle;

    @ViewInject(R.id.text_option)
    private TextView mTextOption;

    @ViewInject(R.id.btn_add)
    private Button mBtnAdd;
    @ViewInject(R.id.list_sign_info)
    private ListView mListSignInfo;

    private GetAllSignInfoTask mSignInfoTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }


    @Override
    public void initView() {
        mTextTitle.setText("已建活动");
        mTextOption.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        //refreshData();
    }

    @Override
    public void initEvent() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateSignActivity.class));
            }
        });

        mListSignInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int signinfo_id = ((SignInfo) parent.getAdapter().getItem(position)).getId();
                Intent intent = new Intent(getContext(), SignInActivity.class);
                intent.putExtra(AppConst.SIGNINFO_ID, signinfo_id);
                //startActivityForResult(intent, 1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshData();
    }

    /**
     * 刷新数据
     */
    public void refreshData(){
        //获取所有已创建的签到信息
        if(mSignInfoTask != null && mSignInfoTask.getStatus() == AsyncTask.Status.RUNNING)
            mSignInfoTask.cancel(true);
        mSignInfoTask =  new GetAllSignInfoTask(new GetAllSignInfoTask.CallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    List<SignInfo> signInfos = (List<SignInfo>)(JsonUtils.arrayToList(SignInfo.class, jsonObject.getJSONArray("signinfos")));
                    AllSignInfosAdapter allSignInfosAdapter = new AllSignInfosAdapter(getContext(), R.layout.adapter_all_signinfo, signInfos);
                    mListSignInfo.setAdapter(allSignInfosAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(AppException exception) {
                Toast.makeText(getContext(), exception.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
        mSignInfoTask.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mSignInfoTask != null && mSignInfoTask.getStatus() == AsyncTask.Status.RUNNING)
            mSignInfoTask.cancel(true);
    }
}
