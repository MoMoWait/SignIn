package momo.cn.edu.fjnu.videoclient.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.UUID;

import momo.cn.edu.fjnu.androidutils.base.BaseFragment;
import momo.cn.edu.fjnu.androidutils.utils.BitmapUtils;
import momo.cn.edu.fjnu.androidutils.utils.DialogUtils;
import momo.cn.edu.fjnu.androidutils.utils.JsonUtils;
import momo.cn.edu.fjnu.androidutils.utils.ResourceUtils;
import momo.cn.edu.fjnu.androidutils.utils.SizeUtils;
import momo.cn.edu.fjnu.androidutils.utils.StorageUtils;
import momo.cn.edu.fjnu.videoclient.R;
import momo.cn.edu.fjnu.videoclient.data.AppConst;
import momo.cn.edu.fjnu.videoclient.data.SharedKeys;
import momo.cn.edu.fjnu.videoclient.exception.AppException;
import momo.cn.edu.fjnu.videoclient.model.net.HeadPhotoUploadTask;
import momo.cn.edu.fjnu.videoclient.pojo.User;
import momo.cn.edu.fjnu.videoclient.view.HeadPhotoSelectDialog;
import momo.cn.edu.fjnu.videoclient.view.PasswordChangeDialog;
import momo.cn.edu.fjnu.videoclient.view.PersonDataChangeDialog;

/**
 * 个人资料修改页面
 * Created by GaoFei on 2016/3/27.
 */
@ContentView(R.layout.fragment_person_data_change)
public class PersonDataChangeFragment extends BaseFragment{

