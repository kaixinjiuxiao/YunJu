package com.yunju.app.util;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.TextView;

import com.yunju.app.R;


/**
 * 对话框工具类
 * @author sm
 *
 */
public class DialogUtil {
	
	
	public static void showNormalDialog(Context context,String title,String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton("确定", null);
		builder.show();
	}
	
	
	/**
	 * 创建一个回调对话框
	 * @param context
	 */
	public static void showDialogCallback(Context context,String msg,final DialogListener listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提示");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				listener.onClickPositive();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		builder.create().show();
	}
	
	/**
	 * 创建一个回调对话框,自定义取消按钮
	 * @param context
	 */
	public static void createDialogCallback(Context context,String title,String msg,String positiveBtn, String negativeBtn, final MyDialogListener listener){
		createDialogCallback(context, title, msg, positiveBtn, negativeBtn, true, listener);
	}
	
	public static void createDialogCallback(Context context,String title,String msg,String positiveBtn, String negativeBtn, Boolean cancelable, final MyDialogListener listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(positiveBtn, new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				listener.onClickPositive();
			}
		});
		builder.setNegativeButton(negativeBtn, new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				listener.onClickNegative();
			}
		});
		builder.setCancelable(cancelable);
		builder.create().show();
	}
	
	
	/**
	 * 创建一个回调对话框
	 * @param context
	 */
	public static void showDialogCallback1(Context context,String msg,final DialogListener listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提示");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				listener.onClickPositive();
			}
		});
		builder.create().show();
	}
	
	/**
	 * 创建一个回调对话框
	 * @param context
	 */
	public static void showDialogCallback(Context context,String title,String msg,final DialogListener listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				listener.onClickPositive();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		builder.create().show();
	}
	
	
	/**
	 * 创建一个等待对话框
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog showProgressDialog(Context context,String msg,boolean isCancelable){
		Dialog progressDialog = new Dialog(context, R.style.progress_dialog);
		progressDialog.setContentView(R.layout.dialog_progress);
		progressDialog.setCancelable(isCancelable);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg_tv = (TextView) progressDialog
				.findViewById(R.id.dialog_progress_msg);
		msg_tv.setText(msg);
		progressDialog.show();
		return progressDialog;
	}
	
	public interface DialogListener{
		public void onClickPositive();
	}
	
	public interface MyDialogListener{
		public void onClickPositive();
		public void onClickNegative();
	}
	
}
