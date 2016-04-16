package momo.cn.edu.fjnu.videoclient.model.net;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.service.NetService;

/**
 * 获取签到用户
 * Created by GaoFei on 2016/4/5.
 */
public class GetSignRecordTask extends AsyncTask<String, Integer, Integer>{
    public interface CallBack{
        void onSuccess(JSONObject jsonObject);
        void onFailed(AppException exception);
    }
    private CallBack mCallback;
    private JSONObject mJsonResult;
    private AppException mException;

    public GetSignRecordTask(CallBack callBack){
        this.mCallback = callBack;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Map<String, Object> reqParams = new LinkedHashMap<>();
        try {
            JSONObject userObject = new JSONObject(StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO));
            if(userObject.getInt("type") == AppConst.UserType.NORMAL)
                reqParams.put("uid", params[0]);
            else
                reqParams.put("sid", params[0]);
        }catch (Exception e){

        }
        try {
            mJsonResult = NetService.request("GetSignRecordService", reqParams);
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
