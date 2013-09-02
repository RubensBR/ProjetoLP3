package br.com.busaojp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.busaojp.onibus.Onibus;
import br.com.busaojp.onibus.OnibusDAO;
import br.com.busaojp.onibus.OnibusDAOJSON;
import br.com.busaojp.rotamaps.RotaMaps;
import br.com.busaojp.utils.ActivityUtil;

public class ItirenarioActivity extends Activity{
	
	private ListView mListView;
	private ArrayAdapter<String> arrayAdapter;
	private ProgressDialog mProgress;
	private Onibus onibus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itirenario);
		
		Intent activity = getIntent();
		Bundle parametros = activity.getExtras();
		if (parametros != null) {
			onibus = (Onibus) parametros.getSerializable("onibus");
			TextView textView = (TextView) findViewById(R.id.onibus_title);
			textView.setText(onibus.getLinha() + " - " + onibus.getNome());			
		}		
		
		mListView = (ListView) findViewById(R.id.lista_rota);		
		ArrayList<String> lista = new ArrayList<String>();
		
		for (int i = 1; i <= 20; ++i) {
			String s = "Rua número " + i;
			lista.add(s);
		}
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, lista);
		mListView.setAdapter(arrayAdapter);
		
	}
	
	public void mostraHorarios(View v) {
		ActivityUtil.mudarActivity(this, HorarioActivity.class);
	}
	
	public void verRota(View v) {
		//new PegaRotaTask().execute(onibus.getLinha());
		new PegaRotaTask().execute("1502");
	}
	
	public void verFavoritos(View v) {
		ActivityUtil.mudarActivity(this, FavoritosActivity.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferencias, menu);
		return true;
	}
	
	private class PegaRotaTask extends AsyncTask<String, String, RotaMaps> {
		private OnibusDAO dao;
		
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(ItirenarioActivity.this, "Aguarde", "Acessando o banco de dados remoto.", true);
		}
		
		@Override
		protected RotaMaps doInBackground(String... params) {
			dao = new OnibusDAOJSON();
			RotaMaps rota = dao.buscaRotaMaps(params[0]);
			return rota;			
		}
		
		@Override
		protected void onPostExecute(RotaMaps rota) {
			mProgress.cancel();
			if (rota == null) {
				AlertDialog.Builder popup = new AlertDialog.Builder(ItirenarioActivity.this);
				popup.setTitle("Erro");
				popup.setMessage("Erro a tentar conectar com o servidor. Verifique sua conexão com a internet.");
				popup.setPositiveButton("Ok", null);
				popup.show();
				return;
			}			
			
			Bundle parametro = new Bundle();
			parametro.putSerializable("rota", rota);
			ActivityUtil.mudarActivity(ItirenarioActivity.this, RotasActivity.class, parametro);
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

}
