package momo.cn.edu.fjnu.videoclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.pojo.SignInfo;

/**
 * 所有以创建列表适配其
 * Created by GaoFei on 2016/4/4.
 */
public class AllSignInfosAdapter extends ArrayAdapter<SignInfo>{
    private List<SignInfo> mObjects;
    private Context mContext;
    public AllSignInfosAdapter(Context context, int resource, List<SignInfo> objects) {
        super(context, resource, objects);
        this.mObjects = objects;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public SignInfo getItem(int position) {
        return mObjects.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_all_signinfo, parent, false);
            ((TextView)convertView.findViewById(R.id.text_course)).setText(getItem(position).getCourse());
            ((TextView)convertView.findViewById(R.id.text_persons)).setText("" + getItem(position).getPersons());
            ((TextView)convertView.findViewById(R.id.text_start_time)).setText(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(new Date(getItem(position).getStartTime() * 1000L)));
            ((TextView)convertView.findViewById(R.id.text_end_time)).setText(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(new Date(getItem(position).getEndTime() * 1000L)));
        }
        return convertView;
    }
}
