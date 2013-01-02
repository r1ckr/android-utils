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
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	
	ImageView mImgDownloaded;
	Bitmap mBitmap;
	EditText mEtUrl;
	ProgressBar mProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImgDownloaded = (ImageView) findViewById(R.id.img_downloaded);
		mEtUrl = (EditText) findViewById(R.id.et_url);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onClick(View v){
		String uri = mEtUrl.getText().toString();
		new DownloadImageTask().execute(uri);
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
	     }

	 }
	
	
}
