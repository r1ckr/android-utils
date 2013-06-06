/**
 * @author Richard Pablo
 * */

package com.rickrsoft.drawings.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.rickrsoft.drawings.R;

public class MyView extends View{
	private int mStartColor;
	private int mEndColor;
	private String mLeftText;
	private String mRightText;
	private String mVariableText;
	private Paint mPaint;
	
	public MyView(Context context){
    	super(context);
    	init();
    }
    
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.PieChart, 0, 0);
		
		// Obtenemos los parametros y luego hacemos un recicle al objeto
		// TypeArray, por eso se usa el bloque try
		try {
			mStartColor = a.getColor(R.styleable.MyView_startColor, Color.GRAY);
			mEndColor = a.getColor(R.styleable.MyView_endColor, Color.BLACK);
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
        }
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        
        Float height = (float)canvas.getHeight();
        Float width = (float)canvas.getWidth();
        Paint este = new Paint();
        este.setColor(getResources().getColor(android.R.color.black));
        //canvas.drawCircle(40.0f, 40.0f, 30.0f, este);
        RectF rectangulo = new RectF(0.0f, 0.0f, width, height);
        canvas.drawRect(rectangulo, este);

    }

}
