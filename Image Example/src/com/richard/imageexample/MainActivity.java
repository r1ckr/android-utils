package com.richard.imageexample;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	
	ImageView mImgDownloaded;
	Bitmap mBitmap;
	EditText mEtUrl;
	ProgressBar mProgressBar;
	Button mBtnOpaque;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImgDownloaded = (ImageView) findViewById(R.id.img_downloaded);
		mEtUrl = (EditText) findViewById(R.id.et_url);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		mBtnOpaque = (Button) findViewById(R.id.btn_change_opaque);
	}
	
	@SuppressWarnings("deprecation")
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.btn_download:
			String uri = mEtUrl.getText().toString();
			new DownloadImageTask().execute(uri);
			break;
		case R.id.btn_change_opaque:
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
				mImgDownloaded.setAlpha(100);
			}else {
				mImgDownloaded.setImageAlpha(100);
			}
			break;
		}
		
	}

	private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
		
		@Override
		protected void onPreExecute() {
			mProgressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			
			URL myFileUrl =null;          
			String myUrl= params[0];
	        try {
	             myFileUrl= new URL(myUrl);
	        } catch (MalformedURLException e) {
	             // TODO Auto-generated catch block
	             e.printStackTrace();
	        }
	        try {
	             HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
	             conn.setDoInput(true);
	             conn.connect();
	             InputStream is = conn.getInputStream();
	             
	             return BitmapFactory.decodeStream(is);
	             
	        } catch (IOException e) {
	             // TODO Auto-generated catch block
	             e.printStackTrace();
	        }
			return null;
		}
		
		protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(Bitmap result) {
	    	 mProgressBar.setVisibility(View.INVISIBLE);
	    	 mImgDownloaded.setImageBitmap(result);
	    	 mBtnOpaque.setEnabled(true);
	     }

	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}	
	
}
