package com.richard.preferences.prefs;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

/**Esta clase es llamada desde el xml preference_headers para cargar el fragmento 
 * que contendrá las preferencias, se usará tantas preferencias estén declaradas 
 * con el <extra android:name="resource" ya que es el que se llama abajo*/

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StockPreferenceFragment extends PreferenceFragment {

@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    int res = getActivity().getResources().getIdentifier(getArguments().getString("resource"), "xml",getActivity().getPackageName());

    addPreferencesFromResource(res);
    
    /* Antes de mostrar las preferencias, llenamos la lista, esto tenemos 2 veces,
     * aquí está hecho para las versiones de HONEYCOMB en adelante, en la clase EditPreferences 
     * implementaremos el llenado para las versiones anteriores a HONEYCOMB
     * 
     * En caso de que la lista cambie y el valor que haya seleccionado el usuario ya no exista,
     * esta preferencia seguirá devolviendo el valor que habia seleccionado antes*/
    ListPreference lp = (ListPreference) findPreference("prefs2_list");
    lp.setEntries(new String[]{"Item 1", "Item 2", "Item 3"});
    lp.setEntryValues(new String[]{"1","2","3"});
    
  }
}
