package br.com.busaojp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import br.com.busaojp.utils.ActivityUtil;

public class HorarioActivity extends Activity{

	private ListView mListView;	
	private ArrayAdapter<String> arrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.horario);
		
		mListView = (ListView) findViewById(R.id.horarios_listview);		
		ArrayList<String> lista = new ArrayList<String>();
		Intent activity = getIntent();
		Bundle parametros = activity.getExtras();
		if (parametros != null) {
			lista = parametros.getStringArrayList("horarios");
			TextView mProximoHorario = (TextView) findViewById(R.id.proximo_horario);
			mProximoHorario.setText("Próximo: " + pegarProximoOnibus(lista));
		}
		
		setBackground();
		
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, lista);
		mListView.setAdapter(arrayAdapter);

	}
	
	public String pegarProximoOnibus(ArrayList<String> horarios) {
		Calendar horaAtual = Calendar.getInstance();
		int hr = horaAtual.get(Calendar.HOUR_OF_DAY);
		int mn = horaAtual.get(Calendar.MINUTE);
		for (int i = 0; i < horarios.size(); ++i) {
			StringTokenizer tk = new StringTokenizer(horarios.get(i), ":");
			int hrProx = Integer.parseInt(tk.nextToken());
			int mnProx = Integer.parseInt(tk.nextToken());
			if (hrProx > hr || (hrProx == hr && mnProx >= mn)) {
				return horarios.get(i);
			}								
		}	
		return horarios.get(0);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferencias, menu);
		return true;
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
		LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayoutHorario);
		BusaoPreferences.backgroundPreferencia(layout, this);
	}

}