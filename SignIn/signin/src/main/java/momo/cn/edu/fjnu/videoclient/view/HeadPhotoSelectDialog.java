package momo.cn.edu.fjnu.videoclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import momo.cn.edu.fjnu.androidutils.base.BaseDialog;
import momo.cn.edu.fjnu.androidutils.utils.DeviceInfoUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.adapter.HeadPhotoSelectAdapter;

/**
 * 头像选择方法
 * Created by GaoFei on 2016/3/28.
 */
public class HeadPhotoSelectDialog extends BaseDialog{

    private Context mContext;
    private SelectChangeListener mListener;
    private View mView;
    @ViewInject(R.id.list_head_photo_select)
    private ListView mListHeadPhoto;

    public interface SelectChangeListener{
        void onSelectCamera();
        void onSelectPhoto();
    }

    public HeadPhotoSelectDialog(Context context, SelectChangeListener listener){
        super(context);
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_head_photo_select, null, false);
        setContentView(mView, new ViewGroup.LayoutParams((int)(DeviceInfoUtils.getScreenWidth(mContext)*0.8), ViewGroup.LayoutParams.WRAP_CONTENT));
        x.view().inject(this, mView);
    }

    @Override
    public void initData() {
        List<String> selectNames = new ArrayList<>();
        selectNames.add("拍照");
        selectNames.add("图库");
        HeadPhotoSelectAdapter adapter = new HeadPhotoSelectAdapter(mContext, R.layout.adapter_head_photo, R.id.text_select_name, selectNames);
        mListHeadPhoto.setAdapter(adapter);
    }

    @Override
    public void initEvent() {
        mListHeadPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    mListener.onSelectCamera();
                else
                    mListener.onSelectPhoto();
                dismiss();
            }
        });
    }
}
