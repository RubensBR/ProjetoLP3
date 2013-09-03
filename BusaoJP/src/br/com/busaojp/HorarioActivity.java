package br.com.busaojp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
		}
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayoutHorario);
		BusaoPreferences.backgroundPreferencia(layout, this);
		
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, lista);
		mListView.setAdapter(arrayAdapter);

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

}

