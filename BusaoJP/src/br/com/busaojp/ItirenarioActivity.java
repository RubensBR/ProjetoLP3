package br.com.busaojp;

import java.util.ArrayList;
import br.com.busaojp.utils.ActivityUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ItirenarioActivity extends Activity {
	
	private ListView mListView;
	private ArrayAdapter<String> arrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itirenario);
		
		Intent activity = getIntent();
		Bundle parametros = activity.getExtras();
		
		if (parametros != null) {
			String onibus = parametros.getString("linha");
			TextView textView = (TextView) findViewById(R.id.onibus_title);
			textView.setText(onibus);			
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
		ActivityUtil.mudarActivity(this, RotasActivity.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itirenario, menu);
		return true;
	}

}
