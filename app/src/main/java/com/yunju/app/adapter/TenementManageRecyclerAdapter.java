package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.Tenement;
import com.yunju.app.interfac.OnRecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TenementManageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Tenement> list;
    private int flag; //1:房源管理 2：复制房源列表
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_SECTION = 2;

    public TenementManageRecyclerAdapter(Context context, List<Tenement> list, int flag) {
        this.mContext = context;
        this.list = list;
        this.flag=flag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_tenement_management, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_SECTION) {
            //section
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_section_tenement_management, parent, false);
            return new SectionViewHolder(view);
        }
        throw new RuntimeException("there is no type which matches this type " + viewType + ",please make sure your are using type correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Tenement tenement = list.get(position);
        if (tenement != null) {
            if (holder instanceof ItemViewHolder) {
                final ItemViewHolder itemHolder = (ItemViewHolder) holder;
                if(flag==1) {
                    itemHolder.layBtn.setVisibility(View.VISIBLE);
                }else if(flag==2){
                    itemHolder.layBtn.setVisibility(View.INVISIBLE);
                }
                itemHolder.tvTitle.setText(tenement.getTitle());
                itemHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnEditBtnListener!=null){
                            mOnEditBtnListener.onEdit(tenement);
                        }
                    }
                });
                itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnRecyclerViewItemClickListener != null) {
                            int layoutPosition = itemHolder.getLayoutPosition();
                            mOnRecyclerViewItemClickListener.onRecyclerViewItemClick(v, layoutPosition);
                        }
                    }
                });
            } else if (holder instanceof SectionViewHolder) {
                ((SectionViewHolder) holder).tvSection.setText(tenement.getSection().getText());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getSection()!=null) {
            return TYPE_SECTION;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_tenement_manage_rootview)
        View rootView;
        @BindView(R.id.item_tenement_manage_image)
        ImageView ivImage;
        @BindView(R.id.item_tenement_manage_title)
        TextView tvTitle;
        @BindView(R.id.item_tenement_manage_detail)
        TextView tvDetail;
        @BindView(R.id.item_tenement_manage_editbtn)
        Button btnEdit;
        @BindView(R.id.item_tenement_manage_pricebtn)
        Button btnPrice;
        @BindView(R.id.item_tenement_manage_btnLay)
        View layBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.section_tenement_manage_text)
        TextView tvSection;

        public SectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    private OnEditBtnListener mOnEditBtnListener;
    public void setOnEditBtnListener(OnEditBtnListener onEditBtnListener){
        mOnEditBtnListener=onEditBtnListener;
    }
    public interface OnEditBtnListener{
        void onEdit(Tenement tenement);
    }

}
