/**
 * @author Richard Pablo
 * 
 * Cuando inicializa entra en el constructor, luego onSizeChanged(), luego onDraw()
 * Cuando se hace un invalidate() entra directamente en el onDraw sin pasar por el onSizeChanged()
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
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.rickrsoft.drawings.R;

public class MyProgressBar extends View{
	private int mProgress;
	private int mStartColor;
	private int mEndColor;
	private String mLeftText;
	private String mRightText;
	private String mVariableText;
	private Typeface mFontStyle;
	
	private Paint mPaint;
	private Paint mStrokePaint;
	private Paint mPaintText;
	
	private Shader mProgressShader;
	private RectF mProgressRectangle;
	
	private Shader mBackgroundShader; 
	private RectF mBackgroundRectangle;
	
	private Shader mFrameShader; //Shader para el marco cromado
	private RectF mFrameRectangle;
	
	private Shader mShadowShader;
	private RectF mShadowRectangle;
	
	private float mWidth, mBoxHeight, mHeight, mAnchoMarco, mMiddlePoint;
	private float[] mPointersCoords= new float[4];
	
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
		
		// Obtenemos los parametros y luego hacemos un recycle al objeto
		// TypeArray, por eso se usa el bloque try
		try {
			mStartColor = a.getColor(R.styleable.MyProgressBar_startColor, Color.WHITE);
			mEndColor = a.getColor(R.styleable.MyProgressBar_endColor, Color.GRAY);
			mLeftText = a.getString(R.styleable.MyProgressBar_leftText);
			mRightText = a.getString(R.styleable.MyProgressBar_rightText);
			mVariableText = a.getString(R.styleable.MyProgressBar_variableText);
			mProgress = a.getInteger((R.styleable.MyProgressBar_progress), 50);

		} finally {
			a.recycle();
		}
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
            mStrokePaint.setAntiAlias(true);
            
            mPaintText = new Paint();
            mPaintText.setStyle(Paint.Style.FILL);
            mPaintText.setTypeface(mFontStyle);
            mPaintText.setTextSize((mBoxHeight/4));

            float centerX = mWidth/2;
    	    float centerY = mBoxHeight/2;
    	    mAnchoMarco = mBoxHeight/5;
    	    mMiddlePoint = ((mProgress*(mWidth-(mAnchoMarco*2)))/100)+mAnchoMarco;
    	    
    	    //Hacemos el marco
    	    mFrameRectangle = new RectF(0.0f, 0.0f, mWidth, mBoxHeight);

    	    int[] colors = {Color.WHITE, getResources().getColor(R.color.border_grey),getResources().getColor(R.color.border_grey), Color.WHITE};
    	    float[] positions = {0.0f,0.08f, 0.4f, 1.0f};
    	    mFrameShader = new LinearGradient(centerX, 0, centerX, mBoxHeight, colors, positions, Shader.TileMode.CLAMP);
    	    
    	    //Hacemos la linea del borde del marco
    	    mStrokePaint.setStyle(Paint.Style.STROKE);
    	    mStrokePaint.setStrokeWidth(2);
    	    mStrokePaint.setColor(getResources().getColor(R.color.border_grey));
    	    
    	    //Hacemos el background
    	    mBackgroundShader = new LinearGradient(mMiddlePoint, centerY, mWidth-mAnchoMarco, centerY, getResources().getColor(R.color.background_dark), getResources().getColor(R.color.background_light), Shader.TileMode.MIRROR);
    	    mBackgroundRectangle = new RectF(mMiddlePoint, mAnchoMarco, mWidth-mAnchoMarco, mBoxHeight-mAnchoMarco);
            
    	    //Hacemos el progress
            mProgressShader = new LinearGradient(mAnchoMarco, centerY, mMiddlePoint, centerY, mStartColor, mEndColor, Shader.TileMode.MIRROR);
            mProgressRectangle = new RectF(mAnchoMarco, mAnchoMarco, mMiddlePoint, mBoxHeight-mAnchoMarco);
            
            //Hacemos la sombra interior
            mShadowShader = new LinearGradient(mWidth/2, mAnchoMarco, mWidth/2, mBoxHeight-mAnchoMarco, new int[]{getResources().getColor(R.color.background_dark),Color.TRANSPARENT,Color.TRANSPARENT},new float[]{0.0f,0.4f,1.0f} , Shader.TileMode.MIRROR);
            mShadowRectangle = new RectF(mAnchoMarco, mAnchoMarco, mWidth-mAnchoMarco, mBoxHeight-mAnchoMarco);
            
            mPointersCoords = new float[]{mMiddlePoint, mAnchoMarco, mMiddlePoint, mHeight-mAnchoMarco-4};
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

        //Dibujamos el marco
        mPaint.setShader(mFrameShader);
        canvas.drawRect(mFrameRectangle, mPaint);
        canvas.drawRect(mFrameRectangle, mStrokePaint);
        
        //Dibujamos el background
        mPaint.setShader(mBackgroundShader);
        canvas.drawRect(mBackgroundRectangle, mPaint);
        
        //Dibujamos el progress
        mPaint.setShader(mProgressShader);
        canvas.drawRect(mProgressRectangle, mPaint);
        
        //Dibujamos la sombra interior
        mPaint.setAlpha(50);
        mPaint.setShader(mShadowShader);
        canvas.drawRect(mShadowRectangle, mPaint);
        
        
        //Dibujamos los marcadores:
        //Dibujamos el marcador del medio:
        
        mStrokePaint.setColor(getResources().getColor(R.color.background_dark));
        mStrokePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintText.setColor((mEndColor));
        canvas.drawLines(mPointersCoords, mStrokePaint);
        canvas.drawCircle(mPointersCoords[2], mPointersCoords[3]+4, 4.0f, mStrokePaint);
        if(mMiddlePoint<(mWidth/2)){
        	canvas.drawText(mVariableText, 0, mVariableText.length(), mPointersCoords[2]+8, mPointersCoords[3]+8, mPaintText);
        }else{
        	canvas.drawText(mVariableText, 0, mVariableText.length(), mPointersCoords[2]-8-mPaintText.measureText(mVariableText), mPointersCoords[3]+8, mPaintText);
        	
        }
        
        //Seteamos los colores para los marcadores de los extremos:
        mStrokePaint.setColor(getResources().getColor(R.color.border_grey));
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(getResources().getColor(R.color.border_grey));
        
        //Dibujamos el marcador de la izquierda
        mPointersCoords[0]=mAnchoMarco;
        mPointersCoords[2]=mAnchoMarco;
        canvas.drawLines(mPointersCoords, mStrokePaint);
        canvas.drawCircle(mPointersCoords[2], mPointersCoords[3]+4, 4.0f, mStrokePaint); 
        if(mMiddlePoint>(mAnchoMarco+mPaintText.measureText(mLeftText)+8)){
        	canvas.drawText(mLeftText, 0, mLeftText.length(), mPointersCoords[2]+8, mPointersCoords[3]+8, mPaintText);
        }
        
        //Dibujamos el marcador de la derecha
        mPointersCoords[0]=mWidth-mAnchoMarco;
        mPointersCoords[2]=mWidth-mAnchoMarco;
        canvas.drawLines(mPointersCoords, mStrokePaint);
        canvas.drawCircle(mPointersCoords[2], mPointersCoords[3]+4, 4.0f, mStrokePaint);
        if((mMiddlePoint)<(mPointersCoords[2]-8-mPaintText.measureText(mRightText))){
        	canvas.drawText(mRightText, 0, mRightText.length(), mPointersCoords[2]-8-mPaintText.measureText(mRightText), mPointersCoords[3]+8, mPaintText);
        }
        
        super.onDraw(canvas);
    }
	
	public void setProgress(int progress) {
        mProgress = progress;
        init();
        invalidate();
    }

	public int getProgress() {
		return mProgress;
	}

	public int getStartColor() {
		return mStartColor;
	}

	public void setStartColor(int startColor) {
		mStartColor = startColor;
		init();
        invalidate();
	}

	public int getEndColor() {
		return mEndColor;
	}

	public void setEndColor(int endColor) {
		mEndColor = endColor;
		init();
        invalidate();
	}

	public String getLeftText() {
		return mLeftText;
	}

	public void setLeftText(String leftText) {
		mLeftText = leftText;
		init();
        invalidate();
	}

	public String getRightText() {
		return mRightText;
	}

	public void setRightText(String rightText) {
		this.mRightText = rightText;
		init();
        invalidate();
	}

	public String getVariableText() {
		return mVariableText;
	}

	public void setVariableText(String variableText) {
		mVariableText = variableText;
		init();
        invalidate();
	}

	public Typeface getFontStyle() {
		return mFontStyle;
	}

	public void setFontStyle(Typeface fontStyle) {
		mFontStyle = fontStyle;
		init();
        invalidate();
	}

}
