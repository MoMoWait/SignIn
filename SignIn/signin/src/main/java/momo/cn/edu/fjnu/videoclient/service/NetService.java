package momo.cn.edu.fjnu.videoclient.service;

import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.Map;

import momo.cn.edu.fjnu.androidutils.data.CommonValues;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.exception.APPErrorCode;
import momo.cn.edu.fjnu.videoclient.exception.AppException;


/**
 * 网络服务,用于请求服务器中的方法
 * Created by GaoFei on 2016/3/8.
 */
public class NetService {

    public static final String TAG = NetService.class.getSimpleName();

    /**
     * @param method 请求方法
     * @param params 请求参数
     * @return
     */
    public static JSONObject request(String method, Map<String, Object> params) throws AppException {
        AQuery query = new AQuery(CommonValues.application);
        AjaxCallback<String> callback = new AjaxCallback<>();
        callback.url(AppConst.URL_HEAD + method).params(params).type(String.class);
        query.sync(callback);
        String strResult = callback.getResult();
        if (callback.getStatus().getCode() == AjaxStatus.NETWORK_ERROR || strResult == null) {
            //网络错误
            throw new AppException(APPErrorCode.ERROR_NETWORK, "网络错误");
        } else {
            try {
                JSONObject resultJson = new JSONObject(strResult);
                JSONObject content = resultJson.getJSONObject("result");
                try {
                    content.get("succ");
                } catch (Exception e) {
                    if (e.getMessage().equals("No value for succ"))
                        throw new AppException(APPErrorCode.INTERNEL_ERROR, content.getString("error"));
                    Log.i(TAG, "locae:" + e.getLocalizedMessage());
                    Log.i(TAG, "msg:" + e.getMessage());
                }
                return content.getJSONObject("succ");
            } catch (Exception e) {
                Log.i(TAG, "" + e);
                try {
                    AppException internelException = (AppException) e;
                    //服务器内部抛出的错误
                    if (internelException.getCode() == APPErrorCode.INTERNEL_ERROR)
                        throw new AppException(APPErrorCode.INTERNEL_ERROR, internelException.getErrorMsg());
                } catch (ClassCastException ec) {
                    throw new AppException(APPErrorCode.INVALID_RESPONSE, "服务端非法数据返回");
                } catch (AppException ea) {
                    throw new AppException(APPErrorCode.INTERNEL_ERROR, ea.getErrorMsg());
                }
            }

        }
        return new JSONObject();
    }

}
