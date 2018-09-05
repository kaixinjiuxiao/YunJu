package com.yunju.app.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunju.app.R;
import com.yunju.app.base.BaseActivity;
import com.yunju.app.entity.HouseDetails;
import com.yunju.app.widget.ZoomImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: captain
 * Time:  2018/6/14 0014
 * Describe:
 */
public class HouseImageDetailsActivity extends BaseActivity {

    @BindView(R.id.titleview_title)
    TextView mTitleviewTitle;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.house_desc)
    TextView mHouseDesc;
    private List<HouseDetails.ImgBean> mImgBeanList;
    private HouseDetails mHouseDetails;
    private ImageView[] mImageViews;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_houseimg_details;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {
        mHouseDetails = (HouseDetails) getIntent().getExtras().getSerializable("image");
        mImgBeanList = mHouseDetails.getImg();
        mTitleviewTitle.setText(1 + " / " + mImgBeanList.size());
        mImageViews = new ImageView[mImgBeanList.size()];
        mHouseDesc.setText(mImgBeanList.get(0).getImgremark());
        mViewpager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView imageView = new ZoomImageView(
                        getApplicationContext());
                Glide.with(HouseImageDetailsActivity.this).load(mImgBeanList.get(position).getImgurl()).into(imageView);
                container.addView(imageView);
                mImageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mImgBeanList.size();
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTitleviewTitle.setText(position + 1 + " / " + mImgBeanList.size());
                 mHouseDesc.setText(mImgBeanList.get(position).getImgremark());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initEvent() {

    }


    @OnClick(R.id.titleview_btnLeft)
    public void onClick() {
        finish();
    }


}
