package br.com.busaojp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class BusaoPreferences extends PreferenceActivity {
	private static final String PREFERENCIAS = "preferencias";
	private static BusaoPreferences busao;
	
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.preferencias);
	        busao = this;
	}
	
	public static boolean getMusic(Context context) {
	    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("music", true);
	}
	
	public static void setBackgroundChecked(LinearLayout l){
		l.setBackgroundResource(R.drawable.altbackground);
		SharedPreferences prefs = busao.getSharedPreferences(PREFERENCIAS, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("bg", 1);
		editor.commit();	
	}
	
	public static void setBackgroundUnchecked(LinearLayout l){
		l.setBackgroundResource(R.drawable.fundo);
		SharedPreferences prefs = busao.getSharedPreferences(PREFERENCIAS, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("bg", 2);
		editor.commit();
	}
	
	public static void setFonte(int tamanho, Button b){ 
		b.setTextSize(tamanho);  	 
	}
	
	public static void setColorFonte(String cor, Button b){ 
		b.setTextColor(Color.parseColor(cor));  	 
	}
	
	public static void backgroundPreferencia(LinearLayout layout, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCIAS, 0);
		int bg = prefs.getInt("bg", 2);
		if (bg == 1) {
			layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.altbackground));
		} else {
			layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fundo));
		}
	}
	
    protected void onPause() {
        super.onPause();
        Music.stop(this);
      }
      
  	public void onResume(){
  		Music.play(this, R.raw.tar);
  		super.onResume();
  	}
}
	
	


