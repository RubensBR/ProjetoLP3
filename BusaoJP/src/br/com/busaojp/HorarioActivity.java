package br.com.busaojp;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HorarioActivity extends Activity {

	private ListView mListView;
	private ArrayAdapter<String> arrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.horario);
		
		mListView = (ListView) findViewById(R.id.horarios_listview);
		
		ArrayList<String> lista = new ArrayList<String>();
		
		for (int i = 0; i < 24; ++i) {
			if (i < 10) {
				lista.add("0" + i + ":00");
				lista.add("0" + i + ":30");
			}
			else {
				lista.add(i + ":00");
				lista.add(i + ":30");
			}
		}
		
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, lista);
		mListView.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.horario, menu);
		return true;
	}

}

