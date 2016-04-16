package momo.cn.edu.fjnu.videoclient.model.net;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.service.NetService;

/**
 * 注册异步块
 * Created by GaoFei on 2016/3/24.
 */
public class RegisterTask extends AsyncTask<String, Integer, Integer>{
    public static final String TAG = RegisterTask.class.getSimpleName();
    public interface CallBack{
        void onSuccess(JSONObject jsonObject);
        void onFailed(AppException exception);
    }
    private CallBack mCallback;
    private JSONObject mJsonResult;
    private AppException mException;

    public RegisterTask(CallBack callBack){
        this.mCallback = callBack;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Map<String, Object> reqParams = new LinkedHashMap<>();
        reqParams.put("user_name", params[0]);
        reqParams.put("password", params[1]);
        reqParams.put("mail", params[2]);
        reqParams.put("nick_name", params[3]);
        reqParams.put("phone_number", params[4]);
        try {
            mJsonResult = NetService.request("RegisterService", reqParams);
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
