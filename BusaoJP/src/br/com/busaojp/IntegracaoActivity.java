package br.com.busaojp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class IntegracaoActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.integracao);
    
    }
        
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.integracao, menu);
		return true;
	}

}
