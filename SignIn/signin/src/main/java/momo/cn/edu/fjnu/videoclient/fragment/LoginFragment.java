package momo.cn.edu.fjnu.videoclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import momo.cn.edu.fjnu.androidutils.base.BaseFragment;
import momo.cn.edu.fjnu.androidutils.utils.DialogUtils;
import momo.cn.edu.fjnu.androidutils.utils.JsonUtils;
import momo.cn.edu.fjnu.androidutils.utils.ResourceUtils;
import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.androidutils.utils.TextUtils;
import momo.cn.edu.fjnu.androidutils.utils.ValidUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.activity.MainActivity;
import momo.cn.edu.fjnu.videoclient.activity.RegisterActivity;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.db.UserService;
import momo.cn.edu.fjnu.videoclient.model.net.LoginTask;
import momo.cn.edu.fjnu.videoclient.pojo.User;

/**
 * 登录页面及相关处理
 * Created by GaoFei on 2016/3/7.
 */
@ContentView(R.layout.fragment_login)
public class LoginFragment extends BaseFragment{

    public final String TAG = LoginFragment.class.getSimpleName();

    @ViewInject(R.id.img_back)
    private ImageView mImgBack;
    @ViewInject(R.id.text_title)
    private TextView mTextTitle;
    @ViewInject(R.id.text_option)
    private TextView mTextOption;
    /**登陆按钮*/
    @ViewInject(R.id.btn_login)
    private TextView mBtnLogin;
    @ViewInject(R.id.edt_user_name)
    private EditText mEdtUserName;
    @ViewInject(R.id.edt_password)
    private EditText mEdtPassword;
    @ViewInject(R.id.img_user_head)
    private ImageView mImgUserHead;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void initView() {
        //隐藏按钮
        mImgBack.setVisibility(View.GONE);
        mTextOption.setText(ResourceUtils.getString(R.string.register));
        //设置标题
        mTextTitle.setText(ResourceUtils.getString(R.string.user_login));
        //处理用户头像为圆角
        //mImgUserHead.setImageBitmap(BitmapUtils.getCroppedBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.img_default_head)));
    }

    @Override
    public void initData() {
        String userName = StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_NAME);
        String password = StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_PASSWORD);
        String userInfo = StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO);
        if(!ValidUtils.isEmpty(userInfo)){
            try{
                JSONObject userObject = new JSONObject(userInfo);
                String headUrl = userObject.getString("head_photo");
                AQuery aQuery = new AQuery(mImgUserHead);
                aQuery.image(headUrl);
            }catch (Exception e){

            }
        }
        mEdtUserName.setText(userName);
        mEdtPassword.setText(password);
        if(StorageUtils.getDataFromSharedPreference(SharedKeys.IS_AUTO_LOGIN).equals(AppConst.StrBooleanValue.TRUE)){
            login();
        }
        //login();
    }

    @Override
    public void initEvent() {
        //登录页面
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //注册页面
        mTextOption.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RegisterActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_enter_right, R.anim.activity_enter_left);
            }
        });

        mEdtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AQuery aQuery = new AQuery(mImgUserHead);
                //从本地数据库查找用户头像URL
                List<User> userList =  new UserService().getAll(User.class);
                //是否设置用户头像
                boolean isSetting = false;
                if(userList != null && userList.size() > 0){
                    for(User user : userList){
                        if(s.toString().toLowerCase(Locale.US).equals(user.getUser_name().toLowerCase(Locale.US))){
                            //设置用户头像
                            aQuery.image(user.getHead_photo());
                            isSetting = true;
                            break;
                        }
                    }
                }
                //如果没有找到匹配的图像设置默认的图像
                if(!isSetting)
                    mImgUserHead.setImageResource(R.mipmap.img_default_head);
            }
        });

    }

    /**
     * 登录事件
     */
    public void login(){
        final String userName = mEdtUserName.getText().toString();
        String password = mEdtPassword.getText().toString();
        if(ValidUtils.isEmpty(userName) || ValidUtils.isEmpty(password)){
            Toast.makeText(getContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        DialogUtils.showLoadingDialog(getContext(), false);
        new LoginTask(new LoginTask.CallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    //存储用户信息
                    StorageUtils.saveDataToSharedPreference(SharedKeys.CURR_USER_INFO, jsonObject.getString("user"));
                    Log.i(TAG, "获取的用户信息:" + jsonObject.getString("user"));
                    User saveUser = (User)(JsonUtils.jsonToObject(User.class, jsonObject.getJSONObject("user")));
                    Log.i(TAG, "saveUser = " +saveUser);
                    UserService userService = new UserService();
                    if(userService.isExist(saveUser))
                        userService.update(saveUser);
                    else
                        userService.save(saveUser);
                } catch (JSONException e) {
                    Log.i(TAG, "json解析发生异常：" + e);
                    e.printStackTrace();
                }
                //获取登录的用户信息
                String currUserInfo = StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO);
                try{
                    JSONObject userObject = new JSONObject(currUserInfo);
                    int userType = userObject.getInt("type");
                    Set<String> tagSets = new HashSet<String>();
                    tagSets.add("" + userType);
                    JPushInterface.setTags(getContext(), tagSets, new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            Log.i(TAG, "设置标签结果：" + i);
                        }
                    });
                    //设置别名
                    JPushInterface.setAlias(getContext(), userObject.getString("id"), new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            Log.i(TAG, "设置别名结果：" + i);
                        }
                    });
                    userObject.getString("id");
                }catch (Exception e){

                }
                //存储用户名
                StorageUtils.saveDataToSharedPreference(SharedKeys.CURR_USER_NAME, mEdtUserName.getText().toString());
                //存储密码
                StorageUtils.saveDataToSharedPreference(SharedKeys.CURR_USER_PASSWORD, mEdtPassword.getText().toString());
                //存储自动登录标记
                StorageUtils.saveDataToSharedPreference(SharedKeys.IS_AUTO_LOGIN, AppConst.StrBooleanValue.TRUE);
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_enter_left, R.anim.activity_enter_right);
                DialogUtils.closeLoadingDialog();
                getActivity().finish();
            }

            @Override
            public void onFailed(AppException exception) {
                Toast.makeText(getContext(), exception.getErrorMsg(), Toast.LENGTH_SHORT).show();
                DialogUtils.closeLoadingDialog();
            }
        }).execute(userName, TextUtils.str2MD5(password));
    }
}
