package com.yunju.app.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;
import com.yunju.app.entity.HouseDetails;

/**
 * @author: captain
 * Time:  2018/5/10 0010
 * Describe:
 */
public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        HouseDetails.ImgBean banner = ( HouseDetails.ImgBean) path;
        Glide.with(context).load(banner.getImgurl()).into(imageView);
    }
}
