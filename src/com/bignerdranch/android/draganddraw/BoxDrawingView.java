package com.bignerdranch.android.draganddraw;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


@SuppressLint("ViewConstructor")
public class BoxDrawingView<mBoxes> extends View {
	public static final String TAG = "BoxDrawingView";
	private Paint mBoxPaint;
	private Paint mBackgroundPaint;
	
	private Box mCurrentBox;
	private ArrayList<Box> mBoxes = new ArrayList<Box>();
	
	public BoxDrawingView(Context context) {
		this(context, null);
	}
	public boolean onTouchEvent(MotionEvent event) {
		PointF curr = new PointF(event.getX(), event.getY());
		
		Log.i(TAG, "Received event at x=" + curr.x + ", y=" + curr.y + ":");
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, " ACTION_DOWN");
			mCurrentBox = new Box(curr);
			mBoxes.add(mCurrentBox);
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, " ACTION_MOVE");
			if(mCurrentBox != null) {
				mCurrentBox.setCurrent(curr);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.i(TAG, " ACTION_UP");
			mCurrentBox = null;
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.i(TAG, " ACTION_CANCEL");
			mCurrentBox = null;
			break;
		}
		return true;
	}
	
	public BoxDrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mBoxPaint = new Paint();
		mBoxPaint.setColor(0x22ff0000);
		
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(0xfff8efe0);
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawPaint(mBackgroundPaint);
		
		for (Box box: mBoxes) {
			float left = Math.min(box.getOrigin().x, box.getCurrent().x);
			float right = Math.max(box.getOrigin().x, box.getCurrent().x);
			float top = Math.min(box.getOrigin().y, box.getCurrent().y);
			float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);
			
			canvas.drawRect(left, top, right, bottom, mBoxPaint);
		}
	}
}
