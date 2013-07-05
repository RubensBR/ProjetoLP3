package br.com.busaojp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class IntegracaoActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.integracao);
		
		 SupportMapFragment Map = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.integracao);
	        GoogleMap gm = Map.getMap();
	        gm.setMyLocationEnabled(true);

	        gm.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7,34), 20));	
	       
	     }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.integracao, menu);
		return true;
	}

}
