package momo.cn.edu.fjnu.videoclient.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import momo.cn.edu.fjnu.androidutils.utils.JsonUtils;
import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.activity.PersonDataChangeActivity;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;
import momo.cn.edu.fjnu.videoclient.pojo.User;
import momo.cn.edu.fjnu.videoclient.utils.CommonUtils;

/**
 * 个人资料页面
 * Created by GaoFei on 2016/3/27.
 */
public class PersonDataView extends LinearLayout{
    private Context mContext;
    private View mView;
    @ViewInject(R.id.img_user_head)
    private ImageView mImgUserHead;
    @ViewInject(R.id.text_user_name)
    private TextView mTextUserName;
    @ViewInject(R.id.text_mail)
    private TextView mTextMail;
    @ViewInject(R.id.text_nick_name)
    private TextView mTextNickName;
    @ViewInject(R.id.text_phone_number)
    private TextView mTextPhoneNumber;
    @ViewInject(R.id.text_exit)
    private TextView mTextExit;
    @ViewInject(R.id.text_change_person_data)
    private TextView mTextChangePersonData;
    public PersonDataView(Context context) {
        super(context);
        this.mContext = context;
        mView =  LayoutInflater.from(mContext).inflate(R.layout.view_person_data, null, false);
        x.view().inject(this, mView);
        initView();
        initData();
        initEvent();
    }

    public void initView(){
        addView(mView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void initData(){
        refreshData();
    }

    public void initEvent(){
        mTextExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageUtils.saveDataToSharedPreference(SharedKeys.IS_AUTO_LOGIN, AppConst.StrBooleanValue.FALSE);
                CommonUtils.restartApp();
            }
        });

        mTextChangePersonData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PersonDataChangeActivity.class));
                ((Activity)mContext).overridePendingTransition(R.anim.activity_enter_left, R.anim.activity_enter_right);
            }
        });
    }


    /**
     * 刷新数据
     */
    public void refreshData(){
        String userInfo = StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO);
        try{
            User user = (User)(JsonUtils.jsonToObject(User.class, new JSONObject(userInfo)));
            AQuery aQuery = new AQuery(mImgUserHead);
            aQuery.image(user.getHead_photo());
            mTextUserName.setText(user.getNick_name());
            mTextMail.setText("邮箱:" + user.getMail());
            mTextNickName.setText("学号:" + user.getUser_name());
            mTextPhoneNumber.setText("手机号码:" + user.getPhone_number());
        }catch (Exception e){

        }
    }
}
