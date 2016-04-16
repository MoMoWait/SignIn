package momo.cn.edu.fjnu.videoclient.model.net;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.service.NetService;

/**
 * 用户头像上传任务块
 * Created by GaoFei on 2016/3/28.
 */
public class HeadPhotoUploadTask extends AsyncTask<String, Integer, Integer>{
    public static final String TAG = HeadPhotoUploadTask.class.getSimpleName();
    public interface CallBack{
        void onSuccess(JSONObject jsonObject);
        void onFailed(AppException exception);
    }
    private CallBack mCallback;
    private JSONObject mJsonResult;
    private AppException mException;

    public HeadPhotoUploadTask(CallBack callBack){
        this.mCallback = callBack;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Map<String, Object> reqParams = new LinkedHashMap<>();
        reqParams.put("id", params[0]);
        reqParams.put("file", new File(params[1]));
        try {
            mJsonResult = NetService.request("HeadPhotoUploadService", reqParams);
        } catch (AppException e) {
            Log.i(TAG, "" + e);
            mException = e;
            e.printStackTrace();
            return AppConst.RetResult.FAILED;
        }
        return AppConst.RetResult.SUCC;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == AppConst.RetResult.SUCC)
            mCallback.onSuccess(mJsonResult);
        else
            mCallback.onFailed(mException);
    }
}
