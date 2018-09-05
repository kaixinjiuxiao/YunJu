package com.yunju.app.widget;


import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunju.app.R;


public class CustomEditTextShowNum extends LinearLayout {

	private TextView numText;
	private EditText contentExt;
	private int maxNum = 140;
	private int leftNum;

	public CustomEditTextShowNum(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.custom_edittext_view, this);
		numText = (TextView) findViewById(R.id.custom_numtext);
		contentExt = (EditText) findViewById(R.id.custom_edittext);

		contentExt.addTextChangedListener(new MyTextWatcher());
	}
	public void setMaxNum(int num) {
		this.maxNum = num;
		numText.setText("0/" + maxNum);
		contentExt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxNum)});
//		EditTextLengthFilter.lengthFilter(getContext(), contentExt, maxNum, "已超出限制字数");
	}

	public void setContentHint(String hint) {
		contentExt.setHint(hint);
	}
	
	public void setContentText(String text) {
		contentExt.setText(text);
		contentExt.setSelection(text.length());
	}
	
	public String getContentText(){
		return contentExt.getText().toString().trim();
	}

	public void setInputType(int inputType) {
		contentExt.setInputType(inputType);
	}

	public void setMinLines(int minLines){
		contentExt.setMinLines(minLines);
	}

	class MyTextWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
//			numText.setText(leftNum + "/" + maxNum);

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
//			leftNum = maxNum- s.length();
			numText.setText(s.length() + "/" + maxNum);
			
		}

		@Override
		public void afterTextChanged(Editable editable) {
			
		}

	}

}
