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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.busaojp.onibus.Onibus;
import br.com.busaojp.onibus.OnibusDAO;
import br.com.busaojp.onibus.OnibusDAOJSON;
import br.com.busaojp.onibus.OnibusDAOSQL;
import br.com.busaojp.rotamaps.RotaMaps;
import br.com.busaojp.utils.ActivityUtil;

public class ItirenarioActivity extends Activity {
	
	private ListView mListView;
	private ArrayAdapter<String> arrayAdapter;
	private ProgressDialog mProgress;
	private Onibus onibus;
	private boolean pegarDadosLocal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itirenario);
		
		Intent activity = getIntent();
		Bundle parametros = activity.getExtras();
		if (parametros != null) {
			onibus = (Onibus) parametros.getSerializable("onibus");			
			pegarDadosLocal = parametros.getBoolean("pegarDadosLocal");
			TextView textView = (TextView) findViewById(R.id.onibus_title);
			textView.setText(onibus.getLinha() + " - " + onibus.getNome());			
		}		
		
		mListView = (ListView) findViewById(R.id.lista_rota);
		setBackground();
		
		ArrayList<String> lista = onibus.getRota().getIda();		
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, lista);
		System.out.println("== volta main: " + onibus.getRota().getVolta().size());
		mListView.setAdapter(arrayAdapter);		
	}
	
	public void verIda(View v) {
		ArrayList<String> lista = onibus.getRota().getIda();		
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, lista);
		mListView.setAdapter(arrayAdapter);
		Toast.makeText(this, "Rota de ida", Toast.LENGTH_SHORT).show();
	}
	
	public void verVolta(View v) {
		ArrayList<String> lista = onibus.getRota().getVolta();		
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, lista);
		mListView.setAdapter(arrayAdapter);
		Toast.makeText(this, "Rota de volta", Toast.LENGTH_SHORT).show();
	}
	
	@Override
    protected void onPause() {
      super.onPause();
      Music.stop(this);
    }
    
	public void onResume(){
      Music.play(this, R.raw.tar);
      setBackground();
      super.onResume();
	}
	
	public void mostraHorarios(View v) {
		Bundle parametros = new Bundle();
		
		if (pegarDadosLocal)
			parametros.putStringArrayList("horarios", pegarHorariosDoBancoDeDados());
		else 
			parametros.putStringArrayList("horarios", onibus.getHorarios());
		ActivityUtil.mudarActivity(this, HorarioActivity.class, parametros);
	}
	
	public void verRota(View v) {		
		if (pegarDadosLocal) {
			pegarRotaDoBancoDeDados();
		} else {
			new PegaRotaTask().execute("1502");
			//new PegaRotaTask().execute(onibus.getLinha());
		}
	}
	
	private ArrayList<String> pegarHorariosDoBancoDeDados() {
		mProgress = ProgressDialog.show(ItirenarioActivity.this, "Aguarde", "Carregando Rota", true);
		OnibusDAOSQL onibusBD = new OnibusDAOSQL(ItirenarioActivity.this);
		ArrayList<String> horarios = onibusBD.getHorarios(onibus.getLinha());
		mProgress.cancel();
		return horarios;
	}
	
	private void pegarRotaDoBancoDeDados() {
		mProgress = ProgressDialog.show(ItirenarioActivity.this, "Aguarde", "Carregando Rota", true);
		OnibusDAOSQL onibusBD = new OnibusDAOSQL(ItirenarioActivity.this);
		RotaMaps rota = onibusBD.buscaRotaMaps(onibus.getLinha());
		mProgress.cancel();
		Bundle parametro = new Bundle();
		parametro.putSerializable("rota", rota);
		parametro.putSerializable("onibus", onibus);
		ActivityUtil.mudarActivity(ItirenarioActivity.this, RotasActivity.class, parametro);
	}
	
	public void adicionaFavoritos(View v) {
		PegaRotaTask salvar = new PegaRotaTask(false);
		salvar.execute("1502");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferencias, menu);
		return true;
	}
	
	private class PegaRotaTask extends AsyncTask<String, String, RotaMaps> {
		private OnibusDAO dao;
		
		private boolean mudarActivity = true;
		
		public PegaRotaTask() {}
		public PegaRotaTask(boolean mudarActivity) {
			this.mudarActivity = mudarActivity;
		}
		
		@Override
		protected void onPreExecute() {
			String mensagem = "Acessando o banco de dados remoto.";
			if (!mudarActivity)
				mensagem = "Recuperando dados do servidor para salvar";
			mProgress = ProgressDialog.show(ItirenarioActivity.this, "Aguarde", mensagem, true);
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
			if (mudarActivity) {
				Bundle parametro = new Bundle();
				parametro.putSerializable("rota", rota);
				parametro.putSerializable("onibus", onibus);
				ActivityUtil.mudarActivity(ItirenarioActivity.this, RotasActivity.class, parametro);
			}
			else {
				onibus.setRotaMaps(rota);
				OnibusDAOSQL onibusBD = new OnibusDAOSQL(ItirenarioActivity.this);
				boolean sucesso = onibusBD.salvarOnibus(onibus);
				if (sucesso) {
					Toast.makeText(ItirenarioActivity.this, 
							"Esta linha foi adicionada aos favoritos com sucesso.", Toast.LENGTH_SHORT).show();					
				} else {
					Toast.makeText(ItirenarioActivity.this, 
							"Esta linha já está em seus favoritos.", Toast.LENGTH_SHORT).show();
				}
			}
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
    
    public void setBackground(){		
		LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayoutItinerario);
		BusaoPreferences.backgroundPreferencia(layout, this);
    }
    
}
