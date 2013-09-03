package br.com.busaojp;

import android.app.ProgressDialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;
import br.com.busaojp.onibus.OnibusDAOJSON;
import br.com.busaojp.rotamaps.Parada;
import br.com.busaojp.rotamaps.Posicao;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ParadasActivity extends FragmentActivity {
	
	private GoogleMap gm;
	private ProgressDialog mProgress;
	private boolean ativarMarcador = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paradas);
        
        SupportMapFragment Map = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        gm = Map.getMap();
        gm.setMyLocationEnabled(true);
        gm.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				if (!ativarMarcador)
					return;
				double latitude = point.latitude;
				double longitude = point.longitude;
				Toast.makeText(ParadasActivity.this, latitude + ", " + longitude, Toast.LENGTH_SHORT).show();				
				new SalvarParadaTask(latitude, longitude).execute();
			}
		});   
               
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        Location local = locationManager.getLastKnownLocation(provider);

        if(local!=null){
        	LatLng posicao = new LatLng(local.getLatitude(), local.getLongitude());
        	gm.animateCamera(CameraUpdateFactory.newLatLngZoom(posicao, 15)); 
        	gm.addMarker(new MarkerOptions().position(posicao).title("Voc� est� aqui!"));    
        }
        
        new ListarParadasTask().execute();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.paradas, menu);
		return true;
	}
	//==
	private class SalvarParadaTask extends AsyncTask<String, String, Boolean> {
		private double latitude;
		private double longitude;
		
		public SalvarParadaTask(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}
		
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(ParadasActivity.this, "Aguarde", "Salvando suas informa��es no servidor.", true);
		}
		
		@Override 
		protected Boolean doInBackground(String... params) {
			OnibusDAOJSON dao = new OnibusDAOJSON();
			boolean teste = dao.salvarParada(new Posicao(latitude, longitude));
			return teste;
		}
		
		@Override
		protected void onPostExecute(Boolean teste) {
			mProgress.cancel();
			if (teste) {
				gm.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
						.title("Parada")
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.onibus_marcador)));
				Toast.makeText(ParadasActivity.this, "A parada foi salva com sucesso.", Toast.LENGTH_SHORT).show();				
			} else {
				Toast.makeText(ParadasActivity.this, "Erro ao tentar salvar parada. Verifique sua conex�o.", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	//==
	private class ListarParadasTask extends AsyncTask<String, String, Parada> {
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(ParadasActivity.this, "Aguarde", "Carregando informa��es do servidor.", true);
		}
		
		@Override 
		protected Parada doInBackground(String... params) {
			OnibusDAOJSON dao = new OnibusDAOJSON();
			Parada parada = dao.pegarParadas();
			return parada;
		}
		
		@Override
		protected void onPostExecute(Parada paradas) {
			mProgress.cancel();
			for (Posicao pos : paradas.getParadas()) {
				LatLng point = new LatLng(pos.getLatitude(), pos.getLongitude());
				gm.addMarker(new MarkerOptions().position(point)
						.title("Parada")
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.onibus_marcador)));
			}
		}
	}

}
 