    @ViewInject(R.id.img_back)
    private ImageView mImgBack;
    @ViewInject(R.id.text_title)
    private TextView mTextTitle;
    @ViewInject(R.id.text_option)
    private TextView mTextOption;
    @ViewInject(R.id.layout_head_photo)
    private LinearLayout mLayoutHeadPhoto;
    @ViewInject(R.id.layout_user_name)
    private LinearLayout mLayoutUserName;
    @ViewInject(R.id.layout_nick_name)
    private LinearLayout mLayoutNickName;
    @ViewInject(R.id.layout_mail)
    private LinearLayout mLayoutMail;
    @ViewInject(R.id.layout_password)
    private LinearLayout mLayoutPassword;
    @ViewInject(R.id.layout_phone_number)
    private LinearLayout mLayoutPhoneNumber;
    @ViewInject(R.id.img_head_photo)
    private ImageView mImgHeadPhoto;
    @ViewInject(R.id.text_user_name)
    private TextView mTextUserName;
    @ViewInject(R.id.text_nick_name)
    private TextView mTextNickName;
    @ViewInject(R.id.text_mail)
    private TextView mTextMail;
    @ViewInject(R.id.text_phone_number)
    private TextView mTextPhoneNumber;
    private String mCameraPhotoPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void initView() {
        mTextTitle.setText(ResourceUtils.getString(R.string.data_change));
        mTextOption.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
       String userInfo =  StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO);
        JSONObject userObject = new JSONObject();
        try{
             userObject = new JSONObject(userInfo);
        }catch (Exception e){

        }
        User user = (User)(JsonUtils.jsonToObject(User.class, userObject));
        AQuery aQuery = new AQuery(mImgHeadPhoto);
        aQuery.image(user.getHead_photo());
        mTextUserName.setText(user.getUser_name());
        mTextNickName.setText(user.getNick_name());
        mTextMail.setText(user.getMail());
    }

    @Override
    public void initEvent() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.activity_exit_left, R.anim.activity_exit_right);
            }
        });

        mLayoutHeadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeadPhotoSelectDialog headPhotoSelectDialog = new HeadPhotoSelectDialog(getContext(), new HeadPhotoSelectDialog.SelectChangeListener() {
                    @Override
                    public void onSelectCamera() {
                        File photoDirFile = new File(AppConst.PHOTO_DIRECTORY);
                        if(!photoDirFile.exists())
                            photoDirFile.mkdirs();
                        File photoFile = new File(photoDirFile, UUID.randomUUID().toString() + ".jpg");
                        mCameraPhotoPath = photoFile.getAbsolutePath();
                        startCamera(mCameraPhotoPath);
                    }

                    @Override
                    public void onSelectPhoto() {
                        startSelectPhoto();
                    }
                });

                headPhotoSelectDialog.show();
            }
        });


        mLayoutNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDataChangeDialog personDataChangeDialog = new PersonDataChangeDialog(getContext(), "nick_name", new PersonDataChangeDialog.DialogChangeListener() {
                    @Override
                    public void onContentChange(TextView textDialogTitle, EditText editContent) {
                        textDialogTitle.setText(ResourceUtils.getString(R.string.nick_name_change));
                        editContent.setHint(R.string.input_nickname);
                        editContent.setText(mTextNickName.getText());
                        //editContent.setText();
                    }

                    @Override
                    public void onChangeSuccess(String nValue) {
                        mTextNickName.setText(nValue);
                    }
                });
                personDataChangeDialog.show();
            }
        });

        mLayoutMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDataChangeDialog personDataChangeDialog = new PersonDataChangeDialog(getContext(), "mail", new PersonDataChangeDialog.DialogChangeListener() {
                    @Override
                    public void onContentChange(TextView textDialogTitle, EditText editContent) {
                        textDialogTitle.setText(ResourceUtils.getString(R.string.mail_change));
                        editContent.setHint(R.string.input_mail);
                        editContent.setText(mTextMail.getText());
                        //editContent.setText();
                    }

                    @Override
                    public void onChangeSuccess(String nValue) {
                        mTextMail.setText(nValue);
                    }
                });
                personDataChangeDialog.show();
            }
        });



        mLayoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordChangeDialog passwordChangeDialog = new PasswordChangeDialog(getContext());
                passwordChangeDialog.show();
            }
        });
    }

    @Override
    public void onTakePicture() {
        //启动裁剪
        startCropPhoto(Uri.fromFile(new File(mCameraPhotoPath)));
    }

    @Override
    public void onSelectPhoto(Uri uri) {
        //启动裁剪
        startCropPhoto(uri);
    }

    @Override
    public void onPhotoCrop(String filePath) {
        //对图片进行压缩
        Bitmap scaleBitmap = BitmapUtils.getScaledBitmapFromFile(filePath, SizeUtils.dp2px(getContext(), 100), SizeUtils.dp2px(getContext(), 100));
        //图片的圆角化处理
        Bitmap saveBitmap = BitmapUtils.getCroppedBitmap(scaleBitmap);
        File saveDirFile = new File(AppConst.PHOTO_DIRECTORY);
        File savePhotoFile = new File(saveDirFile, UUID.randomUUID().toString() + ".png");
        //对圆角图片进行存储
        BitmapUtils.saveBitmapToImage(saveBitmap,savePhotoFile.getAbsolutePath(), Bitmap.CompressFormat.PNG, 80);
        String userInfo = StorageUtils.getDataFromSharedPreference(SharedKeys.CURR_USER_INFO);
        String userId = String.valueOf(-1);
        try{
            JSONObject userObject = new JSONObject(userInfo);
            userId = userObject.getString("id");
        }catch (Exception e){

        }
        DialogUtils.showLoadingDialog(getContext(), false);
        //对保存的图片进行上传
        new HeadPhotoUploadTask(new HeadPhotoUploadTask.CallBack() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                DialogUtils.closeLoadingDialog();
                try{
                    JSONObject userObject = jsonObject.getJSONObject("user");
                    //修改页面
                    String headPhotoUrl = userObject.getString("head_photo");
                    AQuery aQuery = new AQuery(mImgHeadPhoto);
                    aQuery.image(headPhotoUrl);
                    StorageUtils.saveDataToSharedPreference(SharedKeys.CURR_USER_INFO, userObject.toString());
                }catch (Exception e){

                }
                Toast.makeText(getContext(), "头像修改成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(AppException exception) {
                DialogUtils.closeLoadingDialog();
                Toast.makeText(getContext(), exception.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        }).execute(userId, savePhotoFile.getAbsolutePath());
    }
}
