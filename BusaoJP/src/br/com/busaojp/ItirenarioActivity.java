package br.com.busaojp;

import br.com.busaojp.utils.ActivityUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

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
	
	public void mostraHorarios(View v) {
		ActivityUtil.mudarActivity(this, HorarioActivity.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itirenario, menu);
		return true;
	}

}
