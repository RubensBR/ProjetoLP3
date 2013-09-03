package br.com.busaojp.onibus;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.busaojp.rotamaps.Marcador;
import br.com.busaojp.rotamaps.Parada;
import br.com.busaojp.rotamaps.Posicao;
import br.com.busaojp.rotamaps.RotaMapeada;
import br.com.busaojp.rotamaps.RotaMaps;
import br.com.busaojp.utils.HttpUtil;
import br.com.busaojp.utils.Operacoes;

public class OnibusDAOJSON implements OnibusDAO {

	private final String ENDERECO = "http://192.168.0.14:8080/ServidorBusaoJP/Servidor";

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
		/*Onibus onibus = null;
		try {
			HttpUtil.urlContentPost(ENDERECO, "buscar", linha);
			
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}*/
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

	@Override
	public RotaMaps buscaRotaMaps(String linha) {
		try {
			String res = HttpUtil.urlContentPost(ENDERECO, "operacao", ""+Operacoes.GET_ROTA_MAPS, "busca", linha);
			System.out.println(res);
			System.out.println("tamanho: " + res.length());
			
			JSONObject json = new JSONObject(res);
			
			ArrayList<Posicao> posVolta = new ArrayList<Posicao>();
			ArrayList<Marcador> marcVolta = new ArrayList<Marcador>();
			
			JSONObject voltaJSON = json.getJSONObject("volta");
			JSONArray rotaVolta = voltaJSON.getJSONArray("rota");
			
			for (int i = 0; i < rotaVolta.length(); ++i) {
				JSONObject posicao = rotaVolta.getJSONObject(i);
				double latitude = posicao.getDouble("latitude");
				double longitude = posicao.getDouble("longitude");
				posVolta.add(new Posicao(latitude, longitude));
			}
			
			JSONArray marcadores = voltaJSON.getJSONArray("marcadores");
			
			for (int i = 0; i < marcadores.length(); ++i) {
				JSONObject marcador = marcadores.getJSONObject(i);
				JSONObject marcadorPosicao = marcador.getJSONObject("posicao");
				String nome = marcador.getString("nome");
				double latitude = marcadorPosicao.getDouble("latitude");
				double longitude = marcadorPosicao.getDouble("longitude");
				marcVolta.add(new Marcador(nome, new Posicao(latitude, longitude)));
			}		
			
			//Ida
			ArrayList<Posicao> posIda = new ArrayList<Posicao>();
			ArrayList<Marcador> marcIda = new ArrayList<Marcador>();
			
			JSONObject idaJSON = json.getJSONObject("ida");
			JSONArray rotaIda = idaJSON.getJSONArray("rota");
			
			for (int i = 0; i < rotaIda.length(); ++i) {
				JSONObject posicao = rotaIda.getJSONObject(i);
				double latitude = posicao.getDouble("latitude");
				double longitude = posicao.getDouble("longitude");
				posIda.add(new Posicao(latitude, longitude));
			}		
			
			marcadores = idaJSON.getJSONArray("marcadores");
			
			for (int i = 0; i < marcadores.length(); ++i) {
				JSONObject marcador = marcadores.getJSONObject(i);
				JSONObject marcadorPosicao = marcador.getJSONObject("posicao");		
				String nome = marcador.getString("nome");
				double latitude = marcadorPosicao.getDouble("latitude");
				double longitude = marcadorPosicao.getDouble("longitude");
				marcIda.add(new Marcador(nome, new Posicao(latitude, longitude)));
			}
			
			return new RotaMaps(new RotaMapeada(posIda, marcIda), new RotaMapeada(posVolta, marcVolta));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean salvarParada(Posicao pos) {
		try {
			 HttpUtil.urlContentPost(ENDERECO, "operacao", ""+Operacoes.SALVAR_PARADA, 
					"latitude", ""+pos.getLatitude(), "longitude", ""+pos.getLongitude());			 
		} catch (ClientProtocolException e) {			
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
		System.out.println("== salvar ok");
		return true;
	}
	
	public Parada pegarParadas() {
		Parada paradas = new Parada();
		try {
			String s = HttpUtil.urlContentPost(ENDERECO, "operacao", ""+Operacoes.GET_PARADAS);			
			JSONObject json = new JSONObject(s);
			JSONArray ar = json.getJSONArray("paradas");
			for (int i = 0; i < ar.length(); ++i) {
				JSONObject parada = ar.getJSONObject(i);
				double latitude = parada.getDouble("latitude");
				double longitude = parada.getDouble("longitude");
				paradas.getParadas().add(new Posicao(latitude, longitude));
			}
			return paradas;
			
		} catch (ClientProtocolException e) {			
			e.printStackTrace();	
			return paradas;
		} catch (IOException e) {
			e.printStackTrace();
			return paradas;
		} catch (JSONException e) {
			e.printStackTrace();
			return paradas;
		}
	}
	
	public int FazerLogin(String login, String senha) {
		try {
			String s = HttpUtil.urlContentPost(ENDERECO, "operacao", ""+Operacoes.LOGIN, "login", login, "senha", senha);
			JSONObject json = new JSONObject(s);
			boolean sucesso = json.getBoolean("sucesso");
			if (sucesso) {
				return 0;
			} else {
				return 1;
			}			
		} catch (ClientProtocolException e) {			
			e.printStackTrace();
			return 2;
		} catch (IOException e) {			
			e.printStackTrace();
			return 2;
		} catch (JSONException e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	public int CadastrarUsuario(String login, String senha) {
		try {
			String s = HttpUtil.urlContentPost(ENDERECO, "operacao", ""+Operacoes.CADASTRO, "login", login, "senha", senha);
			JSONObject json = new JSONObject(s);
			boolean sucesso = json.getBoolean("sucesso");
			if (sucesso) {
				return 0;
			} else {
				return 1;
			}	
		} catch (ClientProtocolException e) {			
			e.printStackTrace();
			return 2;
		} catch (IOException e) {			
			e.printStackTrace();
			return 2;
		} catch (JSONException e) {
			e.printStackTrace();
			return 2;
		}
	}
}
