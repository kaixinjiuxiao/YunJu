package com.yunju.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yunju.app.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: captain
 * Time:  2018/7/3 0003
 * Describe:
 */
public class UploadImageAdapter extends RecyclerView.Adapter<UploadImageAdapter.UpLoadImageViewHolder> {

    private Context mContext;
    private List<ImageItem> mData;

    public UploadImageAdapter(Context context, List<ImageItem> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public UpLoadImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_upload_image, parent, false);
        return new UpLoadImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpLoadImageViewHolder holder, int position) {
        ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext,mData.get(position).path, holder.mImg, 0, 0);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class UpLoadImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView mImg;
        @BindView(R.id.imgDelete)
        ImageView mImgDelete;
        @BindView(R.id.imgEdit)
        ImageView mImgEdit;
        public UpLoadImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
