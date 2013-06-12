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
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.rickrsoft.drawings.R;

public class MyProgressBar extends View{
	private int mStartColor;
	private int mEndColor;
	private int mShadowColor;
	private String mLeftText;
	private String mRightText;
	private String mVariableText;
	private Paint mPaint;
	LinearGradient mColorProgress;
	LinearGradient mGradientColorShadow;
	Shader mColorBackground;
	RectF mRectanguloBackground;
	RectF mRectanguloProgress;
	RectF mRectanguloMarco;
	LinearGradient mGradientColorMarco;
	Paint mStrokePaint;
	Paint mPaintText;
	float mWidth, mBoxHeight, mHeight;
	float mAnchoMarco;
	int mProgress = 1;
	float[] mLinePoints= new float[4];
	
	public MyProgressBar(Context context){
    	super(context);
    	
    }
    
	public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MyProgressBar, 0, 0);
		
		// Obtenemos los parametros y luego hacemos un recicle al objeto
		// TypeArray, por eso se usa el bloque try
		try {
			mStartColor = a.getColor(R.styleable.MyProgressBar_startColor, Color.WHITE);
			mEndColor = a.getColor(R.styleable.MyProgressBar_endColor, Color.GRAY);
			mShadowColor = a.getColor(R.styleable.MyProgressBar_shadowColor, Color.BLACK);
			mLeftText = a.getString(R.styleable.MyProgressBar_leftText);
			mRightText = a.getString(R.styleable.MyProgressBar_rightText);
			mVariableText = a.getString(R.styleable.MyProgressBar_variableText);
			mProgress = a.getInteger((R.styleable.MyProgressBar_progress), 50);

		} finally {
			a.recycle();
		}
		
		

	}
	
	public void setProgress(int progress) {
        mProgress = progress;
        init();
        invalidate();
    }
	
	/**
     * Initialize the control. This code is in a separate method so that it can be
     * called from both constructors.
     */
    private void init() {
    	
    		mPaint=new Paint();
    	    mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
            
            mStrokePaint= new Paint();
            mStrokePaint.setStyle(Paint.Style.STROKE);
           // mStrokePaint.setAntiAlias(true);
            
            mPaintText = new Paint();
            
            
            
            float centerX = mWidth/2;
    	    float centerY = mBoxHeight/2;
    	    mAnchoMarco = mBoxHeight/5;
    	    float middlePoint = ((mProgress*(mWidth-(mAnchoMarco*2)))/100)+mAnchoMarco;
    	    
    	    //Hacemos el rectangulo del marco:
    	    mRectanguloMarco = new RectF(0.0f, 0.0f, mWidth, mBoxHeight);
    	    Color.parseColor("#e2e2e2");
    	    int[] colors = {Color.WHITE, Color.parseColor("#e2e2e2"),Color.parseColor("#e2e2e2"), Color.WHITE};
    	    float[] positions = {0.0f,0.08f, 0.4f, 1.0f};
    	    mGradientColorMarco = new LinearGradient(centerX, 0, centerX, mBoxHeight, colors, positions, Shader.TileMode.CLAMP);
    	    
    	    //Hacemos la linea del borde del marco:
    	    mStrokePaint.setStyle(Paint.Style.STROKE);
    	    mStrokePaint.setStrokeWidth(2);
    	    mStrokePaint.setColor(Color.parseColor("#e2e2e2"));
    	    
    	    
    	    //Hacemos el background:
    	    mColorBackground = new LinearGradient(middlePoint, centerY, mWidth-mAnchoMarco, centerY, Color.parseColor("#3a3a3a"), Color.parseColor("#8a8a8a"), Shader.TileMode.MIRROR);
    	    mRectanguloBackground = new RectF(middlePoint, mAnchoMarco, mWidth-mAnchoMarco, mBoxHeight-mAnchoMarco);
            
    	    //Hacemos el progress:
            mColorProgress = new LinearGradient(0+mAnchoMarco, centerY, middlePoint, centerY, mStartColor, mEndColor, Shader.TileMode.MIRROR);
            mRectanguloProgress = new RectF(mAnchoMarco, mAnchoMarco, middlePoint, mBoxHeight-mAnchoMarco);
            
            mLinePoints = new float[]{middlePoint, mAnchoMarco, middlePoint, mHeight-mAnchoMarco-4};
            

        }
    
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		//Calculamos el alto y el ancho del area
		mWidth    = (float)w;
		mHeight = (float)h;
	    mBoxHeight   = ((float)h/3)*2;
	    init();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	
	
	@Override
    protected void onDraw(Canvas canvas) {
        
        
        //Dibujamos el marco:
        mPaint.setShader(mGradientColorMarco);
        
        canvas.drawRect(mRectanguloMarco, mPaint);
        canvas.drawRect(mRectanguloMarco, mStrokePaint);
        
        //Dibujamos el background
        mPaint.setShader(mColorBackground);
        canvas.drawRect(mRectanguloBackground, mPaint);
        
        //Dibujamos el progress
        mPaint.setShader(mColorProgress);
        canvas.drawRect(mRectanguloProgress, mPaint);
        
        
        mStrokePaint.setColor(Color.parseColor("#3a3a3a"));
        mStrokePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        
        mPaintText.setColor((mEndColor));
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setTextSize((mBoxHeight/4));
        
        //Dibujamos los marcadores
        canvas.drawLines(mLinePoints, mStrokePaint);
        mStrokePaint.setAntiAlias(true);
        canvas.drawCircle(mLinePoints[2], mLinePoints[3]+4, 4.0f, mStrokePaint);
        mVariableText=mProgress+"%";
        canvas.drawText(mVariableText, 0, mVariableText.length(), mLinePoints[2]+8, mLinePoints[3]+8, mPaintText);
        
        mStrokePaint.setColor(Color.parseColor("#dadada"));
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(Color.parseColor("#dadada"));
        
        mLinePoints[0]=mAnchoMarco;
        mLinePoints[2]=mAnchoMarco;
        canvas.drawLines(mLinePoints, mStrokePaint);
        mStrokePaint.setAntiAlias(true);
        canvas.drawCircle(mLinePoints[2], mLinePoints[3]+4, 4.0f, mStrokePaint); 
        
        canvas.drawText(mLeftText, 0, mLeftText.length(), mLinePoints[2]+8, mLinePoints[3]+8, mPaintText);
        
        
        mLinePoints[0]=mWidth-mAnchoMarco;
        mLinePoints[2]=mWidth-mAnchoMarco;
        canvas.drawLines(mLinePoints, mStrokePaint);
        mStrokePaint.setAntiAlias(true);
        canvas.drawCircle(mLinePoints[2], mLinePoints[3]+4, 4.0f, mStrokePaint);

        
        canvas.drawText(mRightText, 0, mRightText.length(), mLinePoints[2]-8-mPaintText.measureText(mRightText), mLinePoints[3]+8, mPaintText);
        super.onDraw(canvas);
    }

}
