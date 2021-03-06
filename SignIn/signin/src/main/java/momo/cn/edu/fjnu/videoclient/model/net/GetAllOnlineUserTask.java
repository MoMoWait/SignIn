package momo.cn.edu.fjnu.videoclient.model.net;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.service.NetService;

/**
 * 获取所有在线用户异步块
 * Created by GaoFei on 2016/3/25.
 */
public class GetAllOnlineUserTask extends AsyncTask<String, Integer, Integer>{
    public static final String TAG = GetAllOnlineUserTask.class.getSimpleName();
    public interface CallBack{
        void onSuccess(JSONObject jsonObject);
        void onFailed(AppException exception);
    }
    private CallBack mCallback;
    private JSONObject mJsonResult;
    private AppException mException;

    public GetAllOnlineUserTask(CallBack callBack){
        this.mCallback = callBack;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Map<String, Object> reqParams = new LinkedHashMap<>();
        try {
            mJsonResult = NetService.request("GetAllOnlineUserService", reqParams);
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
