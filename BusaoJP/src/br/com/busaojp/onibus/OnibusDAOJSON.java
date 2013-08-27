package br.com.busaojp.onibus;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.OpenableColumns;
import br.com.busaojp.utils.HttpUtil;
import br.com.busaojp.utils.Operacoes;

public class OnibusDAOJSON implements OnibusDAO {

	private final String ENDERECO = "http://192.168.0.11:8080/ServidorBusaoJP/Servidor";
	
	@Override
	public ArrayList<Onibus> lista() {		
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		try {
			//Se conecta e processa o JSON
			String res = HttpUtil.urlContentGet(ENDERECO);
			lista = processaJSON(res);			
		} catch (ClientProtocolException e) {			
			return null;
		} catch (IOException e) {			
			return null;
		}
		return lista;
	}

	@Override
	public Onibus getOnibus(String linha) {
		Onibus onibus = null;
		try {
			HttpUtil.urlContentPost(ENDERECO, "buscar", linha);
			
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<Onibus> buscaPorLinha(String linha) {
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		try {
			String res = HttpUtil.urlContentPost(ENDERECO, "operacao", ""+Operacoes.GET_POR_LINHA_NOME, "busca", linha);
			lista = processaJSON(res);
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return lista;
	}

	@Override
	public ArrayList<Onibus> buscaPorLogradouro(String logradouro) {
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		try {
			String res = HttpUtil.urlContentPost(ENDERECO, "operacao", ""+Operacoes.GET_POR_LOGRADOURO, "busca", logradouro);
			lista = processaJSON(res);
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return lista;
	}	
	
	public ArrayList<Onibus> processaJSON(String json) {
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			
			//monta os objetos e adiciona na lista
			for (int i = 0; i < jsonArray.length(); ++i) {
				JSONObject obj = jsonArray.getJSONObject(i);
				JSONObject rota = obj.getJSONObject("rota");
				JSONArray ida = rota.getJSONArray("ida");
				JSONArray volta = rota.getJSONArray("volta");
				String linha = obj.getString("linha");
				String nome = obj.getString("nome");
				JSONArray horarios = obj.getJSONArray("horarios");
				
				ArrayList<String> rotaIda = new ArrayList<String>();
				for (int rua = 0; rua < ida.length(); ++ rua) {
					String r = ida.getString(rua);
					rotaIda.add(r);						
				}
				
				ArrayList<String> rotaVolta = new ArrayList<String>();
				for (int rua = 0; rua < volta.length(); ++ rua) {
					String r = volta.getString(rua);
					rotaVolta.add(r);
				}					
				
				ArrayList<String> hrs = new ArrayList<String>();
				for (int h = 0; h < horarios.length(); ++ h) {
					String r = horarios.getString(h);
					hrs.add(r);
				}
				Rota itinerario = new Rota();
				itinerario.addRotaIda(rotaIda);
				itinerario.addRotaVolta(rotaVolta);
				Onibus onibus = new Onibus(linha, nome, itinerario, hrs);				
				lista.add(onibus);
			}
			
		} catch (JSONException e) {
			return null;		
		}
		return lista;
	}
	
}
