package momo.cn.edu.fjnu.videoclient.model.net;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import momo.cn.edu.fjnu.androidutils.utils.JsonUtils;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.db.UserService;
import momo.cn.edu.fjnu.videoclient.pojo.User;
import momo.cn.edu.fjnu.videoclient.service.NetService;


/**
 * 登录异步块
 * Created by GaoFei on 2016/3/10.
 */
public class LoginTask extends AsyncTask<String, Integer, Integer>{
    public static final String TAG = LoginTask.class.getSimpleName();
    public interface CallBack{
        void onSuccess(JSONObject jsonObject);
        void onFailed(AppException exception);
    }
    private CallBack mCallback;
    private JSONObject mJsonResult;
    private AppException mException;

    public LoginTask(CallBack callBack){
        this.mCallback = callBack;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Map<String, Object> reqParams = new LinkedHashMap<>();
        reqParams.put("user_name", params[0]);
        reqParams.put("password", params[1]);
        try {
            mJsonResult = NetService.request("LoginService", reqParams);
        } catch (AppException e) {
            mException = e;
            e.printStackTrace();
            return AppConst.RetResult.FAILED;
        }
        try{
            JSONObject userObject = mJsonResult.getJSONObject("user");
            //此处需要判断用户类型,如果用户类型是管理员
            if(userObject.getInt("type") == AppConst.UserType.MANAGER){
                Map<String, Object> reqAllUserParams = new LinkedHashMap<>();
                try {
                    JSONObject allUserObject = NetService.request("GetAllUserService", reqAllUserParams);
                    JSONArray userArrays = allUserObject.getJSONArray("users");
                    List<User> userList = (List<User>)(JsonUtils.arrayToList(User.class, userArrays));
                    Log.i(TAG, "获取的用户列表:" + userList);
                    UserService userService = new UserService();
                    userService.saveOrUpdateAll(userList);
                    List<User> saveUsers =  userService.getAll(User.class);
                    Log.i(TAG, "从数据库读出来的用户:" + saveUsers);
                } catch (AppException e) {
                    mException = e;
                    e.printStackTrace();
                    return AppConst.RetResult.FAILED;
                }
            }
        }catch (Exception e){
            Log.i(TAG, "发生异常:" + e);
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
