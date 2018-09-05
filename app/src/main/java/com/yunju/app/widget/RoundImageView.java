package com.yunju.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.yunju.app.R;


public class RoundImageView extends AppCompatImageView {

	private Paint paint;
	private int radius  = 0;//圆形的半径
	private boolean isCircle;

	public RoundImageView(Context context) {
		super(context);
		paint = new Paint();
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
		isCircle=typedArray.getBoolean(R.styleable.RoundImageView_isCircle, true);
	}

	/**
	 * * 绘制圆形图片
	 *
	 * @author caizhiming
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		Drawable drawable = getDrawable();
		if (null != drawable) {
			// 获取配置文件中的图像资源
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			Bitmap b;
			if(isCircle){
				b = getCircleBitmap(bitmap, 360);
			}else {
				b = getCircleBitmap(bitmap, 10);
			}
			// 定义一个矩形,宽、高为裁剪过后的圆形bitmap的宽、高
			final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
			// 定义一个矩形,宽、高为资源文件中定义的资源文件的宽、高
			final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
			paint.reset();
			/*************************
			 * Rect src: 是对图片进行裁截，若是空null则显示整个图片
			 * RectF dst：是图片在Canvas画布中显示的区域，
			 * 			    大于src则把src的裁截区放大， 小于src则把src的裁截区缩小。
			 */
			canvas.drawBitmap(b, rectSrc, rectDest, paint);
		}
	}

	/**
	 * * 获取圆形图片方法
	 *
	 * @param bitmap
	 * @param roundPx 360为一个圆形，圆角矩形一般设置成20
	 * @return Bitmap
	 * @author caizhiming
	 */
	private Bitmap getCircleBitmap(Bitmap bitmap, int roundPx) {
		//获得原位图的宽、高
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		//比较原位图宽、高,截取较小者为矩形边长,一半作为圆形半径,
		if(w >= h){
			radius = h / 2;
		}else{
			radius = w / 2;
		}
		//此方法将创建出一个正方形
		Bitmap output = Bitmap.createBitmap(2*radius,
				2*radius, Config.ARGB_8888);
//		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		//设置将原图像绘制到output上
		Canvas canvas = new Canvas(output);
//		final Rect rect = new Rect(0, 0, radius*2, radius*2);
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		// 图像去锯齿,效果不是很显著
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		//绘制出一个圆形/圆角矩形显示区域
//		canvas.drawCircle(radius,radius,radius, paint);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		//将bitmap绘制到圆形显示区域上去
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
}
