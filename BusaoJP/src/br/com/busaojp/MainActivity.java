package br.com.busaojp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import br.com.busaojp.utils.ActivityUtil;

public class MainActivity extends Activity implements OnSharedPreferenceChangeListener{
	Resources resources; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preferencias, menu);
        return true;
    }
    
    public void trataMenu(View v) {
    	Button bt = (Button) v;
    	switch (bt.getId()) {
	    	case R.id.button_pesquisar:
	    		ActivityUtil.mudarActivity(this, PesquisarActivity.class); 
	    		break;
	    		
	    	case R.id.button_favoritos:
	    		ActivityUtil.mudarActivity(this, FavoritosActivity.class); 
	    		break;
	    	
	    	case R.id.button_paradas:
	    		ActivityUtil.mudarActivity(this, ParadasActivity.class); 
	    		break;
	    		
	    	case R.id.button_terminal:
	    		ActivityUtil.mudarActivity(this, IntegracaoActivity.class); 
	    		break;
	    		
	    	default:
	    		return;
    	}
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.config) {
            ActivityUtil.mudarActivity(this, BusaoPreferences.class);
            return true;
        } else if (item.getItemId() == R.id.aboutUs) {
        	new AlertDialog.Builder(this).setMessage("Este é um projeto desenvolvido para conclusão da disciplina Linguagens de Programação 3 (Android) na UFPB\n\n Equipe: Ana Beatrice Severo\n Carlos André Correia\n Rubens Correia").setTitle("About").setPositiveButton("Voltar", null).show();
            return true;
        }
     
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences pref, String key) { 		
 		
		if(key.equals("fonte")){
			Button i = (Button)findViewById(R.id.button_terminal);
	 		Button i2 = (Button)findViewById(R.id.button_pesquisar);
	 		Button i3 = (Button)findViewById(R.id.button_paradas);
	 		Button i4 = (Button)findViewById(R.id.button_favoritos);
	 		
			if(pref.getString(key, "14").equals("12")){
			 i.setTextSize(12);
	       	 i2.setTextSize(12);
	       	 i3.setTextSize(12);
	       	 i4.setTextSize(12);

			}
			if(pref.getString(key, "14").equals("14")){
	       	 i.setTextSize(14);
	       	 i2.setTextSize(14);
	       	 i3.setTextSize(14);
	       	 i4.setTextSize(14);

	        }
			if(pref.getString(key, "14").equals("16")){
	       	 i.setTextSize(16);
	       	 i2.setTextSize(16);
	       	 i3.setTextSize(16);
	       	 i4.setTextSize(16);

	        }
			
			if(pref.getString(key, "14").equals("18")){
		     i.setTextSize(18);
		     i2.setTextSize(18);
		     i3.setTextSize(18);
		     i4.setTextSize(18);
		     
		    }
		}
		
	}
    
}