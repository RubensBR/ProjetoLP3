package br.com.busaojp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import br.com.busaojp.onibus.OnibusDAOJSON;
import br.com.busaojp.rotamaps.Parada;
import br.com.busaojp.rotamaps.Posicao;
import br.com.busaojp.utils.ActivityUtil;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ParadasActivity extends FragmentActivity {
	
	private GoogleMap gm;
	private ProgressDialog mProgress;
	private boolean ativarMarcador = false;
	private Button marcadorButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paradas);
		
        marcadorButton = (Button) findViewById(R.id.ativar_desativar_marcador);
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
        	gm.addMarker(new MarkerOptions().position(posicao).title("Você está aqui!"));    
        }
        
		setBackground();
        new ListarParadasTask().execute();        
	}
	
	public void ativarDesativarMarcaParada(View v) {
		Button ativarDesativarButton = (Button) v;
		if (ativarMarcador) {
			ativarMarcador = false;
			ativarDesativarButton.setText(R.string.adicionar_parada);
		} else {
			ativarMarcador = true;
			ativarDesativarButton.setText("Cancelar");
			Toast.makeText(this, "Clique na posição que está localizada a parada para salvá-la.",
					Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
    protected void onPause() {
      super.onPause();
      Music.stop(this);
    }
    
	public void onResume(){
		super.onResume();
		Music.play(this, R.raw.tar);
		setBackground();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferencias, menu);
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
			mProgress = ProgressDialog.show(ParadasActivity.this, "Aguarde", "Salvando suas informações no servidor.", true);
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
				
				ativarMarcador = false;				
				marcadorButton.setText(R.string.adicionar_parada);
				
			} else {
				Toast.makeText(ParadasActivity.this, "Erro ao tentar salvar parada. Verifique sua conexão.", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	//==
	private class ListarParadasTask extends AsyncTask<String, String, Parada> {
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(ParadasActivity.this, "Aguarde", "Carregando informações do servidor.", true);
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
    
    public void setBackground(){		
		LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayoutParadas);
		BusaoPreferences.backgroundPreferencia(layout, this);
	}
}
 
