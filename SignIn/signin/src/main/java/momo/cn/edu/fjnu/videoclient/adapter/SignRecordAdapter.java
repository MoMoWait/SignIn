package momo.cn.edu.fjnu.videoclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.model.db.UserService;
import momo.cn.edu.fjnu.videoclient.pojo.SignRecord;
import momo.cn.edu.fjnu.videoclient.pojo.User;

/**
 * 签到记录适配器
 * Created by GaoFei on 2016/4/5.
 */
public class SignRecordAdapter extends ArrayAdapter<SignRecord>{
    private Context mContext;
    private List<SignRecord> mObjects;
    public SignRecordAdapter(Context context, int resource, List<SignRecord> objects) {
        super(context, resource, objects);
        mContext = context;
        mObjects = objects;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public SignRecord getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_sign_record, parent, false);
            TextView textStudentNo = (TextView)(convertView.findViewById(R.id.text_student_no));
            TextView textName = (TextView)(convertView.findViewById(R.id.text_name));
            SignRecord signRecord = getItem(position);
            UserService userService = new UserService();
            User user =  userService.getObjectById(User.class, signRecord.getUid());
            textStudentNo.setText(user.getUser_name());
            textName.setText(user.getNick_name());
        }
        return convertView;
    }
}
