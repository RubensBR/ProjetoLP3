package br.com.busaojp;

import br.com.busaojp.utils.ActivityUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class FavoritosActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favoritos);
		
		Button b = (Button)findViewById(R.id.parada);
		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ActivityUtil.mudarActivity(FavoritosActivity.this, RotasActivity.class);	
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favoritos, menu);
		return true;
	}

}
