package momo.cn.edu.fjnu.videoclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import momo.cn.edu.fjnu.androidutils.base.BaseDialog;
import momo.cn.edu.fjnu.androidutils.utils.ResourceUtils;
import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.androidutils.utils.ValidUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;


/**
 * 视频服务器参数设置
 * Created by GaoFei on 2016/3/17.
 */
public class VideoServerDialog extends BaseDialog{

    private Context mContext;
    private View mView;

    @ViewInject(R.id.btn_ok)
    private Button mBtnOk;

    @ViewInject(R.id.btn_cancel)
    private Button mBtnCancel;

    @ViewInject(R.id.edt_server_ip)
    private EditText mEdtServerIp;

    @ViewInject(R.id.edt_user_name)
    private EditText mEdtUserName;

    @ViewInject(R.id.edt_password)
    private EditText mEdtPassword;

    public VideoServerDialog(Context context){
        super(context);
        this.mContext = context;
    }

    @Override
    public void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_video_server, null, false);
        setContentView(mView);
        x.view().inject(this, mView);
    }

    @Override
    public void initData() {
        mEdtServerIp.setText(StorageUtils.getDataFromSharedPreference(SharedKeys.SERVER_IP));
        mEdtUserName.setText(StorageUtils.getDataFromSharedPreference(SharedKeys.SERVER_USER_NAME));
        mEdtPassword.setText(StorageUtils.getDataFromSharedPreference(SharedKeys.SERVER_PASSWORD));
    }

    @Override
    public void initEvent() {
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverIp = mEdtServerIp.getText().toString().trim();
                String userName = mEdtUserName.getText().toString().trim();
                String password = mEdtPassword.getText().toString();
                if(ValidUtils.isEmpty(serverIp) || ValidUtils.isEmpty(userName) || ValidUtils.isEmpty(password)){
                    Toast.makeText(getContext(), ResourceUtils.getString(R.string.input_all), Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //保存至本地
                    StorageUtils.saveDataToSharedPreference(SharedKeys.SERVER_IP, serverIp);
                    StorageUtils.saveDataToSharedPreference(SharedKeys.SERVER_USER_NAME, userName);
                    StorageUtils.saveDataToSharedPreference(SharedKeys.SERVER_PASSWORD, password);
                    dismiss();
                }

            }
        });
    }
}
