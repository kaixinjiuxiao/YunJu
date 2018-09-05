package com.yunju.app.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.fragment.CollectionFragment;
import com.yunju.app.fragment.MainFragment;
import com.yunju.app.fragment.MessageFragment;
import com.yunju.app.fragment.MineFragment;
import com.yunju.app.util.Constant;
import com.yunju.app.util.SharedPreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tab_radioGroup)
    RadioGroup radioGroupTab;
    private MainFragment mainFragment;
    private CollectionFragment collectionFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setDoubleBack(true);
    }

    @Override
    protected void initData() {
        mainFragment = new MainFragment();
        collectionFragment = new CollectionFragment();
        messageFragment = new MessageFragment();
        mineFragment = new MineFragment();
        switchDiffFragmentContent(mainFragment, R.id.content_fragment, 1, false);
    }

    @Override
    protected void initEvent() {
        radioGroupTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.tab_radioBtn_main:
                        switchDiffFragmentContent(mainFragment, R.id.content_fragment, 1, false);
                        break;
                    case R.id.tab_radioBtn_collection:
                        switchDiffFragmentContent(collectionFragment, R.id.content_fragment, 2, false);
                        break;
                    case R.id.tab_radioBtn_message:
                        switchDiffFragmentContent(messageFragment, R.id.content_fragment, 3, false);
                        break;
                    case R.id.tab_radioBtn_mine:
                        switchDiffFragmentContent(mineFragment, R.id.content_fragment, 4, false);
                        break;
                }
            }
        });
    }


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                if(mainFragment.popupWindowPerson.isShowing()){
                    mainFragment.popupWindowPerson.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                }
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }




}
