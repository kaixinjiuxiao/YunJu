package com.yunju.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.entity.Trace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sm on 2018/3/26 0026.
 */
public class TraceRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<Trace> traceList = new ArrayList<>(1);

    public TraceRecyclerAdapter(Context context, List<Trace> traceList) {
        inflater = LayoutInflater.from(context);
        this.traceList = traceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_recyclerview_trace, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        Trace trace = traceList.get(position);
        if (position == 0) {
            itemHolder.ivDot.setImageResource(R.drawable.icon_trace_blue);
        } else if (position == traceList.size() - 1) {
            itemHolder.ivDot.setImageResource(R.drawable.icon_trace_gray);
            itemHolder.tvRemind.setVisibility(View.GONE);
        }else{
            itemHolder.ivDot.setImageResource(R.drawable.icon_trace_red);
        }
        itemHolder.tvStatus.setText(trace.getStatus());
        itemHolder.tvRemind.setText(trace.getRemind());
    }

    @Override
    public int getItemCount() {
        return traceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_trace_dot)
        ImageView ivDot;
        @BindView(R.id.item_trace_status)
        TextView tvStatus;
        @BindView(R.id.item_trace_remind)
        TextView tvRemind;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
