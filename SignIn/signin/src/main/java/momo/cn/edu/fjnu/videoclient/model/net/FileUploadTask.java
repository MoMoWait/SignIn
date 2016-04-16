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
 * 文件上传异步块
 * Created by GaoFei on 2016/3/11.
 */
public class FileUploadTask extends AsyncTask<String, Integer, Integer>{
    public static final String TAG = FileUploadTask.class.getSimpleName();
    public interface CallBack{
        void onSuccess(JSONObject jsonObject);
        void onFailed(AppException exception);
    }
    private CallBack mCallback;
    private JSONObject mJsonResult;
    private AppException mException;

    public FileUploadTask(CallBack callBack){
        this.mCallback = callBack;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Map<String, Object> reqParams = new LinkedHashMap<>();
        reqParams.put("uid", params[0]);
        reqParams.put("type", params[1]);
        reqParams.put("file_size", params[2]);
        reqParams.put("file", new File(params[3]));
        reqParams.put("lng", params[4]);
        reqParams.put("lat", params[5]);
        reqParams.put("address", params[6]);
      //  reqParams.put("address", "happy day");
        Log.i(TAG, "获取的地址:" +params[6]);
        try {
            mJsonResult = NetService.request("FileUploadService", reqParams);
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
