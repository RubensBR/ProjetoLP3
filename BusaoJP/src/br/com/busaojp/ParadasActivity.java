package br.com.busaojp;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class ParadasActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paradas);
        
        SupportMapFragment Map = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        GoogleMap gm = Map.getMap();
        gm.setMyLocationEnabled(true);
        
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        Location local = locationManager.getLastKnownLocation(provider);

        if(local!=null){
        	LatLng posicao = new LatLng(local.getLatitude(), local.getLongitude());
        	gm.animateCamera(CameraUpdateFactory.newLatLngZoom(posicao, 15)); 
        	gm.addMarker(new MarkerOptions().position(posicao).title("Voc� est� aqui!"));
    
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.paradas, menu);
		return true;
	}

}
