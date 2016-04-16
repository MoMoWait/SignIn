package momo.cn.edu.fjnu.videoclient.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import momo.cn.edu.fjnu.androidutils.base.BaseFragment;
import momo.cn.edu.fjnu.androidutils.utils.DeviceInfoUtils;
import momo.cn.edu.fjnu.androidutils.utils.JsonUtils;
import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.adapter.UserSignAdapter;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.net.GetSignRecordTask;
import momo.cn.edu.fjnu.videoclient.pojo.SignInfo;
import momo.cn.edu.fjnu.videoclient.pojo.SignUserRecord;
import momo.cn.edu.fjnu.videoclient.view.PersonDataView;

/**
 * 学生主页
 * Created by GaoFei on 2016/3/30.
 */
@ContentView(R.layout.fragment_student_main)
public class StudentMainFragment extends BaseFragment{

    @ViewInject(R.id.img_back)
    private ImageView mImgBack;
    @ViewInject(R.id.text_title)
    private TextView mTextTitle;
    @ViewInject(R.id.text_option)
    private TextView mTextOption;
    @ViewInject(R.id.list_sign_in)
    private ListView mListSign;
    private SlidingMenu mSlidMenu;
    private PersonDataView mPersonDataView;
    private GetSignRecordTask mRecordTask;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void initView() {

        AQuery imgHeadQuery = new AQuery(mImgBack);
        try {
            JSONObject userObject = new JSONObject(StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO));
            imgHeadQuery.image(userObject.getString("head_photo"));
        }catch (Exception e){

        }
        mTextOption.setVisibility(View.GONE);
        //设置标题
        mTextTitle.setText("已签到列表");

        mSlidMenu = new SlidingMenu(getContext());
        mSlidMenu.setMode(SlidingMenu.LEFT);
        mSlidMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidMenu.setShadowWidth(0);
        mSlidMenu.setBehindOffset(DeviceInfoUtils.getScreenWidth(getContext()) / 5);
        mSlidMenu.setFadeDegree(0.35f);
        mSlidMenu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
        mPersonDataView = new PersonDataView(getContext());
        mSlidMenu.setMenu(mPersonDataView);
    }

    @Override
    public void initData() {
        String userId = "-1";
        try {
            JSONObject userObject = new JSONObject(StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO));
            userId = userObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRecordTask = new GetSignRecordTask(new GetSignRecordTask.CallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                //signUserRecords
                List<SignUserRecord> signUserRecords = new ArrayList<SignUserRecord>();
                try{
                    signUserRecords = (List<SignUserRecord>)JsonUtils.arrayToList(SignUserRecord.class, jsonObject.getJSONArray("signUserRecords"));
                }catch (Exception e){

                }

                List<SignInfo> signInfos = new ArrayList<SignInfo>();
                for(SignUserRecord userRecord : signUserRecords){
                    SignInfo signInfo = new SignInfo();
                    signInfo.setId(userRecord.getId());
                    signInfo.setCourse(userRecord.getCourse());
                    signInfo.setEndTime(userRecord.getEndTime());
                    signInfo.setPersons(userRecord.getPersons());
                    signInfo.setStartTime(userRecord.getStartTime());
                    signInfos.add(signInfo);
                }

                UserSignAdapter userSignAdapter = new UserSignAdapter(getContext(), R.layout.adapter_all_signinfo, signInfos);
                mListSign.setAdapter(userSignAdapter);

            }

            @Override
            public void onFailed(AppException exception) {
                Toast.makeText(getContext(), exception.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecordTask.execute("" + userId);
    }

    @Override
    public void initEvent() {

        mSlidMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                mImgBack.setVisibility(View.GONE);
                //刷新页面
                //  mPersonDataView.refreshData();
            }
        });

        mSlidMenu.setOnCloseListener(new SlidingMenu.OnCloseListener() {
            @Override
            public void onClose() {
                mImgBack.setVisibility(View.VISIBLE);
            }
        });

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSlidMenu.isMenuShowing())
                    mSlidMenu.showMenu();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mPersonDataView.refreshData();
        String userInfo =StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO);
        try{
            JSONObject userObject = new JSONObject(userInfo);
            AQuery aQuery = new AQuery(mImgBack);
            aQuery.image(userObject.getString("head_photo"));

        }catch (Exception e){

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mRecordTask != null && mRecordTask.getStatus() == AsyncTask.Status.RUNNING)
            mRecordTask.cancel(true);
    }
}
