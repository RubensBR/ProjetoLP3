package br.com.busaojp;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PesquisarActivity extends Activity {

	private ListView mListView;
	private ArrayAdapter<String> arrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisar);
		
		mListView = (ListView) findViewById(R.id.resultado_busca);
		final String res[] = {"202 - Geisel", "1500 - Circular", "1510 - Circular", "1519 - Valentina",
				"1502 - Geisel Epitacio", "202 - Geisel", "1500 - Circular", "1510 - Circular", "1519 - Valentina",
				"1502 - Geisel Epitacio"};
		
		ArrayList<String> lista = new ArrayList<String>();
		lista.addAll(Arrays.asList(res));
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, lista);
		mListView.setAdapter(arrayAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				Toast.makeText(getApplicationContext(),
					      "Click ListItem Number " + position + ", " + res[position], Toast.LENGTH_LONG)
					      .show();
			}});
	
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pesquisar, menu);
		return true;
	}

}
