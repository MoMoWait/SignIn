package momo.cn.edu.fjnu.videoclient.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import momo.cn.edu.fjnu.androidutils.base.BaseDialog;
import momo.cn.edu.fjnu.androidutils.utils.ResourceUtils;
import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.androidutils.utils.ValidUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;

/**
 * 视频参数设置对话框
 * Created by GaoFei on 2016/3/17.
 */
public class VideoSettingDialog extends BaseDialog{
    public final String TAG = VideoSettingDialog.class.getSimpleName();
    private Context mContext;
    private View mView;

    @ViewInject(R.id.btn_ok)
    private Button mBtnOk;

    @ViewInject(R.id.btn_cancel)
    private Button mBtnCancel;

    @ViewInject(R.id.edt_video_frame_rate)
    private EditText mEdtVideoFrame;

    @ViewInject(R.id.edt_video_bitrate)
    private EditText mEdtVideoBitrate;

    @ViewInject(R.id.spn_video_size)
    private Spinner mSpnVideoSize;

    public VideoSettingDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_video_setting, null, false);
        x.view().inject(this, mView);
        setContentView(mView);
    }

    @Override
    public void initData() {
        mEdtVideoFrame.setText(StorageUtils.getDataFromSharedPreference(SharedKeys.VIDEO_FRAME_RATE));
        mEdtVideoBitrate.setText(StorageUtils.getDataFromSharedPreference(SharedKeys.VIDEO_BITRATE));
        String videoWidth = StorageUtils.getDataFromSharedPreference(SharedKeys.VIDEO_WIDTH);
        String videoHeight = StorageUtils.getDataFromSharedPreference(SharedKeys.VIDEO_HEIGHT);
        int selectPosition = 0;
        List<String> sizeList = new ArrayList<>();
        try{
            JSONArray sizeArray = new JSONArray(StorageUtils.getDataFromSharedPreference(SharedKeys.CAMERA_SIZES));
            for(int i = 0; i < sizeArray.length(); ++i){
                JSONObject sizeObject = sizeArray.getJSONObject(i);
                if(sizeObject.getString("width").equals(videoWidth) && sizeObject.getString("height").equals(videoHeight))
                    selectPosition = i;
                sizeList.add(sizeObject.getString("width") + "*" + sizeObject.getString("height"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, sizeList);
        mSpnVideoSize.setAdapter(adapter);
        Log.i(TAG, "选中位置:" + selectPosition);
        mSpnVideoSize.setSelection(selectPosition);
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
                String frameRate = mEdtVideoFrame.getText().toString();
                String videoBitrate = mEdtVideoBitrate.getText().toString();
                if(ValidUtils.isEmpty(frameRate) || ValidUtils.isEmpty(videoBitrate)){
                    Toast.makeText(mContext, ResourceUtils.getString(R.string.input_all), Toast.LENGTH_SHORT).show();
                    return;
                }
                StorageUtils.saveDataToSharedPreference(SharedKeys.VIDEO_WIDTH, mSpnVideoSize.getSelectedItem().toString().split("\\*")[0]);
                StorageUtils.saveDataToSharedPreference(SharedKeys.VIDEO_HEIGHT, mSpnVideoSize.getSelectedItem().toString().split("\\*")[1]);
                StorageUtils.saveDataToSharedPreference(SharedKeys.VIDEO_FRAME_RATE, mEdtVideoFrame.getText().toString());
                StorageUtils.saveDataToSharedPreference(SharedKeys.VIDEO_BITRATE, mEdtVideoBitrate.getText().toString());
                dismiss();
            }
        });
    }
}
