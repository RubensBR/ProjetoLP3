package br.com.busaojp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import br.com.busaojp.onibus.Onibus;
import br.com.busaojp.onibus.OnibusDAO;
import br.com.busaojp.onibus.OnibusDAOSQL;
import br.com.busaojp.onibus.OnibusListAdapter;
import br.com.busaojp.onibus.Rota;
import br.com.busaojp.utils.ActivityUtil;

public class FavoritosActivity extends Activity {
	
	private ListView mListView;
	private ProgressDialog mProgress;
	private EditText mEditor;
	private RadioGroup mRadioGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favoritos);

		mListView = (ListView) findViewById(R.id.resultado_busca_favoritos);
		mRadioGroup = (RadioGroup) findViewById(R.id.radiobutton_pesquisar_favoritos);
		mEditor = (EditText) findViewById(R.id.search_favoritos);
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
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayoutFavoritos);
		BusaoPreferences.backgroundPreferencia(layout, this);
		
		if (mProgress != null)
			mProgress.cancel();
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
			mProgress = ProgressDialog.show(FavoritosActivity.this, "Aguarde", "Acessando o banco de dados.", true); 
			Bundle parametro = new Bundle();
			Onibus onibus = (Onibus) parent.getItemAtPosition(position);
			OnibusDAOSQL onibusBD = new OnibusDAOSQL(FavoritosActivity.this);
			Rota rota = onibusBD.getRota(onibus.getLinha());
			onibus.setRota(rota);								
			parametro.putSerializable("onibus", onibus);
			parametro.putBoolean("pegarDadosLocal", true);
			mProgress.cancel();
			ActivityUtil.mudarActivity(FavoritosActivity.this, ItirenarioActivity.class, parametro);			
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
				mProgress = ProgressDialog.show(FavoritosActivity.this, "Aguarde", "Acessando o banco de dados!", true);
			}
			
			@Override
			protected ArrayList<Onibus> doInBackground(String... params) {				
				dao = new OnibusDAOSQL(FavoritosActivity.this);				
				ArrayList<Onibus> lista = dao.lista();				
				return lista;				
			}
			
			@Override
			protected void onPostExecute(ArrayList<Onibus> lista) {
				mProgress.cancel();
				if (lista == null) {
					AlertDialog.Builder popup = new AlertDialog.Builder(FavoritosActivity.this);
					popup.setTitle("Erro");
					popup.setMessage("Ocorreu um erro no banco de dados.");
					popup.setPositiveButton("Ok", null);
					popup.show();
					return;
				}			
				
				mListView.setAdapter(new OnibusListAdapter(lista, FavoritosActivity.this, true));
				mListView.setOnItemClickListener(new TrataItemSelecionado());
				mListView.setOnItemLongClickListener(new TrataDeleteItemSelecionado());			
			}
		}
		//=====
		
		private class TrataDeleteItemSelecionado implements OnItemLongClickListener {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				AlertDialog.Builder popup = new AlertDialog.Builder(FavoritosActivity.this);
				popup.setTitle("Remover Favorito");
				popup.setMessage("Tem certeza que deseja remover essa linha de seus favoritos?");
				final int index = position;
				final AdapterView<?> lista = parent;
				popup.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						OnibusDAOSQL onibusBD = new OnibusDAOSQL(FavoritosActivity.this);
						Onibus onibus = (Onibus) lista.getItemAtPosition(index);						
						onibusBD.removerFavoritos(onibus.getLinha());
						new ListarAsync().execute();
						Toast.makeText(FavoritosActivity.this, "Linha removida com sucesso.", Toast.LENGTH_SHORT)
							.show();
					}
				});
				popup.setNegativeButton("Não", null);
				popup.show();
				return false;
			}
					
		}
		
		private class OperacaoAsync extends AsyncTask<Integer, String, ArrayList<Onibus>> {
			private OnibusDAO dao;
			private String busca;
			
			public OperacaoAsync(String busca) {
				this.busca = busca;
			}
			
			@Override
			protected void onPreExecute() {
				mProgress = ProgressDialog.show(FavoritosActivity.this, "Aguarde", "Acessando o banco de dados.", true);
			}
			
			@Override
			protected ArrayList<Onibus> doInBackground(Integer... params) {
				dao = new OnibusDAOSQL(FavoritosActivity.this);
				ArrayList<Onibus> lista = new ArrayList<Onibus>(); 
				switch (params[0]) {
				case R.id.linha_radioButton_favoritos:
					lista = dao.buscaPorLinha(busca);
					break;
				case R.id.logradouro_radioButton_favoritos:
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
					AlertDialog.Builder popup = new AlertDialog.Builder(FavoritosActivity.this);
					popup.setTitle("Erro");
					popup.setMessage("Erro a tentar conectar com o servidor. Verifique sua conexão com a internet.");
					popup.setPositiveButton("Ok", null);
					popup.show();
					return;
				}			
				
				mListView.setAdapter(new OnibusListAdapter(lista, FavoritosActivity.this, true));
				mListView.setOnItemClickListener(new TrataItemSelecionado());
				mListView.setOnItemLongClickListener(new TrataDeleteItemSelecionado());
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
		
	
