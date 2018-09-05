package com.yunju.app.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.yunju.app.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    private Dialog progressDialog;
    private Toast toast;
    protected boolean isActive = true;
    private boolean doubleBack;
    private long lastClickTimeMillis = 0L;
    private long timeInterval = 2000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
//        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutResource());
        isActive = true;
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    public abstract int getLayoutResource();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    public void openActivity(Class<?> cls) {
        openActivity(cls, null);
    }

    public void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> cls, Bundle bundle, int FLAG) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(FLAG);
        startActivity(intent);
    }

    public void toast(String s) {
        if (isActive) {
            if (toast == null) {
                toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            } else {
                toast.setText(s);
            }
            toast.show();
        }
    }

    public void openActivityForResult(Class<?> cls, int requestCode) {
        openActivityForResult(cls, null, requestCode);
    }

    public void openActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    private Fragment mCurrentFragment;
    protected void switchDiffFragmentContent(Fragment toFragment, int resId,
                                             int index, boolean isAnim) {
        if (null == toFragment) {
            return;
        }
        if (null == mCurrentFragment
                || mCurrentFragment.getArguments().getInt("index") != index) {
            FragmentTransaction fragmentTransaction;
            fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            if (isAnim)
                fragmentTransaction.setCustomAnimations(R.anim.push_left_in,
                        R.anim.push_left_out);

            if (!toFragment.isAdded()) {
                Bundle bundle = toFragment.getArguments();
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putInt("index", index);
                toFragment.setArguments(bundle);

                if (null != mCurrentFragment)
                    fragmentTransaction.hide(mCurrentFragment);
                fragmentTransaction.add(resId, toFragment,
                        String.valueOf(index));
                fragmentTransaction.commit();
            } else {
                fragmentTransaction.hide(mCurrentFragment);
                fragmentTransaction.show(toFragment);
                fragmentTransaction.commit();
            }
            mCurrentFragment = toFragment;
        }
    }

    public void setDoubleBack(boolean doubleBack) {
        this.doubleBack = doubleBack;
    }

    /**
     * @param phone
     * 跳转拨打电话页面
     */
    public void callPhone(String phone){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }
    public void setBackgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().setAttributes(lp);
    }

    /**
     * MD5加密
     *
     * @param string
     * @return
     */
    public String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
//    @Override
//    public void onBackPressed() {
//        if (doubleBack) {
//            long currentTime = System.currentTimeMillis();
//            if (currentTime - lastClickTimeMillis > timeInterval) {
//                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
//                lastClickTimeMillis = currentTime;
//            } else {
//                super.onBackPressed();
//            }
//
//        } else {
//            super.onBackPressed();
//        }
//    }


}
