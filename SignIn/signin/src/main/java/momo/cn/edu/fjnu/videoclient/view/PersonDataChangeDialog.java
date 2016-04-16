package momo.cn.edu.fjnu.videoclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import momo.cn.edu.fjnu.androidutils.base.BaseDialog;
import momo.cn.edu.fjnu.androidutils.utils.DeviceInfoUtils;
import momo.cn.edu.fjnu.androidutils.utils.DialogUtils;
import momo.cn.edu.fjnu.androidutils.utils.ResourceUtils;
import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.androidutils.utils.ValidUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.net.PersonDataChangeTask;

/**
 * 个人资料修改对话框
 * Created by GaoFei on 2016/3/28.
 */
public class PersonDataChangeDialog extends BaseDialog{

    public interface DialogChangeListener{
         void onContentChange(TextView textDialogTitle, EditText editContent);
         void onChangeSuccess(String nValue);
    }
    private DialogChangeListener mListener;
    private Context mContext;
    private View mView;
    @ViewInject(R.id.text_dialog_title)
    private TextView mTextDialogTitle;

    @ViewInject(R.id.edit_value)
    private EditText mEditValue;

    @ViewInject(R.id.btn_ok)
    private Button mBtnOk;

    @ViewInject(R.id.btn_cancel)
    private Button mBtnCancel;

    private String mKey;

    private String mOriginValue;
    public PersonDataChangeDialog(Context context, String key, DialogChangeListener listener){
        super(context);
        this.mContext = context;
        this.mListener = listener;
        this.mKey = key;
    }

    @Override
    public void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_person_data_change, null ,false);
        setContentView(mView, new ViewGroup.LayoutParams((int)(DeviceInfoUtils.getScreenWidth(getContext()) * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT));
        x.view().inject(this, mView);
    }

    @Override
    public void initData() {
        mListener.onContentChange(mTextDialogTitle, mEditValue);
        mOriginValue = mEditValue.getText().toString();
    }

    @Override
    public void initEvent() {
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidUtils.isEmpty(mEditValue.getText().toString())){
                    Toast.makeText(getContext(), ResourceUtils.getString(R.string.input_all), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mOriginValue.equals(mEditValue.getText().toString())){
                    dismiss();
                    return;
                }
                DialogUtils.showLoadingDialog(mContext, false);
                String userInfo = StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO);
                String userId = "-1";
                try{
                    JSONObject userObject = new JSONObject(userInfo);
                    userId = userObject.getString("id");
                }catch (Exception e){

                }
                 new PersonDataChangeTask(new PersonDataChangeTask.CallBack() {
                     @Override
                     public void onSuccess(JSONObject jsonObject) {
                         DialogUtils.closeLoadingDialog();
                         try{
                             JSONObject userObject = jsonObject.getJSONObject("user");
                             StorageUtils.saveDataToSharedPreference(SharedKeys.CURR_USER_INFO, userObject.toString());
                         }catch (Exception e){

                         }
                         mListener.onChangeSuccess(mEditValue.getText().toString());
                         Toast.makeText(mContext, mTextDialogTitle.getText().toString() + "成功", Toast.LENGTH_SHORT).show();
                         dismiss();
                     }

                     @Override
                     public void onFailed(AppException exception) {
                         DialogUtils.closeLoadingDialog();
                         Toast.makeText(mContext, exception.getErrorMsg(), Toast.LENGTH_SHORT).show();
                     }
                 }).execute(userId, mKey, mEditValue.getText().toString());
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
