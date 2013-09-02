package br.com.busaojp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import br.com.busaojp.rotamaps.Marcador;
import br.com.busaojp.rotamaps.Posicao;
import br.com.busaojp.rotamaps.RotaMaps;
import br.com.busaojp.utils.ActivityUtil;

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
		
		Intent activity = getIntent();
		Bundle parametros = activity.getExtras();
		RotaMaps rota = (RotaMaps) parametros.getSerializable("rota");
        
        SupportMapFragment Map = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        GoogleMap gm = Map.getMap();
        gm.setMyLocationEnabled(true);
        
        PolylineOptions options = new PolylineOptions();
        options.width(5).color(Color.BLUE).geodesic(true);
        //adiciona a rota
        for (Posicao pos : rota.getIda().getRota()) 
        	options.add(new LatLng(pos.getLatitude(), pos.getLongitude()));
        
        gm.addPolyline(options);
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        

    	LatLng posicao = new LatLng(rota.getIda().getRota().get(0).getLatitude(),
    			rota.getIda().getRota().get(0).getLongitude());
    
    	gm.animateCamera(CameraUpdateFactory.newLatLngZoom(posicao, 10));     
    	
    	//adiciona os marcadores
    	for(Marcador marcador : rota.getIda().getMarcadores()) {
    		gm.addMarker(new MarkerOptions().position(new LatLng(marcador.getPosicao().getLatitude(), 
    				marcador.getPosicao().getLongitude())).title(marcador.getNome()));
    	} 
		
    	Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location local = locationManager.getLastKnownLocation(provider);        
		if(local!=null){
			LatLng posicaoDispositivo = new LatLng(local.getLatitude(), local.getLatitude());
			gm.addMarker(new MarkerOptions().position(posicaoDispositivo).title("Você está aqui!"));
	    }
        
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preferencias, menu);
        return true;
    }
    
    public LatLng inverte(double longitude, double latitude) {
    	return new LatLng(latitude, longitude);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.config) {
            ActivityUtil.mudarActivity(this, BusaoPreferences.class);
            return true;
        } else if (item.getItemId() == R.id.aboutUs) {
        	new AlertDialog.Builder(this).setMessage("Este é um projeto desenvolvido para conclusão da disciplina Linguagens de Programação 3 (Android) na UFPB\n\n Equipe: Ana Beatrice Severo\n Carlos André Correia\n Rubens Correia").setTitle("About").setPositiveButton("Voltar", null).show();
            return true;
        }
     
        return super.onOptionsItemSelected(item);
    }
    
}
