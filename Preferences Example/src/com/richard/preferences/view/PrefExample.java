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
	private String mSavedText;
	
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
		
		//En el savedInstanceState se guarda el bundle de esta actividad si se ha 
        //puesto en background, asi que de ahi volvemos a coger el KEY_ROWID
			
		if (savedInstanceState==null){
        	mSavedText = "";
        }else{
        	mSavedText= savedInstanceState.getString(STRING_1);
        }

	}
	
	@Override
	public void onResume() {
		super.onResume();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		mCheckbox.setText(((Boolean) prefs.getBoolean("prefs1_checkbox", false)).toString());
		mRingtone.setText(prefs.getString("prefs1_ringtone", "<unset>"));
		mCheckbox2.setText(((Boolean) prefs.getBoolean("prefs2_checkbox", false)).toString());
		mText.setText(prefs.getString("prefs1_text", "<unset>"));
		mList.setText(prefs.getString("prefs1_list", "<unset>"));
		mPrefilledList.setText(prefs.getString("prefs2_list", "<unset>"));
		mEditText.setText(mSavedText);
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
		/*Aqui vamos a salvar lo que queramos volver a tener si se mata la aplicacion, 
		este médoto no es llamado cuando se presiona el back button*/
		outState.putString(STRING_1, mEditText.getText().toString());
		super.onSaveInstanceState(outState);
		
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
