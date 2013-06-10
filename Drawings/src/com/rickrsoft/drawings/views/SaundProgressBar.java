/**
 * @author Richard Pablo
 * */

/* 
 * http://www.jagsaund.com/blog/2011/11/6/customizing-your-progress-bar-part-one.html
 * http://www.jagsaund.com/blog/2011/11/25/customizing-your-progress-bar-part-two.html
 * */
package com.rickrsoft.drawings.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.rickrsoft.drawings.R;

public class SaundProgressBar extends ProgressBar {

	public SaundProgressBar(Context context) {
		super(context);
	}

	public SaundProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SaundProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public synchronized void setProgress(int progress) {
		super.setProgress(progress);

		// the setProgress super will not change the details of the progress bar
		// anymore so we need to force an update to redraw the progress bar
		invalidate();
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// update the size of the progress bar and overlay
		updateProgressBar();

		// paint the changes to the canvas
		super.onDraw(canvas);
	}

	private float getScale(int progress) {
		float scale = getMax() > 0 ? (float) progress / (float) getMax() : 0;

		return scale;
	}

	/**
	 * Instead of using clipping regions to uncover the progress bar as the
	 * progress increases we increase the drawable regions for the progress bar
	 * and pattern overlay. Doing this gives us greater control and allows us to
	 * show the rounded cap on the progress bar.
	 */
	private void updateProgressBar() {
		Drawable progressDrawable = getProgressDrawable();

		if (progressDrawable != null
				&& progressDrawable instanceof LayerDrawable) {
			LayerDrawable d = (LayerDrawable) progressDrawable;

			final float scale = getScale(getProgress());

			// get the progress bar and update it's size
			Drawable progressBar = d.findDrawableByLayerId(R.id.progress);

			final int width = d.getBounds().right - d.getBounds().left;

			if (progressBar != null) {
				Rect progressBarBounds = progressBar.getBounds();
				progressBarBounds.right = progressBarBounds.left + (int) (width * scale + 0.5f);
				progressBar.setBounds(progressBarBounds);
			}

			// get the pattern overlay
			Drawable patternOverlay = d.findDrawableByLayerId(R.id.pattern);

			if (patternOverlay != null) {
				if (progressBar != null) {
					// we want our pattern overlay to sit inside the bounds of
					// our progress bar
					Rect patternOverlayBounds = progressBar.copyBounds();
					final int left = patternOverlayBounds.left;
					final int right = patternOverlayBounds.right;

					patternOverlayBounds.left = (left + 1 > right) ? left : left + 1;
					patternOverlayBounds.right = (right > 0) ? right - 1 : right;
					patternOverlay.setBounds(patternOverlayBounds);
				} else {
					// we don't have a progress bar so just treat this like the
					// progress bar
					Rect patternOverlayBounds = patternOverlay.getBounds();
					patternOverlayBounds.right = patternOverlayBounds.left + (int) (width * scale + 0.5f);
					patternOverlay.setBounds(patternOverlayBounds);
				}
			}
		}
	}

}
