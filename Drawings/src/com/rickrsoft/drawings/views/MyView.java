/**
 * @author Richard Pablo
 * */

package com.rickrsoft.drawings.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

import com.rickrsoft.drawings.R;

public class MyView extends View{
	private int mStartColor;
	private int mEndColor;
	private int mShadowColor;
	private String mLeftText;
	private String mRightText;
	private String mVariableText;
	private Paint mPaint;
	LinearGradient mGradientColor;
	LinearGradient mGradientColorShadow;
	Shader mColorBackground;
	RectF mRectangulo;
	RadialGradient mGradientCircle;
	float mWidth, mHeight;
	
	public MyView(Context context){
    	super(context);
    	init();
    }
    
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MyView, 0, 0);
		
		// Obtenemos los parametros y luego hacemos un recicle al objeto
		// TypeArray, por eso se usa el bloque try
		try {
			mStartColor = a.getColor(R.styleable.MyView_startColor, Color.WHITE);
			mEndColor = a.getColor(R.styleable.MyView_endColor, Color.GRAY);
			mShadowColor = a.getColor(R.styleable.MyView_shadowColor, Color.BLACK);
			mLeftText = a.getString(R.styleable.MyView_leftText);
			mRightText = a.getString(R.styleable.MyView_rightText);
			mVariableText = a.getString(R.styleable.MyView_variableText);

		} finally {
			a.recycle();
		}
		
		init();

	}
	
	
	
	/**
     * Initialize the control. This code is in a separate method so that it can be
     * called from both constructors.
     */
    private void init() {
    	
    		mPaint=new Paint();
    	    mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
            
        }
    
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth    = (float)w;
	    mHeight   = (float)h;
	    float centerX = mWidth/2;
	    float centerY = mHeight/2;
	    
        mGradientColor = new LinearGradient(0, centerY, mWidth, centerY, mStartColor, mEndColor, Shader.TileMode.MIRROR);
        mPaint.setShader(mGradientColor);
        mGradientColorShadow = new LinearGradient(centerX, 0, centerX, mHeight, Color.TRANSPARENT, mShadowColor, Shader.TileMode.MIRROR);
        mColorBackground = new LinearGradient(centerX, 0, centerX, mHeight, Color.GRAY, Color.GRAY, Shader.TileMode.MIRROR);
        
        mRectangulo = new RectF(0.0f, (mHeight/4), mWidth, (mHeight/4)*3);
        
        mGradientCircle = new RadialGradient(centerX-4, centerY, mHeight, Color.WHITE, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        
		super.onSizeChanged(w, h, oldw, oldh);
	}

	
	
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        
        mPaint.setShader(mColorBackground);
        canvas.drawRoundRect(mRectangulo, mHeight/4, mHeight, mPaint);
        mRectangulo = new RectF(0.0f, (mHeight/4), mWidth/2, (mHeight/4)*3);

        mPaint.setShader(mGradientColor);
        canvas.drawRoundRect(mRectangulo, mHeight/4, mHeight, mPaint);
        mPaint.setShader(mGradientColorShadow);
        mPaint.setAlpha(100);
        canvas.drawRoundRect(mRectangulo, mHeight/4, mHeight, mPaint);
        mPaint.setShader(mGradientCircle);
        mPaint.setAlpha(150);
        canvas.drawCircle(mWidth/2, mHeight/2, mHeight/2, mPaint);

    }

}
