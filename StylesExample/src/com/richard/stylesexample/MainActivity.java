package com.richard.stylesexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Esta hecho siguiendo este tutorial:
 * http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/
 * 
 * */
public class MainActivity extends Activity {
	String[] items = new String[]{"Item 1", "Item 2", "Item 3"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lv = (ListView) findViewById(R.id.list);
		lv.setAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1, items));
		
	}
	
	class MyAdapter extends ArrayAdapter<String>{

		public MyAdapter(Context context, int textViewResourceId,
				String[] items) {
			super(context, textViewResourceId, items);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			

				//El LayoutInflater sirve para insanciar un archivo layout XML y asi trabajar con él
				LayoutInflater inflater = getLayoutInflater();
				
				//Metemos en la variable row el layout R.layout.row, que tiene un titulo, una dirección y una imagen 
				row=inflater.inflate(R.layout.list_row, null);
				
				TextView tv=(TextView) row.findViewById(R.id.title);
				tv.setText(items[position]);
			return row;
		}
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		return true;
	}

}
