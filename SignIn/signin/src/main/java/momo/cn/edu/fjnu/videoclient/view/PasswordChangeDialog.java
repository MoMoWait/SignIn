package momo.cn.edu.fjnu.videoclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import momo.cn.edu.fjnu.androidutils.base.BaseDialog;
import momo.cn.edu.fjnu.androidutils.utils.DeviceInfoUtils;
import momo.cn.edu.fjnu.androidutils.utils.DialogUtils;
import momo.cn.edu.fjnu.androidutils.utils.ResourceUtils;
import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.androidutils.utils.TextUtils;
import momo.cn.edu.fjnu.androidutils.utils.ValidUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.net.PersonDataChangeTask;

/**
 * 密码修改对话框
 * Created by GaoFei on 2016/3/27.
 */
public class PasswordChangeDialog extends BaseDialog {
    @ViewInject(R.id.edit_origin_password)
    private EditText mEditOrginPassword;
    @ViewInject(R.id.edit_new_password)
    private EditText mEditNewPassword;
    @ViewInject(R.id.edit_confirm_password)
    private EditText mEditConfirmPassword;
    @ViewInject(R.id.btn_ok)
    private Button mBtnOk;
    @ViewInject(R.id.btn_cancel)
    private Button mBtnCancel;
    private Context mContext;
    private View mView;

    public PasswordChangeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_password_change, null, false);
        setContentView(mView, new ViewGroup.LayoutParams((int) (DeviceInfoUtils.getScreenWidth(getContext()) * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT));
        x.view().inject(this, mView);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidUtils.isEmpty(mEditOrginPassword.getText().toString()) ||
                        ValidUtils.isEmpty(mEditNewPassword.getText().toString()) ||
                        ValidUtils.isEmpty(mEditConfirmPassword.getText().toString())) {
                    Toast.makeText(mContext, ResourceUtils.getString(R.string.input_all), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!mEditConfirmPassword.getText().toString().equals(mEditNewPassword.getText().toString())){
                    Toast.makeText(mContext, "新密码和确认密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                String orginPassword = mEditOrginPassword.getText().toString();
                String md5Password = TextUtils.str2MD5(orginPassword);
                String userInfo = StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO);
                String userId = "-1";
                try{
                    JSONObject userObject = new JSONObject(userInfo);
                    userId = userObject.getString("id");
                    if(!md5Password.equals(userObject.getString("password"))){
                        Toast.makeText(mContext, "原密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception e){

                }

                DialogUtils.showLoadingDialog(getContext(), false);
                new PersonDataChangeTask(new PersonDataChangeTask.CallBack() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try{
                            JSONObject userObject = jsonObject.getJSONObject("user");
                            StorageUtils.saveDataToSharedPreference(SharedKeys.CURR_USER_INFO, userObject.toString());
                        }catch (Exception e){

                        }
                        DialogUtils.closeLoadingDialog();
                        Toast.makeText(getContext(), "密码修改成功", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailed(AppException exception) {
                        DialogUtils.closeLoadingDialog();
                        Toast.makeText(mContext, exception.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                }).execute(userId, "password", TextUtils.str2MD5(mEditNewPassword.getText().toString()));
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
