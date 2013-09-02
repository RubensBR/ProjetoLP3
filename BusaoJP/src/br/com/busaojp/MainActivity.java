package br.com.busaojp;

import java.util.Locale;

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
import br.com.busaojp.utils.ActivityUtil;

public class MainActivity extends Activity implements OnSharedPreferenceChangeListener{
	boolean select = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main);  
        super.onCreate(savedInstanceState);
        
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		pref.registerOnSharedPreferenceChangeListener(this);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preferencias, menu);
        return true;
    }
    
    @Override
	public void onResume(){
    	super.onResume();
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
        	new AlertDialog.Builder(this).setMessage("Este é um projeto desenvolvido para conclusão da disciplina Linguagens de Programação 3 (Android) na UFPB\n\n Equipe: Ana Beatrice Severo\n Carlos André Correia\n Rubens Correia").setTitle("About").setPositiveButton("Voltar", null).show();
            return true;
        }
     
        return super.onOptionsItemSelected(item);
    }
    
	public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
		SharedPreferences settings = this.getSharedPreferences("fonte" , 0);
		SharedPreferences.Editor ed = settings.edit();
		
		if(key.equals("fonte")){
	 		
			if(pref.getString(key, "14").equals("12")){
				setFonte(12);
			}
			if(pref.getString(key, "14").equals("14")){
				setFonte(14);
	       	}
			if(pref.getString(key, "14").equals("16")){
				setFonte(16);
	       	 }
			
			if(pref.getString(key, "14").equals("18")){
				setFonte(18);
		    }
		}
		
		ed.commit();
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
		
		ed = settings.edit();
		
		if(key.equals("background")){   //não funciona :(
			if(pref.getBoolean("background", false) == true){
				setBackgroundChecked();
			}
		else{
				setBackgroundUnchecked();
			}
		}
		ed.commit();
}


	public void setFonte(int tamanho){ 
	Button i = (Button)findViewById(R.id.button_terminal);
	Button i2 = (Button)findViewById(R.id.button_pesquisar);
	Button i3 = (Button)findViewById(R.id.button_paradas);
	Button i4 = (Button)findViewById(R.id.button_favoritos);
	
     i.setTextSize(tamanho);
  	 i2.setTextSize(tamanho);
  	 i3.setTextSize(tamanho);
  	 i4.setTextSize(tamanho);
	}

	public void setBackgroundChecked(){
       /* LinearLayout l = (LinearLayout)findViewById(R.id.LinearLayoutRotas);
		LinearLayout l2 = (LinearLayout)findViewById(R.id.LinearLayoutFavoritos);
		LinearLayout l3 = (LinearLayout)findViewById(R.id.LinearLayoutHorario);
		LinearLayout l4 = (LinearLayout)findViewById(R.id.LinearLayoutItinerario);*/
		LinearLayout l5 = (LinearLayout)findViewById(R.id.LinearLayoutMain);
		/*LinearLayout l6 = (LinearLayout)findViewById(R.id.LinearLayoutOnibus);
		LinearLayout l7 = (LinearLayout)findViewById(R.id.LinearLayoutParadas);
		LinearLayout l8 = (LinearLayout)findViewById(R.id.LinearLayoutPesquisa);*/
		
			
			l5.setBackgroundResource(R.drawable.altbackground);
			/*l2.setBackgroundResource(R.drawable.altbackground);
			l3.setBackgroundResource(R.drawable.altbackground);
			l4.setBackgroundResource(R.drawable.altbackground);
			l5.setBackgroundResource(R.drawable.altbackground);
			/*l6.setBackgroundResource(R.drawable.altbackground);
			l7.setBackgroundResource(R.drawable.altbackground);
			l8.setBackgroundResource(R.drawable.altbackground);*/
	}
	
	public void setBackgroundUnchecked(){
        /*LinearLayout l = (LinearLayout)findViewById(R.id.LinearLayoutRotas);
		LinearLayout l2 = (LinearLayout)findViewById(R.id.LinearLayoutFavoritos);
		LinearLayout l3 = (LinearLayout)findViewById(R.id.LinearLayoutHorario);
		LinearLayout l4 = (LinearLayout)findViewById(R.id.LinearLayoutItinerario);*/
		LinearLayout l5 = (LinearLayout)findViewById(R.id.LinearLayoutMain);
		/*LinearLayout l6 = (LinearLayout)findViewById(R.id.LinearLayoutOnibus);
		LinearLayout l7 = (LinearLayout)findViewById(R.id.LinearLayoutParadas);
		LinearLayout l8 = (LinearLayout)findViewById(R.id.LinearLayoutPesquisa);*/
		
		l5.setBackgroundResource(R.drawable.fundo);
		/*l2.setBackgroundResource(R.drawable.fundo);
		l3.setBackgroundResource(R.drawable.fundo);
		l4.setBackgroundResource(R.drawable.fundo);
		l5.setBackgroundResource(R.drawable.fundo);
		l6.setBackgroundResource(R.drawable.fundo);
		l7.setBackgroundResource(R.drawable.fundo);
		l8.setBackgroundResource(R.drawable.fundo);*/
	}
}
    
