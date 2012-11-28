package com.richard.preferences.view;

import com.richard.preferences.R;
import com.richard.preferences.prefs.EditPreferences;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class PrefExample extends Activity {
	
	public static final String STRING_1 = "string_1";
	
	private TextView mCheckbox = null;
	private TextView mRingtone = null;
	private TextView mCheckbox2 = null;
	private TextView mText = null;
	private TextView mList = null;
	private TextView mPrefilledList = null;
	private EditText mEditText;
	SharedPreferences mPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pref_example);
		
		mCheckbox=(TextView)findViewById(R.id.checkbox);
		mRingtone=(TextView)findViewById(R.id.ringtone);
		mCheckbox2=(TextView)findViewById(R.id.checkbox2);
		mText=(TextView)findViewById(R.id.text);
		mList=(TextView)findViewById(R.id.list);
		mPrefilledList=(TextView)findViewById(R.id.prefilled_list);
		mEditText=(EditText) findViewById(R.id.text_to_save);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();

		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		mCheckbox.setText(((Boolean) mPrefs.getBoolean("prefs1_checkbox", false)).toString());
		mRingtone.setText(mPrefs.getString("prefs1_ringtone", "<unset>"));
		mCheckbox2.setText(((Boolean) mPrefs.getBoolean("prefs2_checkbox", false)).toString());
		mText.setText(mPrefs.getString("prefs1_text", "<unset>"));
		mList.setText(mPrefs.getString("prefs1_list", "<unset>"));
		mPrefilledList.setText(mPrefs.getString("prefs2_list", "<unset>"));
		mEditText.setText(mPrefs.getString("hidden_preference", "<unset>"));
	}
	
	

	@Override
	protected void onPause() {
		String text=mEditText.getText().toString();
		
		// Salvamos el texto del EditText en una preferencia oculta, para luego llamarla
		SharedPreferences.Editor editor = mPrefs.edit();
	    editor.putString("hidden_preference", text);
	    editor.commit(); // Importante!!
	    
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pref_example, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, EditPreferences.class));
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
