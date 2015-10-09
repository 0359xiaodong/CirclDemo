package com.xuie.circldemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {

	private Bitmap image;
	private int radius;
	private int degree = 0;

	private Paint paint;
	private Matrix matrix;
	private BitmapShader bitmapShader;

	public CircleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomCircle, defStyleAttr, 0);
		int n = a.getIndexCount();

		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomCircle_bg:
				image = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
				break;
			case R.styleable.CustomCircle_speed:
				break;
			}
		}
		a.recycle();
		paint = new Paint();
		matrix = new Matrix();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					degree = (degree < 360) ? degree + 1 : 0;

					try {
						Thread.sleep(100L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					postInvalidate();
				}
			}
		}).start();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		bitmapShader = new BitmapShader(image, TileMode.CLAMP, TileMode.CLAMP);
		float scale = 1.0f;
		int size = Math.min(image.getWidth(), image.getHeight());
		scale = getWidth() * 1.0f / size;
		// �������ű���
		matrix.setScale(scale, scale);
		// ������ת�Ƕ�
		matrix.postRotate(degree, getWidth() / 2, getHeight() / 2);
		// ���ر任����
		bitmapShader.setLocalMatrix(matrix);
		// ����shader
		paint.setShader(bitmapShader);

		// ��Բ
		radius = getWidth() / 2;
		// Log.d("xuie", getWidth() + ", " + image.getWidth() + ", " + getHeight() + ", " + image.getHeight());
		canvas.drawCircle(radius, radius, radius, paint);
	}

}
