package br.com.busaojp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class BusaoPreferences extends PreferenceActivity {
	
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.preferencias);
	}
	
	public static boolean getMusic(Context context) {
	    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("music", true);
	}
	
	public static void setFonte(int tamanho, Button b){ 
		b.setTextSize(tamanho);  	 
	}
	
	public static void setColorFonte(String cor, Button b){ 
		b.setTextColor(Color.parseColor(cor));  	 
	}
	
	public static String fontePreferencia(Context ctx){
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString("fonte", "14");
	}
	
	public static boolean BackgroundCheck(Context ctx){
		return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean("background", true);
	}
	
	public static String corFontePreferencia(Context ctx){
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString("colorf", "preto");
	}
	
	public static void backgroundPreferencia(LinearLayout l, Context ctx){
		if(BackgroundCheck(ctx)){
			l.setBackgroundResource(R.drawable.altbackground);
		}else{
			l.setBackgroundResource(R.drawable.fundo);
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
	
	


