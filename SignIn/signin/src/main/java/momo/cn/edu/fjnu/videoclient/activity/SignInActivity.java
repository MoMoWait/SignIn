package momo.cn.edu.fjnu.videoclient.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

import momo.cn.edu.fjnu.androidutils.base.BaseActivity;
import momo.cn.edu.fjnu.androidutils.utils.DialogUtils;
import momo.cn.edu.fjnu.androidutils.utils.JsonUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.adapter.SignRecordAdapter;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.net.GetSignRecordTask;
import momo.cn.edu.fjnu.videoclient.model.net.SignRecordTask;
import momo.cn.edu.fjnu.videoclient.pojo.SignRecord;

/**
 * NFC签到页面
 * Created by GaoFei on 2016/4/5.
 */
public class SignInActivity extends BaseActivity{
    NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;
    @ViewInject(R.id.img_back)
    private ImageView mImgBack;
    @ViewInject(R.id.text_title)
    private TextView mTextTitle;
    @ViewInject(R.id.text_option)
    private TextView mTextOption;
    @ViewInject(R.id.list_sign_user)
    private ListView mListSignUser;
    private int mSignInfoId;
    private GetSignRecordTask mRecordTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);
        IntentFilter tagDiscovered = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        intentFilters = new IntentFilter[]{tagDiscovered};
        x.view().inject(this);
        initView();
        initData();
        initEvent();
    }

    public void initView(){
        mTextTitle.setText("NFC签到");
        mTextOption.setVisibility(View.GONE);
    }

    public void initData(){
        mSignInfoId = getIntent().getIntExtra(AppConst.SIGNINFO_ID, -1);
        refreshData();
    }

    public void initEvent(){
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (parcelables != null && parcelables.length>0)
        {
            readTextFromMessage((NdefMessage) parcelables[0]);
        }
        else
        {
            Toast.makeText(this, "未找到标签信息", Toast.LENGTH_LONG).show();
        }
        super.onNewIntent(intent);
    }

    private void readTextFromMessage(NdefMessage ndefMessage)
    {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if (ndefRecords != null && ndefRecords.length>0)
        {
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
            DialogUtils.showLoadingDialog(this, false);
            new SignRecordTask(new SignRecordTask.CallBack() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    DialogUtils.closeLoadingDialog();
                    Toast.makeText(SignInActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                    refreshData();
                }

                @Override
                public void onFailed(AppException exception) {
                    DialogUtils.closeLoadingDialog();
                    Toast.makeText(SignInActivity.this, exception.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }).execute(tagContent, "" + getIntent().getIntExtra(AppConst.SIGNINFO_ID, -1));
        }
        else
        {
            Toast.makeText(this, "NFC记录不存在", Toast.LENGTH_LONG).show();
        }

    }

    public String getTextFromNdefRecord (NdefRecord ndefRecord)
    {
        String tagContent = null;

        try
        {
            byte[] payload = ndefRecord.getPayload();
            tagContent = new String(payload, 0 , payload.length, "UTF-8");

        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("fail: ", e.getMessage());
        }
        return tagContent;
    }

    protected void onPause() {
        if (nfcAdapter != null) nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();

        if (nfcAdapter != null)
        {
            if (!nfcAdapter.isEnabled())
            {
                Toast.makeText(this, "请开启NFC", Toast.LENGTH_SHORT).show();
                return;
            }
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
        }
        else
        {
            Toast.makeText(this, "设备不支持NFC", Toast.LENGTH_LONG).show();
        }

    }

    public void refreshData(){
        if(mRecordTask != null && mRecordTask.getStatus() == AsyncTask.Status.RUNNING)
            mRecordTask.cancel(true);
        mRecordTask =  new GetSignRecordTask(new GetSignRecordTask.CallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                // jsonObject.getJSONArray("signRecords");
                try{
                    List<SignRecord> signRecords = (List<SignRecord>)(JsonUtils.arrayToList(SignRecord.class, jsonObject.getJSONArray("signRecords")));
                    SignRecordAdapter signRecordAdapter = new SignRecordAdapter(SignInActivity.this, R.layout.adapter_sign_record, signRecords);
                    mListSignUser.setAdapter(signRecordAdapter);
                }catch (Exception e){
                    Toast.makeText(SignInActivity.this, "获取签到人员失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(AppException exception){

            }
        });
        mRecordTask.execute("" + mSignInfoId);
    }
}
