package momo.cn.edu.fjnu.videoclient.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * 头像选择适配器
 * Created by GaoFei on 2016/3/28.
 */
public class HeadPhotoSelectAdapter extends ArrayAdapter<String>{

    public HeadPhotoSelectAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }


}
