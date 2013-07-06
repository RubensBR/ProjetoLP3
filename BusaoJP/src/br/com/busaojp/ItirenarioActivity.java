package br.com.busaojp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import br.com.busaojp.utils.ActivityUtil;

public class ItirenarioActivity extends Activity {

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
	}
	
	public void botoes(View v){
		ImageButton b = (ImageButton) v;
    	switch (b.getId()) {
	    	case R.id.mapas: ActivityUtil.mudarActivity(this, RotasActivity.class); break;	
	    	case R.id.horario: ActivityUtil.mudarActivity(this, HorarioActivity.class); break;
	    	case R.id.favoritos: ActivityUtil.mudarActivity(this, FavoritosActivity.class); break;	
	    	default: return;
    	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itirenario, menu);
		return true;
	}

}
