package momo.cn.edu.fjnu.videoclient.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import momo.cn.edu.fjnu.androidutils.base.BaseFragment;
import momo.cn.edu.fjnu.androidutils.utils.DialogUtils;
import momo.cn.edu.fjnu.androidutils.utils.ValidUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.net.CreateSignInfoTask;

/**
 * 新建签到页面
 * Created by GaoFei on 2016/3/30.
 */
@ContentView(R.layout.fragment_create_sign)
public class CreateSignFragment extends BaseFragment{
    private final String TAG = CreateSignFragment.class.getSimpleName();
    @ViewInject(R.id.img_back)
    private ImageView mImgBack;
    @ViewInject(R.id.text_title)
    private TextView mTextTitle;
    @ViewInject(R.id.text_option)
    private TextView mTextOption;
    @ViewInject(R.id.btn_finish)
    private Button mBtnFinish;
    @ViewInject(R.id.edit_course)
    private EditText mEditCourse;
    @ViewInject(R.id.edit_persons)
    private EditText mEditPersons;
    @ViewInject(R.id.edit_start_time)
    private EditText mEditStartTime;
    @ViewInject(R.id.edit_end_time)
    private EditText mEditEndTime;
    @ViewInject(R.id.edit_start_date)
    private EditText mEditStartDate;
    @ViewInject(R.id.edit_end_date)
    private EditText mEditEndDate;
    private int mStartDate;
    private int mStartTime;
    private int mEndDate;
    private int mEndTime;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void initView() {
        mTextTitle.setText("新建签到");
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

        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidUtils.isEmpty(mEditCourse.getText().toString()) || ValidUtils.isEmpty(mEditPersons.getText().toString())
                        || ValidUtils.isEmpty(mEditStartDate.getText().toString()) || ValidUtils.isEmpty(mEditStartTime.getText().toString())
                        || ValidUtils.isEmpty(mEditEndDate.getText().toString()) || ValidUtils.isEmpty(mEditEndTime.getText().toString())){
                    Toast.makeText(getContext(), "请输入完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                String course = mEditCourse.getText().toString();
                String persons = mEditPersons.getText().toString();
                int startTime = mStartDate + mStartTime;
                int endTime = mEndDate + mEndTime;
                if(startTime >= endTime){
                    Toast.makeText(getContext(), "结束时间必须大于开始时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                DialogUtils.showLoadingDialog(getContext(), false);
                new CreateSignInfoTask(new CreateSignInfoTask.CallBack() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        DialogUtils.closeLoadingDialog();
                        Toast.makeText(getContext(), "新建签到成功", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }

                    @Override
                    public void onFailed(AppException exception) {
                        DialogUtils.closeLoadingDialog();
                        Toast.makeText(getContext(), exception.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                }).execute(course, persons, "" + startTime, "" + endTime);
            }
        });

        //开始日期监听事件
        mEditStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                try{
                    Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()));
                    calendar.setTime(date);
                }catch (Exception e){

                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mStartDate = (int)(calendar.getTime().getTime() / 1000);
                        Log.i(TAG, "开始日期:" + mStartDate);
                        mEditStartDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(view.getCalendarView().getDate())));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        //开始时间监听事件
        mEditStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mStartTime = hourOfDay * 60  * 60 + minute * 60;
                        //Log.i(TAG, "获取的当前时间:" + hourOfDay);
                        mEditStartTime.setText(String.format("%1$02d:%2$02d", hourOfDay, minute));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                timePickerDialog.show();
            }
        });

        //结束日期监听事件
        mEditEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                try{
                    Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()));
                    calendar.setTime(date);
                }catch (Exception e){

                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mEndDate = (int)(calendar.getTime().getTime() / 1000);
                        mEditEndDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(view.getCalendarView().getDate())));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        //结束时间监听事件
        mEditEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mEndTime = hourOfDay * 60 * 60 + minute * 60;
                        mEditEndTime.setText(String.format("%1$02d:%2$02d", hourOfDay, minute));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                timePickerDialog.show();
            }
        });
    }
}
