package com.rickrsoft.drawings;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.rickrsoft.drawings.views.MyProgressBar;

public class MainActivity extends Activity {
	
	MyProgressBar mProgressBar;
	SeekBar mSeekBar;
	ProgressBar mRegularProgress, mMiniProgress, mtunedProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mProgressBar= (MyProgressBar) findViewById(R.id.my_progressBar);
		mRegularProgress = (ProgressBar) findViewById(R.id.regularprogressbar);
		mMiniProgress = (ProgressBar) findViewById(R.id.miniprogressbar);
		mtunedProgress = (ProgressBar) findViewById(R.id.progressBar1);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar1);
		
		mProgressBar.setFontStyle(Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Semibold.ttf"));
		mSeekBar.setOnSeekBarChangeListener(seekBarListener);

//        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
//        pie.addItem("Agamemnon", 2, res.getColor(R.color.seafoam));
//        pie.addItem("Bocephus", 3.5f, res.getColor(R.color.chartreuse));
//        pie.addItem("Calliope", 2.5f, res.getColor(R.color.emerald));
//        pie.addItem("Daedalus", 3, res.getColor(R.color.bluegrass));
//        pie.addItem("Euripides", 1, res.getColor(R.color.turquoise));
//        pie.addItem("Ganymede", 3, res.getColor(R.color.slate));
//
//        ((Button) findViewById(R.id.Reset)).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                pie.setCurrentItem(0);
//            }
//        });
		
		
	}
	
	OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			mProgressBar.setVariableText(progress+"%");
			mProgressBar.setProgress(progress);
			mRegularProgress.setProgress(progress);
			mMiniProgress.setProgress(progress);
			mtunedProgress.setProgress(progress);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
