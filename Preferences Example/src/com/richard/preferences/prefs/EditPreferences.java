package com.richard.preferences.prefs;

import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import com.richard.preferences.R;

public class EditPreferences extends PreferenceActivity {
  @SuppressWarnings("deprecation")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    /* Desde la vista se llama a este método, si la version es anterior a HONEYCOMB 
     * cargará las preferencias una detrás de otra, sin cabeceras*/
    if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB) {
      addPreferencesFromResource(R.xml.preferences1);
      addPreferencesFromResource(R.xml.preferences2);
      
      /* Este es el llenado de la lista de preferencias para las versiones anteriores a HONEYCOMB*/
      ListPreference lp = (ListPreference) findPreference("prefs2_list");
      lp.setEntries(new String[]{"Item 1", "Item 2", "Item 3"});
      lp.setEntryValues(new String[]{"1","2","3"});
    }
  }
  
  @Override
  public void onBuildHeaders(List<Header> target) {
	  /*Este metodo solo es llamado a partir de la version HONEYCOMB, que es la que soporta
	   * los fragments y headers de preferencias*/
    loadHeadersFromResource(R.xml.preference_headers, target);
  }
}
