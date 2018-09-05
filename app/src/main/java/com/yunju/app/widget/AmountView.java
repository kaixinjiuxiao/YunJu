package com.yunju.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunju.app.R;

import java.util.List;

public class AmountView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "AmountView";
    private int amount = 0;
//    private int goods_storage = 1;

    private OnAmountChangeListener mListener;

    private TextView tvAmount;
    private ImageView ivDecrease;
    private ImageView ivIncrease;

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        tvAmount = findViewById(R.id.etAmount);
        ivDecrease = findViewById(R.id.btnDecrease);
        ivIncrease = findViewById(R.id.btnIncrease);
        ivDecrease.setOnClickListener(this);
        ivIncrease.setOnClickListener(this);
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }
//
//    public void setGoods_storage(int goods_storage) {
//        this.goods_storage = goods_storage;
//    }

    @Override
    public void onClick(View v) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            if (amount > 0) {
                amount--;
                tvAmount.setText(dataList.get(amount));
                if (mListener != null) {
                    mListener.onAmountChange(this, amount);
                }
            }
        } else if (i == R.id.btnIncrease) {
            if (amount < dataList.size()-1) {
                amount++;
                tvAmount.setText(dataList.get(amount));
                if (mListener != null) {
                    mListener.onAmountChange(this, amount);
                }
            }
        }
        if(amount<=0){
            ivDecrease.setImageResource(R.drawable.icon_decrease_unfocus);
        }else{
            ivDecrease.setImageResource(R.drawable.icon_decrease);
        }
        if(amount>=dataList.size()-1){
            ivIncrease.setImageResource(R.drawable.icon_increase_unfocus);
        }else{
            ivIncrease.setImageResource(R.drawable.icon_increase);
        }
//        tvAmount.clearFocus();
    }

//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//        if (s.toString().isEmpty())
//            return;
//        amount = Integer.valueOf(s.toString());
//        if (amount > goods_storage) {
//            etAmount.setText(goods_storage + "");
//            return;
//        }
//
//        if (mListener != null) {
//            mListener.onAmountChange(this, amount);
//        }
//    }

    List<String> dataList;
    public void setDataList(List<String> dataList){
        this.dataList=dataList;
        tvAmount.setText(dataList.get(0));
        ivDecrease.setImageResource(R.drawable.icon_decrease_unfocus);
        if(dataList.size()<=1){
            ivIncrease.setImageResource(R.drawable.icon_increase_unfocus);
        }
    }

    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

    public String getAountText(){
        return tvAmount.getText().toString();
    }
}
