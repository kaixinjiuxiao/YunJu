package com.yunju.app.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yunju.app.R;
import com.yunju.app.adapter.ImagePickerAdapter;
import com.yunju.app.adapter.UploadImageAdapter;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.util.GlideImageLoader;
import com.yunju.app.widget.MyItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: captain
 * Time:  2018/7/3 0003
 * Describe:
 */
public class EditTenementImageActivity extends BaseActivity {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList;
    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.titleview_btnRight)
    Button mTitleviewBtnRight;
    @BindView(R.id.receclerview_upload_img)
    RecyclerView mRececlerviewUploadImg;
    @BindView(R.id.relative_upload)
    RelativeLayout mRelativeUpload;
    private PopupWindow popupWindow;
    private UploadImageAdapter mImageAdapter;
     private int maxImgCount =50;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_tenement_image;
    }

    @Override
    protected void initView() {
         mTitleviewTitle.setText("请为每个房间提供美照");
         mTitleviewBtnRight.setText("保存");
         mTitleviewBtnRight.setVisibility(View.VISIBLE);
        initImagePicker();
        selImageList = new ArrayList<>();
        mImageAdapter = new UploadImageAdapter(this,selImageList);
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(new ImagePickerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        mRececlerviewUploadImg.setLayoutManager(new GridLayoutManager(this, 2));
        mRececlerviewUploadImg.setAdapter(mImageAdapter);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }



    @OnClick({R.id.titleview_btnLeft, R.id.titleview_btnRight, R.id.relative_upload, R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleview_btnLeft:
                finish();
                break;
            case R.id.titleview_btnRight:
                break;
            case R.id.relative_upload:
                showPopupWindow();
                setBackgroundAlpha(this,0.5f);
                break;
            case R.id.btn_upload:
                break;
        }
    }


    private void showPopupWindow() {
        View layout = View.inflate(EditTenementImageActivity.this, R.layout.layout_bottom_img, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true); // 点击空白处时，隐藏掉POP窗口
        popupWindow.setOutsideTouchable(true); // 点击外部时，隐藏掉POP窗口
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
        popupWindow.showAtLocation(EditTenementImageActivity.this.findViewById(R.id.uploadHouseImage), Gravity.BOTTOM, 20, 0);
        TextView ablum = (TextView) layout.findViewById(R.id.ablum);
        TextView carma = (TextView) layout.findViewById(R.id.carmea);
        carma.setVisibility(View.GONE);
        TextView cancel = (TextView) layout.findViewById(R.id.cancle);
        ablum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(EditTenementImageActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                mRelativeUpload.setVisibility(View.GONE);
                mRececlerviewUploadImg.setVisibility(View.VISIBLE);
                popupWindow.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setBackgroundAlpha(EditTenementImageActivity.this, 1.0f);
            }
        });
}


    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                   // adapter.setImages(selImageList);
                    mImageAdapter.notifyDataSetChanged();
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }
}
