package br.com.busaojp;

import br.com.busaojp.utils.ActivityUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void trataMenu(View v) {
    	Button bt = (Button) v;
    	switch (bt.getId()) {
	    	case R.id.button_pesquisar:
	    		ActivityUtil.mudarActivity(this, PesquisarActivity.class); 
	    		break;
	    		
	    	case R.id.button_favoritos:
	    		ActivityUtil.mudarActivity(this, PesquisarActivity.class); 
	    		break;
	    	
	    	case R.id.button_paradas:
	    		ActivityUtil.mudarActivity(this, PesquisarActivity.class); 
	    		break;
	    		
	    	case R.id.button_terminal:
	    		ActivityUtil.mudarActivity(this, PesquisarActivity.class); 
	    		break;
	    		
	    	default:
	    		return;
    	}
    }
    
}
