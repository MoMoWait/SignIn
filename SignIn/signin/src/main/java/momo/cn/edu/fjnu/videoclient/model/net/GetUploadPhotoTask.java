package momo.cn.edu.fjnu.videoclient.model.net;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.service.NetService;

/**
 * 获取图片
 * Created by GaoFei on 2016/3/27.
 */
public class GetUploadPhotoTask extends AsyncTask<String, Integer, Integer>{
    public static final String TAG = GetUploadPhotoTask.class.getSimpleName();
    public interface CallBack{
        void onSuccess(JSONObject jsonObject);
        void onFailed(AppException exception);
    }
    private CallBack mCallback;
    private JSONObject mJsonResult;
    private AppException mException;

    public GetUploadPhotoTask(CallBack callBack){
        this.mCallback = callBack;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Map<String, Object> reqParams = new LinkedHashMap<>();
        reqParams.put("create_time", params[0]);
        try {
            mJsonResult = NetService.request("GetUploadPhotoService", reqParams);
        } catch (AppException e) {
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
