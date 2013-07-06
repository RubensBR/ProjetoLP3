package br.com.busaojp;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.busaojp.utils.ActivityUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View.OnClickListener;

public class FavoritosActivity extends Activity{
	
	private ListView mListView;
	private ArrayAdapter<String> arrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favoritos);
		
		mListView = (ListView) findViewById(R.id.lista_favoritos);
		final String res[] = {"202 - Geisel", "1500 - Circular", "1510 - Circular"};
		
		ArrayList<String> lista = new ArrayList<String>();
		lista.addAll(Arrays.asList(res));
		arrayAdapter = new ArrayAdapter<String>(this, R.layout.favoritos_listview, lista);
		mListView.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favoritos, menu);
		return true;
	}

}
