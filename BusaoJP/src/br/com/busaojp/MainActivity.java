package br.com.busaojp;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.busaojp.utils.ActivityUtil;
import br.com.busaojp.utils.TemperaturaTask;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnSharedPreferenceChangeListener{
	public boolean select = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main);  
        super.onCreate(savedInstanceState);
        
        setFonte();
        setCorFonte();
        setBackground();
              
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		pref.registerOnSharedPreferenceChangeListener(this);
		
		TextView texto = (TextView) findViewById(R.id.temperatura);
		new TemperaturaTask(texto).execute();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preferencias, menu);
        return true;
    }
    
	@Override
    protected void onPause() {
      super.onPause();
      Music.stop(this);
    }
    
	public void onResume(){
	  super.onResume();
      Music.play(this, R.raw.tar);
      setFonte();
      setCorFonte();
      setBackground();
      
      if(select){
    	  finish();
    	  startActivity(getIntent());
    	  select = false;
      }
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
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	
        if (item.getItemId() == R.id.config) {
            ActivityUtil.mudarActivity(this, BusaoPreferences.class);
            return true;
        } else if (item.getItemId() == R.id.aboutUs) {
        	new AlertDialog.Builder(this).setMessage(R.string.descricao).setTitle(R.string.about).setPositiveButton("Voltar", null).show();
            return true;
        }
     
        return super.onOptionsItemSelected(item);
    }

    public void setFonte(){
	    if(BusaoPreferences.fontePreferencia(this).equals("12")){
			BusaoPreferences.setFonte(12, (Button)findViewById(R.id.button_favoritos));
			BusaoPreferences.setFonte(12, (Button)findViewById(R.id.button_paradas));
			BusaoPreferences.setFonte(12, (Button)findViewById(R.id.button_pesquisar));
			BusaoPreferences.setFonte(12, (Button)findViewById(R.id.button_terminal));
			
		}
		if(BusaoPreferences.fontePreferencia(this).equals("14")){
			BusaoPreferences.setFonte(14, (Button)findViewById(R.id.button_favoritos));
			BusaoPreferences.setFonte(14, (Button)findViewById(R.id.button_paradas));
			BusaoPreferences.setFonte(14, (Button)findViewById(R.id.button_pesquisar));
			BusaoPreferences.setFonte(14, (Button)findViewById(R.id.button_terminal));
	   	}
		if(BusaoPreferences.fontePreferencia(this).equals("16")){
			BusaoPreferences.setFonte(16, (Button)findViewById(R.id.button_favoritos));
			BusaoPreferences.setFonte(16, (Button)findViewById(R.id.button_paradas));
			BusaoPreferences.setFonte(16, (Button)findViewById(R.id.button_pesquisar));
			BusaoPreferences.setFonte(16, (Button)findViewById(R.id.button_terminal));
	   	 }
		
		if(BusaoPreferences.fontePreferencia(this).equals("18")){
			BusaoPreferences.setFonte(18, (Button)findViewById(R.id.button_favoritos));
			BusaoPreferences.setFonte(18, (Button)findViewById(R.id.button_paradas));
			BusaoPreferences.setFonte(18, (Button)findViewById(R.id.button_pesquisar));
			BusaoPreferences.setFonte(18, (Button)findViewById(R.id.button_terminal));
	    }
    }
    
    public void setCorFonte(){
    	
    	Button terminal = (Button)findViewById(R.id.button_terminal);
    	Button favoritos = (Button)findViewById(R.id.button_favoritos);
    	Button paradas = (Button)findViewById(R.id.button_paradas);
    	Button pesquisar = (Button)findViewById(R.id.button_pesquisar);
    	
		    if(BusaoPreferences.corFontePreferencia(this).equals("preto")){	
		    	BusaoPreferences.setColorFonte("#000000", terminal);
				BusaoPreferences.setColorFonte("#000000", favoritos);
				BusaoPreferences.setColorFonte("#000000", paradas);
				BusaoPreferences.setColorFonte("#000000", pesquisar);
			}
			
			if(BusaoPreferences.corFontePreferencia(this).equals("rosa")){
				BusaoPreferences.setColorFonte("#FF1CAE", terminal);
				BusaoPreferences.setColorFonte("#FF1CAE", favoritos);
				BusaoPreferences.setColorFonte("#FF1CAE", paradas);
				BusaoPreferences.setColorFonte("#FF1CAE", pesquisar);
							
			}
			
			if(BusaoPreferences.corFontePreferencia(this).equals("azul")){
				BusaoPreferences.setColorFonte("#38B0DE", terminal);
				BusaoPreferences.setColorFonte("#38B0DE", favoritos);
				BusaoPreferences.setColorFonte("#38B0DE", paradas);
				BusaoPreferences.setColorFonte("#38B0DE", pesquisar);
				
			}
			
			if(BusaoPreferences.corFontePreferencia(this).equals("verde")){
				BusaoPreferences.setColorFonte("#238E23", terminal);
				BusaoPreferences.setColorFonte("#238E23", favoritos);
				BusaoPreferences.setColorFonte("#238E23", paradas);
				BusaoPreferences.setColorFonte("#238E23", pesquisar);
				
			}
			
			if(BusaoPreferences.corFontePreferencia(this).equals("vermelho")){
				BusaoPreferences.setColorFonte("#FF0000", terminal);
				BusaoPreferences.setColorFonte("#FF0000", favoritos);
				BusaoPreferences.setColorFonte("#FF0000", paradas);
				BusaoPreferences.setColorFonte("#FF0000", pesquisar);		
			}	
    }
    
    public void setBackground(){
	    LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayoutMain);
		BusaoPreferences.backgroundPreferencia(layout, this);
    }
    
    public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
        if(key.equals("idioma")){
    		Configuration config = new Configuration(getResources().getConfiguration());
    		if(pref.getString(key, "portugues").equals("portugues")){
    		    config.locale = Locale.ROOT;
    		    getResources().updateConfiguration(config,getResources().getDisplayMetrics());  
    		    select = true;
    		}
    		if(pref.getString(key, "portugues").equals("ingles")){
    		    config.locale = Locale.ENGLISH ;
    		    getResources().updateConfiguration(config,getResources().getDisplayMetrics());
    		    select = true;
    		}
        }		
    }
	
}
    
