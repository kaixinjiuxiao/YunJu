package com.yunju.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author: captain
 * Time:  2018/5/31 0031
 * Describe:分享好友
 */
public class ShareFriendActivity extends BaseActivity {

    @BindView(R.id.titleview_btnLeft)
    ImageView mTitleviewBtnLeft;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.shareImage)
    ImageView mShareImage;
    private String mToken;
    private String mImrUrl;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_share_friend;
    }

    @Override
    protected void initView() {
        mTitleviewTitle.setText("分享好友");
        mToken = SharedPreferenceUtil.getAccessToken();
        getShareUrl();
    }


    private void getShareUrl() {
        OkHttpUtils.post().url(Constant.USER_SHARE_FRIEND)
                .addParams("uid",mToken)
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
                                mImrUrl = jsonObject.getString("msg");
                                Glide.with(ShareFriendActivity.this).load(mImrUrl).into(mShareImage);
                            }else{
                                toast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mShareImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showSaveDialog();
                return true;
            }
        });
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("长按图片保存在本地");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                url2bitmap(mImrUrl);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @OnClick(R.id.titleview_title)
    public void onClick() {
        finish();
    }

    public void url2bitmap(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bm = null;
                try {
                    URL iconUrl = new URL(url);
                    URLConnection conn = iconUrl.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;
                    int length = http.getContentLength();
                    conn.connect();
                    // 获得图像的字符流
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is, length);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();
                    if (bm != null) {
                        save2Album(url, bm);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void save2Album(String url, Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "yunju");
        if (!appDir.exists()) appDir.mkdir();
        String[] str = url.split("/");
        String fileName = str[str.length - 1];
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            onSaveSuccess(file);
        } catch (IOException e) {
            Toast.makeText(ShareFriendActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void onSaveSuccess(final File file) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Uri imageUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(ShareFriendActivity.this, "com.yunju.app.fileprovider", file);
                } else {
                    imageUri = Uri.fromFile(file);
                }
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                }
                intent.setData(imageUri);
                sendBroadcast(intent);
                Toast.makeText(ShareFriendActivity.this, "保存成功" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
