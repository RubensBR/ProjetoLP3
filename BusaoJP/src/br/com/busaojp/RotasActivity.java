package br.com.busaojp;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class RotasActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotas);
        
        SupportMapFragment Map = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        GoogleMap gm = Map.getMap();
        gm.setMyLocationEnabled(true);
        PolylineOptions options = new PolylineOptions();
        options.width(5).color(Color.RED).geodesic(true);
        options.add(inverte(-34.87605571746826,-7.118086518796384), 
        		inverte(-34.87441420555115,-7.118842394648979),
        		inverte(-34.8725152015686,-7.119747314806361),
        		inverte(-34.87179636955261,-7.118001349608172),
        		inverte(-34.87135648727417,-7.118161041823093),
        		inverte(-34.87042307853699,-7.118576241321808),
        		inverte(-34.870219230651855,-7.118821102388489),
        		inverte(-34.870498180389404,-7.1194385775415245),
        		inverte(-34.87108826637268,-7.12076934013156),
        		inverte(-34.871485233306885,-7.121440043013551),
        		inverte(-34.87459659576416,-7.119992175247974),
        		inverte(-34.876989126205444,-7.119002086571174),
        		inverte(-34.87677454948425,-7.118267503268844),
        		inverte(-34.87655460834503,-7.117868272719938),
        		inverte(-34.87605571746826,-7.118086518796384));
        gm.addPolyline(options);
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        Location local = locationManager.getLastKnownLocation(provider);

        if(local!=null){
        	LatLng posicao = inverte(-34.87153887748718,-7.118320733982457);
        
        	gm.animateCamera(CameraUpdateFactory.newLatLngZoom(posicao, 15)); 
        	gm.addMarker(new MarkerOptions().position(posicao).title("Você está aqui!"));
        	gm.addMarker(new MarkerOptions().position(inverte(-34.87605571746826,-7.118086518796384)).title("Olá Mundo"));
    
        }
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rotas, menu);
        return true;
    }
    
    public LatLng inverte(double longitude, double latitude) {
    	return new LatLng(latitude, longitude);
    }
    
}
