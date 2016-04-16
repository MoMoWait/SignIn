package momo.cn.edu.fjnu.videoclient.activity;

import android.support.v4.app.Fragment;

import org.json.JSONObject;

import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;
import momo.cn.edu.fjnu.videoclient.fragment.ManagerMainFragment;
import momo.cn.edu.fjnu.videoclient.fragment.StudentMainFragment;


/**
 * 主目录页面
 * Created by GaoFei on 2016/3/10.
 */
public class MainActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        String userInfo = StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO);
        String type = String.valueOf("-1");
        try{
            JSONObject userObject = new JSONObject(userInfo);
            type = userObject.getString("type");
        }catch (Exception e){

        }
        if(String.valueOf(AppConst.UserType.MANAGER).equals(type))
            return new ManagerMainFragment();
        return new StudentMainFragment();
    }
}
