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
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	ImageView imgDownloaded;
	Bitmap bmImg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgDownloaded = (ImageView) findViewById(R.id.img_downloaded);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onClick(View v){
		new DownloadImageTask().execute("http://chart.finance.yahoo.com/z?s=EURUSD=X");
	}

	private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {

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
	    	 imgDownloaded.setImageBitmap(result);
	     }
	 }
	
	
}
