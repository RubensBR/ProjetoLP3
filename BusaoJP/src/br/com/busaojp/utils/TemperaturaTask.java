package br.com.busaojp.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.TextView;

public class TemperaturaTask extends AsyncTask<String, String, String>{
	
	private TextView texto;
	
	public TemperaturaTask(TextView texto) {
		this.texto = texto;
	}
	
	@Override
	protected String doInBackground(String... params) {
		try {
			String res = HttpUtil.urlContentGet("http://api.openweathermap.org/data/2.5/weather?q=paraiba,br&units=metric");
			return res;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Erro";
	}
	
	@Override
	protected void onPostExecute(String res) {
		try {
			JSONObject json = new JSONObject(res);
			JSONObject clima = json.getJSONObject("main");
			String temperatura = clima.getString("temp_max");
			texto.setText("Temperatura: " + temperatura + " °C");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
