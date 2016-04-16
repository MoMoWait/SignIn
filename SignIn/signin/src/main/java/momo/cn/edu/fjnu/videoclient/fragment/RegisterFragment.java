package momo.cn.edu.fjnu.videoclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import momo.cn.edu.fjnu.androidutils.base.BaseFragment;
import momo.cn.edu.fjnu.androidutils.utils.DialogUtils;
import momo.cn.edu.fjnu.androidutils.utils.ResourceUtils;
import momo.cn.edu.fjnu.androidutils.utils.TextUtils;
import momo.cn.edu.fjnu.androidutils.utils.ValidUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.net.RegisterTask;

/**
 * 注册页面
 * Created by GaoFei on 2016/3/24.
 */
@ContentView(R.layout.fragment_register)
public class RegisterFragment extends BaseFragment{

    @ViewInject(R.id.img_back)
    private ImageView mImgBack;
    @ViewInject(R.id.text_title)
    private TextView mTextTitle;
    @ViewInject(R.id.text_option)
    private TextView mTextOption;
    @ViewInject(R.id.btn_register)
    private Button mBtnRegister;
    @ViewInject(R.id.edit_user_name)
    private EditText mEditUserName;
    @ViewInject(R.id.edit_password)
    private EditText mEditPassword;
    @ViewInject(R.id.edit_confirm_password)
    private EditText mEditConfirmPassword;
    @ViewInject(R.id.edit_mail)
    private EditText mEditMail;
    @ViewInject(R.id.edit_nick_name)
    private EditText mEditNickName;
    @ViewInject(R.id.edit_phone_number)
    private EditText mEditPhoneNumber;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void initView() {
        mTextTitle.setText(ResourceUtils.getString(R.string.user_register));
        mTextOption.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.activity_exit_left, R.anim.activity_exit_right);
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mEditUserName.getText().toString();
                String password = mEditPassword.getText().toString();
                String confirmPassword = mEditConfirmPassword.getText().toString();
                String mail = mEditMail.getText().toString();
                String nickName = mEditNickName.getText().toString();
                String phoneNumber = mEditPhoneNumber.getText().toString();
                if(ValidUtils.isEmpty(userName) || ValidUtils.isEmpty(password) || ValidUtils.isEmpty(mail) || ValidUtils.isEmpty(nickName)
                        || ValidUtils.isEmpty(phoneNumber)){
                    Toast.makeText(getContext(), ResourceUtils.getString(R.string.input_all), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(confirmPassword)){
                    Toast.makeText(getContext(), ResourceUtils.getString(R.string.password_not_same), Toast.LENGTH_SHORT).show();
                    return;
                }
                DialogUtils.showLoadingDialog(getContext(), false);
                new RegisterTask(new RegisterTask.CallBack() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        Toast.makeText(getContext(), ResourceUtils.getString(R.string.register_succ), Toast.LENGTH_SHORT).show();
                        DialogUtils.closeLoadingDialog();
                        getActivity().finish();
                    }

                    @Override
                    public void onFailed(AppException exception) {
                        Toast.makeText(getContext(), exception.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        DialogUtils.closeLoadingDialog();
                    }
                }).execute(userName, TextUtils.str2MD5(password), mail, nickName, phoneNumber);
            }
        });
    }
}
