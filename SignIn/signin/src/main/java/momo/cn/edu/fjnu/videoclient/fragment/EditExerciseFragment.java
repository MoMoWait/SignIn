package momo.cn.edu.fjnu.videoclient.fragment;

        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

        import org.xutils.view.annotation.ContentView;
        import org.xutils.view.annotation.ViewInject;

        import momo.cn.edu.fjnu.androidutils.base.BaseFragment;
        import momo.cn.edu.fjnu.videoclient.R;

/**
 * 编辑已建立活动
 * Created by GaoFei on 2016/3/30.
 */
@ContentView(R.layout.fragment_edit_exercise)
public class EditExerciseFragment extends BaseFragment{

    @ViewInject(R.id.img_back)
    private ImageView mImgBack;

    @ViewInject(R.id.text_title)
    private TextView mTextTitle;

    @ViewInject(R.id.text_option)
    private TextView mTextOption;

    @Override
    public void initView() {
        mTextTitle.setText("编辑");
        mTextOption.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
