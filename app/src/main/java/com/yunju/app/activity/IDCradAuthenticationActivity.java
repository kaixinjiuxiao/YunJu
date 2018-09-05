package com.yunju.app.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.util.Constant;
import com.yunju.app.util.FileStorage;
import com.yunju.app.util.PermissionsChecker;
import com.yunju.app.util.STSGetter;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author: captain
 * Time:  2018/5/29 0029
 * Describe:
 */
public class IDCradAuthenticationActivity extends BaseActivity {
    private static final int REQUEST_PICK_IMAGE = 1; //相册选取
    private static final int REQUEST_CAPTURE = 2;  //拍照
    private static final int REQUEST_PICTURE_CUT = 3;  //剪裁图片
    private static final int REQUEST_PERMISSION = 4;  //权限请求
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private String endpoint = "http://oss-cn-beijing.aliyuncs.com";


    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.idCradOne)
    ImageView mIdCradOne;
    @BindView(R.id.txtDeleteOne)
    TextView mTxtDeleteOne;
    @BindView(R.id.idCardTwo)
    ImageView mIdCardTwo;
    @BindView(R.id.txtDeleteTwo)
    TextView mTxtDeleteTwo;
    @BindView(R.id.realName)
    TextView mRealName;
    @BindView(R.id.edtName)
    EditText mEdtName;
    @BindView(R.id.realIdCard)
    TextView mRealIdCard;
    @BindView(R.id.edtIdCard)
    EditText mEdtIdCard;
    @BindView(R.id.btnAuthentication)
    Button mBtnAuthentication;
    private PopupWindow popupWindow;
    private String imagePath, fileFolder, imgName,mToken;
    private boolean oneClick, twoClick;
    private boolean isClickCamera;
    private File cameraFile, cropFileOne, cropFileTwo, cropFileThree, cropFile;
    private List<String> filePath = new ArrayList<>();
    private List<String> imageNameList = new ArrayList<>();
    private Uri imageUri, imageUri1, imagrUri2;//原图保存地址
    private int a;
    private OSS mOss;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_idcard_authentication;
    }

    @Override
    protected void initView() {
        mTitleviewTitle.setText("实名认证");
        mPermissionsChecker = new PermissionsChecker(this);
        mToken = SharedPreferenceUtil.getAccessToken();
        getUserInfo();
        initOSS(endpoint);
        getFileType("2");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }


    @OnClick({R.id.titleview_btnLeft, R.id.idCradOne, R.id.txtDeleteOne, R.id.idCardTwo, R.id.txtDeleteTwo, R.id.btnAuthentication})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.idCradOne:
                oneClick = true;
                showPopupWindow();
                setBackgroundAlpha(IDCradAuthenticationActivity.this, 0.5f);
                break;
            case R.id.txtDeleteOne:
                if (cropFileOne != null) {
                    cropFileOne = null;
                    if (filePath.size() == 2) {
                        filePath.remove(0);
                    }
                    Glide.with(IDCradAuthenticationActivity.this).load(R.drawable.icon_id_crad1).into(mIdCradOne);
                } else {
                    mTxtDeleteOne.setEnabled(false);
                }
                break;
            case R.id.idCardTwo:
                twoClick = true;
                showPopupWindow();
                setBackgroundAlpha(IDCradAuthenticationActivity.this, 0.5f);
                break;
            case R.id.txtDeleteTwo:
                if (cropFileTwo != null) {
                    cropFileTwo = null;
                    if (filePath.size() == 2) {
                        filePath.remove(1);
                    }
                    Glide.with(IDCradAuthenticationActivity.this).load(R.drawable.icon_id_card2).into(mIdCardTwo);
                } else {
                    mTxtDeleteTwo.setEnabled(false);
                }
                break;
            case R.id.btnAuthentication:
                check();
                break;
            default:
                break;
        }
    }


    public void initOSS(String endpoint) {
        //使用自己的获取STSToken的类
        OSSCredentialProvider credentialProvider = new STSGetter(Constant.GET_OSS_DATA);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        mOss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
    }

    private void getFileType(String state) {
        OkHttpUtils.post().url(Constant.GET_OSS_BUCKET)
                .addParams("state", state).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                fileFolder = response;
            }
        });
    }

    private void showPopupWindow() {
        View layout = View.inflate(IDCradAuthenticationActivity.this, R.layout.layout_bottom_img, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true); // 点击空白处时，隐藏掉POP窗口
        popupWindow.setOutsideTouchable(true); // 点击外部时，隐藏掉POP窗口
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
        popupWindow.showAtLocation(IDCradAuthenticationActivity.this.findViewById(R.id.authentication), Gravity.BOTTOM, 20, 0);
        TextView ablum = (TextView) layout.findViewById(R.id.ablum);
        TextView carma = (TextView) layout.findViewById(R.id.carmea);
        TextView cancel = (TextView) layout.findViewById(R.id.cancle);
        ablum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                        startPermissionsActivity();
                    } else {
                        selectFromAlbum();
                    }
                } else {
                    selectFromAlbum();
                }
                popupWindow.dismiss();
                isClickCamera = false;
                setBackgroundAlpha(IDCradAuthenticationActivity.this, 1.0f);
            }
        });
        carma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查权限(6.0以上做权限判断)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                        startPermissionsActivity();
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
                popupWindow.dismiss();
                isClickCamera = true;
                setBackgroundAlpha(IDCradAuthenticationActivity.this, 1.0f);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setBackgroundAlpha(IDCradAuthenticationActivity.this, 1.0f);
            }
        });
    }

    private void getUserInfo() {
        OkHttpUtils.post().url(Constant.USER_INFO).addParams("uid", mToken).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("status") == 1) {
                                JSONObject object = jsonObject.getJSONObject("data");
                                if (TextUtils.isEmpty(object.getString("id_card"))) {
                                    mEdtIdCard.setVisibility(View.VISIBLE);
                                } else {
                                    mEdtIdCard.setVisibility(View.GONE);
                                    mRealIdCard.setText(object.getString("id_card"));
                                    mRealIdCard.setVisibility(View.VISIBLE);
                                }
                                if (TextUtils.isEmpty(object.getString("Card_Name"))) {
                                    mEdtName.setVisibility(View.VISIBLE);
                                } else {
                                    mEdtName.setVisibility(View.GONE);
                                    mRealName.setText(object.getString("Card_Name"));
                                    mRealName.setVisibility(View.VISIBLE);
                                }
                                if(!TextUtils.isEmpty(object.getString("IDCard_img1"))){
                                    Glide.with(IDCradAuthenticationActivity.this).load(object.getString("IDCard_img1")).into(mIdCradOne);
                                }
                                if(!TextUtils.isEmpty(object.getString("IDCard_img2"))){
                                    Glide.with(IDCradAuthenticationActivity.this).load(object.getString("IDCard_img2")).into(mIdCardTwo);
                                }
                                if(object.getInt("finish_check")==0){
                                    mBtnAuthentication.setText("认证");
                                }else if(object.getInt("finish_check")==1){
                                    mBtnAuthentication.setText("认证中");
                                    mBtnAuthentication.setEnabled(false);
                                }else if(object.getInt("finish_check")==2){
                                    mBtnAuthentication.setText("认证成功");
                                    mBtnAuthentication.setEnabled(false);
                                }else{
                                    mBtnAuthentication.setText("认证失败");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 上传的图片名
     *
     * @return
     */
    private String fileName() {
        String string = new Date().toString();
        String str = "";
        for (int i = 0; i < 5; i++) {
            str = str + (char) (Math.random() * 26 + 'a');
        }
        return string + str;
    }



    /**
     * 打开系统相机
     */
    private void openCamera() {
        cameraFile = new FileStorage().createIconFile(".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(IDCradAuthenticationActivity.this, "com.yunju.app.fileprovider", cameraFile);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(cameraFile);
        }
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 从相册选择
     */
    private void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_PERMISSION,
                PERMISSIONS);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        if (data == null) {
            return;
        }
        imageUri = data.getData();
        if (DocumentsContract.isDocumentUri(this, imageUri)) {
            //如果是document类型的uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(imageUri);
            if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(imageUri, null);
        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = imageUri.getPath();
        }
        if (oneClick == true) {

            cropFileOne = new File(imagePath.toString());
            Glide.with(IDCradAuthenticationActivity.this).load(imagePath).into(mIdCradOne);
            oneClick = false;
        } else if (twoClick == true) {

            cropFileTwo = new File(imagePath.toString());
            Glide.with(IDCradAuthenticationActivity.this).load(imagePath).into(mIdCardTwo);
            twoClick = false;
        }
    }

    /**
     * 4.4版本以下
     *
     * @param intent
     */
    private void handleImageBeforeKitKat(Intent intent) {
        if (intent == null) {
            return;
        }
        imageUri = intent.getData();
        imagePath = getImagePath(imageUri, null);
        if (oneClick == true) {

            cropFileOne = new File(imagePath.toString());
            Glide.with(IDCradAuthenticationActivity.this).load(imagePath).into(mIdCradOne);
            oneClick = false;
        } else if (twoClick == true) {

            cropFileTwo = new File(imagePath.toString());
            Glide.with(IDCradAuthenticationActivity.this).load(imagePath).into(mIdCardTwo);
            twoClick = false;
        }
    }

    /**
     * 获得图片路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    private void check() {
        String idCard = mEdtIdCard.getText().toString();
        String cardName = mEdtName.getText().toString();
        if (cropFileOne == null) {
            Toast.makeText(IDCradAuthenticationActivity.this, "请上传身份证", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cropFileTwo == null) {
            Toast.makeText(IDCradAuthenticationActivity.this, "请上传身份证", Toast.LENGTH_SHORT).show();
            return;
        }
        filePath.add(cropFileOne.getAbsolutePath());
        filePath.add(cropFileTwo.getAbsolutePath());
        if (TextUtils.isEmpty(cardName)) {
            Toast.makeText(IDCradAuthenticationActivity.this, "请填写正确的姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(idCard)) {
            Toast.makeText(IDCradAuthenticationActivity.this, "请填写正确的身份证号码", Toast.LENGTH_SHORT).show();
            return;
        }
        mBtnAuthentication.setClickable(false);
        mTxtDeleteOne.setEnabled(false);
        mTxtDeleteTwo.setEnabled(false);
        mEdtIdCard.setEnabled(false);
        mEdtName.setEnabled(false);
        OkHttpUtils.post().url(Constant.USER_ID_CHECK).addParams("id_card", idCard)
                .addParams("Card_name",cardName).addParams("uid",mToken)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
              }

            @Override
            public void onResponse(String response, int id) {
               try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {
                        startUpLoad();
                    }else if (jsonObject.getInt("status") == 0) {
                        Toast.makeText(IDCradAuthenticationActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        mBtnAuthentication.setClickable(true);
                        mTxtDeleteOne.setEnabled(true);
                        mTxtDeleteTwo.setEnabled(true);
                        mEdtIdCard.setEnabled(true);
                        mEdtName.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startUpLoad() {
       for (int i = 0; i < filePath.size(); i++) {
            upLoadFile(i, filePath.get(i), filePath.get(i).substring(filePath.get(i).lastIndexOf("."), filePath.get(i).length()));
        }
    }

    private void upLoadFile(final int i, String path, String suffix) {
        imgName = md5(fileName()) + i + suffix;
        imageNameList.add(fileFolder + imgName);
        PutObjectRequest put = new PutObjectRequest("xygdir", fileFolder + imgName, path);
        put.setCallbackParam(new HashMap<String, String>() {
            {
                put("callbackUrl", Constant.OSS_CALLBACK_ADD);
                put("callbackBody", "filename=${object}");
            }
        });
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, final long currentSize, final long totalSize) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        float a = currentSize;
                        float b = totalSize;
                        float c = a / b;
                        BigDecimal bigDecimal2 = new BigDecimal(Double.parseDouble(String.valueOf(c * 100)));
                        mBtnAuthentication.setText("上传中，当前进度 " + bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                    }
                });

            }
        });

        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                 String str = result.getServerCallbackReturnBody().toString();
                  try {
                    JSONObject object = new JSONObject(str);
                    if (object.getInt("Status") == 1) {
                        a++;
                        if (a == imageNameList.size()) {
                           commitData();
                        }
                    } else {
                        mBtnAuthentication.setText("上传失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBtnAuthentication.setText("上传失败");
                            mBtnAuthentication.setEnabled(false);
                        }
                    });
                }
            }
        });
    }

    /**
     * 提交数据开始认证
     */
    private void commitData() {
        Map<String ,String > parmas = new HashMap<>();
        parmas.put("uid",mToken);
        parmas.put("IDCard_img1",imageNameList.get(0));
        parmas.put("IDCard_img2",imageNameList.get(1));
        parmas.put("Card_name",mEdtName.getText().toString());
        parmas.put("id_card",mEdtIdCard.getText().toString());
        OkHttpUtils.post().url(Constant.USER_ID_AUTHER).params(parmas) .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status")==1){
                        getUserInfo();
                    }else{
                        mBtnAuthentication.setText("上传失败，请重试");
                        mBtnAuthentication.setClickable(true);
                        mTxtDeleteOne.setEnabled(true);
                        mTxtDeleteTwo.setEnabled(true);
                        mEdtIdCard.setEnabled(true);
                        mEdtName.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PICK_IMAGE://从相册选择
                if (Build.VERSION.SDK_INT >= 19) {
                    handleImageOnKitKat(data);
                } else {
                    handleImageBeforeKitKat(data);
                }
                break;
            case REQUEST_CAPTURE://拍照
                if (resultCode == RESULT_OK) {
                    if (oneClick == true) {
                        cropFileOne = cameraFile;
                        Glide.with(IDCradAuthenticationActivity.this).load(imageUri).into(mIdCradOne);
                        oneClick = false;
                    } else if (twoClick == true) {
                        cropFileTwo = cameraFile;
                        Glide.with(IDCradAuthenticationActivity.this).load(imageUri).into(mIdCardTwo);
                        twoClick = false;
                    }
                }
                break;
            case REQUEST_PERMISSION://权限请求
                if (resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
                    finish();
                } else {
                    if (isClickCamera) {
                        openCamera();
                    } else {
                        selectFromAlbum();
                    }
                }
                break;
        }
    }
}
