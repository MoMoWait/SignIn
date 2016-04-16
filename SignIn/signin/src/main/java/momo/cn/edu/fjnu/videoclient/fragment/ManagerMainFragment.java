package momo.cn.edu.fjnu.videoclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import momo.cn.edu.fjnu.androidutils.base.BaseFragment;
import momo.cn.edu.fjnu.androidutils.utils.DeviceInfoUtils;
import momo.cn.edu.fjnu.androidutils.utils.ResourceUtils;
import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.activity.CreateSignActivity;
import momo.cn.edu.fjnu.videoclient.activity.ExerciseListActivity;
import momo.cn.edu.fjnu.videoclient.activity.SignListActivity;
import momo.cn.edu.fjnu.videoclient.activity.WriteTagActivity;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;
import momo.cn.edu.fjnu.videoclient.view.PersonDataView;

/**
 * 管理员主目录
 * Created by GaoFei on 2016/3/30.
 */

@ContentView(R.layout.fragment_manager_main)
public class ManagerMainFragment extends BaseFragment{

    @ViewInject(R.id.img_back)
    private ImageView mImgBack;
    @ViewInject(R.id.text_title)
    private TextView mTextTitle;
    @ViewInject(R.id.text_option)
    private TextView mTextOption;
    @ViewInject(R.id.btn_write_tag)
    private Button mBtnWriteTag;
    @ViewInject(R.id.btn_create_activity)
    private Button mBtnCreateActivity;
    @ViewInject(R.id.btn_sign_persons)
    private Button mBtnSignPersons;
    @ViewInject(R.id.btn_activities)
    private Button mBtnActivities;
    private SlidingMenu mSlidMenu;
    private PersonDataView mPersonDataView;
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
        mTextTitle.setText(ResourceUtils.getString(R.string.main_menu));
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

        mBtnWriteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WriteTagActivity.class));
            }
        });

        mBtnCreateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateSignActivity.class));
            }
        });

        mBtnSignPersons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignListActivity.class));
            }
        });

        mBtnActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ExerciseListActivity.class));
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
}
