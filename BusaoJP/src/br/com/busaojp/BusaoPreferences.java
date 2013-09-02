package br.com.busaojp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class BusaoPreferences extends PreferenceActivity {
	
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.preferencias);
	}
	

}
	
	


