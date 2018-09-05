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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * 房东信息验证
 */
public class EditIdentityActivity extends BaseActivity {
    private static final int REQUEST_PICK_IMAGE = 1; //相册选取
    private static final int REQUEST_CAPTURE = 2;  //拍照
    private static final int REQUEST_PERMISSION = 4;  //权限请求
    @BindView(R.id.img_identity_zhizhao)
    ImageView mImgIdentityZhizhao;
    @BindView(R.id.upload)
    Button mUpload;
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    @BindView(R.id.titleview_title)
    TextView tvTitle;
    @BindView(R.id.titleview_btnRight)
    Button btnRight;
    @BindView(R.id.edit_identity_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.edit_identity_nickname)
    EditText mEditIdentityNickname;
    @BindView(R.id.edit_identity_phone)
    EditText mEditIdentityPhone;
    @BindView(R.id.edit_identity_zhizhao)
    EditText mEditIdentityZhizhao;
    @BindView(R.id.edit_identity_zhizhao_code)
    EditText mEditIdentityZhizhaoCode;
    @BindView(R.id.landlord_examine_result)
    TextView mExamineResult;
    private String mUid;
    private PopupWindow popupWindow;
    private OSS mOss;
    private boolean isClickCamera;
    private String imagePath;
    private File cropFile;
    private Uri newImageUri;
    private File cameraFile1;
    private String fileFolder, imgName;
    private Uri imageUri;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_identity;
    }

    @Override
    protected void initView() {
        tvTitle.setText("请帮助我们验证您的身份");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("保存");
        mUid = SharedPreferenceUtil.getAccessToken();
        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void initData() {
        getFileType("5");
        initOSS(endpoint);
        getExamineResult();
    }

    private void getExamineResult() {
        OkHttpUtils.post().url(Constant.Examine_Result)
                .addParams("uid", mUid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("status") == 1) {
                                JSONObject data = jsonObject.getJSONObject("data");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight, R.id.edit_identity_avatar, R.id.img_identity_zhizhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                String nickName = mEditIdentityNickname.getText().toString();
                String phone = mEditIdentityPhone.getText().toString();
                String license = mEditIdentityZhizhao.getText().toString();
                String licenseCode = mEditIdentityZhizhaoCode.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    toast("昵称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    toast("联系电话不能为空");
                    return;
                }
                if (TextUtils.isEmpty(license)) {
                    toast("营业执照不能为空");
                    return;
                }
                if (TextUtils.isEmpty(licenseCode)) {
                    toast("企业注册好不能为空");
                    return;
                }
                if(cropFile==null){
                    toast("营业执照不能为空");
                    return;
                }
                mUpload.setVisibility(View.VISIBLE);
                upLoadFile(md5(fileName()), cropFile.getAbsolutePath().substring(cropFile.getAbsolutePath().indexOf("."), cropFile.getAbsolutePath().length()));
                break;
            case R.id.edit_identity_avatar:
                break;
            case R.id.img_identity_zhizhao:
                showPopupWindow();
                setBackgroundAlpha(EditIdentityActivity.this, 0.5f);
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
        View layout = View.inflate(EditIdentityActivity.this, R.layout.layout_bottom_img, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true); // 点击空白处时，隐藏掉POP窗口
        popupWindow.setOutsideTouchable(true); // 点击外部时，隐藏掉POP窗口
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
        popupWindow.showAtLocation(EditIdentityActivity.this.findViewById(R.id.landlordcertification), Gravity.BOTTOM, 20, 0);
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
                setBackgroundAlpha(EditIdentityActivity.this, 1.0f);
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
                setBackgroundAlpha(EditIdentityActivity.this, 1.0f);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setBackgroundAlpha(EditIdentityActivity.this, 1.0f);
            }
        });
    }

    private void upLoadFile(String path, String suffix) {
        imgName = fileFolder + path + suffix;
        PutObjectRequest put = new PutObjectRequest("xygdir", fileFolder + imgName, cropFile.getAbsolutePath());
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
                        mUpload.setText("上传中，当前进度 " + bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
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
                    if (object.getString("Status") .equals("1")) {
                        commitData();
                    } else {
                        toast("上传失败");
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
                    Log.e("tag", serviceException.getErrorCode());
                    Log.e("tag", serviceException.getRequestId());
                    Log.e("tag", serviceException.getHostId());
                    Log.e("tag", serviceException.getRawMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast("上传失败");
                        }
                    });
                }
            }
        });

    }

    private void commitData() {
        Map<String,String>parmas = new HashMap<>();
        parmas.put("uid",mUid);
        parmas.put("name",mEditIdentityNickname.getText().toString());
        parmas.put("mobile",mEditIdentityPhone.getText().toString());
        parmas.put("papers",mEditIdentityZhizhao.getText().toString());
        parmas.put("pnumber",mEditIdentityZhizhaoCode.getText().toString());
        parmas.put("purl",imgName);
        OkHttpUtils.post().url(Constant.APPLY_COMMIT)
                .params(parmas)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("status")==1){
                                mUpload.setVisibility(View.GONE);
                                openActivity(TenementManagementActivity.class);
                                finish();
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


    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(EditIdentityActivity.this, REQUEST_PERMISSION,
                PERMISSIONS);
    }

    /**
     * 打开系统相机
     */
    private void openCamera() {
        cameraFile1 = new FileStorage().createIconFile(".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(EditIdentityActivity.this, "com.yunju.app.fileprovider", cameraFile1);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(cameraFile1);
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
                    cropFile = cameraFile1;
                    Glide.with(EditIdentityActivity.this).load(cameraFile1)
                            .into(mImgIdentityZhizhao);
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
        cropFile = new File(imagePath);
        Glide.with(EditIdentityActivity.this).load(imagePath).into(mImgIdentityZhizhao);
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
        cropFile = new File(imagePath);
        Glide.with(EditIdentityActivity.this).load(imagePath).into(mImgIdentityZhizhao);
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


}
