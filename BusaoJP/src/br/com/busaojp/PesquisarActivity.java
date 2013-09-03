package br.com.busaojp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import br.com.busaojp.onibus.Onibus;
import br.com.busaojp.onibus.OnibusDAO;
import br.com.busaojp.onibus.OnibusDAOJSON;
import br.com.busaojp.onibus.OnibusListAdapter;
import br.com.busaojp.utils.ActivityUtil;

public class PesquisarActivity extends Activity {

	private ListView mListView;
	private ProgressDialog mProgress;
	private EditText mEditor;
	private RadioGroup mRadioGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisar);
        
		mListView = (ListView) findViewById(R.id.resultado_busca);
		mRadioGroup = (RadioGroup) findViewById(R.id.radiobutton_pesquisar);
		mEditor = (EditText) findViewById(R.id.search);
		mEditor.setOnEditorActionListener(new OnEditorActionListener() {			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean teste = false;
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					realizarBusca();
					teste = true;
				}
				return teste;				
			}
		});
		
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayoutPesquisa);
		BusaoPreferences.backgroundPreferencia(layout, this);
		
		ListarAsync listar = new ListarAsync();
		listar.execute();
		
		
	}
	
	public void realizarBusca() {
		String busca = mEditor.getText().toString();	
		int opcao = mRadioGroup.getCheckedRadioButtonId();
		OperacaoAsync operacao = new OperacaoAsync(busca);
		operacao.execute(opcao);		
	}
	
	private class TrataItemSelecionado implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {			
			Bundle parametro = new Bundle();
			Onibus onibus = (Onibus) parent.getItemAtPosition(position);
			parametro.putSerializable("onibus", onibus);
			parametro.putBoolean("pegarDadosLocal", false);
			ActivityUtil.mudarActivity(PesquisarActivity.this, ItirenarioActivity.class, parametro);
		}		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferencias, menu);
		return true;
	}
	
	//==============
	private class ListarAsync extends AsyncTask<String, String, ArrayList<Onibus>> {
		private OnibusDAO dao;
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(PesquisarActivity.this, "Aguarde", "Acessando o banco de dados remoto.", true);
		}
		
		@Override
		protected ArrayList<Onibus> doInBackground(String... params) {
			dao = new OnibusDAOJSON();
			ArrayList<Onibus> lista = dao.lista();
			return lista;			
		}
		
		@Override
		protected void onPostExecute(ArrayList<Onibus> lista) {
			mProgress.cancel();
			if (lista == null) {
				AlertDialog.Builder popup = new AlertDialog.Builder(PesquisarActivity.this);
				popup.setTitle("Erro");
				popup.setMessage("Erro a tentar conectar com o servidor. Verifique sua conexão com a internet.");
				popup.setPositiveButton("Ok", null);
				popup.show();
				return;
			}			
			
			mListView.setAdapter(new OnibusListAdapter(lista, PesquisarActivity.this));
			mListView.setOnItemClickListener(new TrataItemSelecionado());			
		}
	}
	//============
	private class OperacaoAsync extends AsyncTask<Integer, String, ArrayList<Onibus>> {
		private OnibusDAO dao;
		private String busca;
		
		public OperacaoAsync(String busca) {
			this.busca = busca;
		}
		
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(PesquisarActivity.this, "Aguarde", "Acessando o banco de remoto.", true);
		}
		
		@Override
		protected ArrayList<Onibus> doInBackground(Integer... params) {
			dao = new OnibusDAOJSON();
			ArrayList<Onibus> lista = new ArrayList<Onibus>(); 
			switch (params[0]) {
			case R.id.linha_radioButton:
				lista = dao.buscaPorLinha(busca);
				break;
			case R.id.logradouro_radioButton:
				lista = dao.buscaPorLogradouro(busca);
				break;
			default:
				break;
			}
			return lista;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Onibus> lista) {
			mProgress.cancel();
			if (lista == null) {
				AlertDialog.Builder popup = new AlertDialog.Builder(PesquisarActivity.this);
				popup.setTitle("Erro");
				popup.setMessage("Erro a tentar conectar com o servidor. Verifique sua conexão com a internet.");
				popup.setPositiveButton("Ok", null);
				popup.show();
				return;
			}			
			
			mListView.setAdapter(new OnibusListAdapter(lista, PesquisarActivity.this));			
			mListView.setOnItemClickListener(new TrataItemSelecionado());			
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
    
      @Override
      protected void onPause() {
        super.onPause();
        Music.stop(this);
      }
      
	public void onResume(){
        Music.play(this, R.raw.tar);
		super.onResume();
    }
		
}
