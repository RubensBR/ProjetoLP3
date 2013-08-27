package busaojp.servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import busao.jp.utils.LeitorKml;
import busao.jp.utils.Marcador;
import busao.jp.utils.Posicao;
import busao.jp.utils.RotaMapeada;
import busaojp.onibus.Onibus;
import busaojp.onibus.OnibusDAO;
import busaojp.onibus.RotaMaps;

/**
 * Servlet implementation class Servidor
 */
@WebServlet("/Servidor")
public class Servidor extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static OnibusDAO onibusDAO;   
    public Servidor() {
        super();
        onibusDAO = OnibusDAOFactory.getFactory();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Requisição get recebida");
		PrintWriter out = response.getWriter();
		
		/*onibusDAO = new OnibusDAOListVersion();
		ArrayList<Onibus> onibus = onibusDAO.lista();
		JSONArray jsonArray = new JSONArray(onibus);
		out.println(jsonArray.toString());
		System.out.println("Resposta enviada");*/
		
		RotaMapeada ida = new LeitorKml().pegarRotaMapeada("1502", LeitorKml.IDA);
		
		JSONObject json = new JSONObject(new RotaMaps(ida, new RotaMapeada(new ArrayList<Posicao>(), new ArrayList<Marcador>())));
		
		try {
			JSONObject j1 = json.getJSONObject("volta");
			JSONArray rota = j1.getJSONArray("rota");
			out.println("Rota volta: ");
			for (int i = 0; i < rota.length(); ++i) {
				JSONObject posicao = rota.getJSONObject(i);
				out.println("Latitude, Longitude: " + posicao.getDouble("latitude") + ", " + posicao.getDouble("longitude"));
			}
			JSONArray marcadores = j1.getJSONArray("marcadores");
			out.println("Marcadores: ");
			for (int i = 0; i < marcadores.length(); ++i) {
				JSONObject marcador = marcadores.getJSONObject(i);
				out.println("{ Nome: " + marcador.getString("nome"));
				JSONObject marcadorPosicao = marcador.getJSONObject("posicao");
				out.println("Posicao: " + marcadorPosicao.getDouble("latitude") + marcadorPosicao.getDouble("longitude") + "}");
				
			}
			
			
			JSONObject j2 = json.getJSONObject("ida");
			JSONArray rota2 = j2.getJSONArray("rota");
			out.println("Rota ida: ");
			for (int i = 0; i < rota2.length(); ++i) {
				JSONObject posicao = rota2.getJSONObject(i);
				out.println("Latitude, Longitude: " + posicao.getDouble("latitude") + ", " + posicao.getDouble("longitude"));
			}		
			
			marcadores = j2.getJSONArray("marcadores");
			out.println("Marcadores: ");
			for (int i = 0; i < marcadores.length(); ++i) {
				JSONObject marcador = marcadores.getJSONObject(i);
				out.println("{ Nome: " + marcador.getString("nome"));
				JSONObject marcadorPosicao = marcador.getJSONObject("posicao");
				out.println("Posicao: " + marcadorPosicao.getDouble("latitude") + marcadorPosicao.getDouble("longitude") + "}");
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Requisicao post recebida");
		String operacao = request.getParameter("operacao");
		Integer op = Integer.parseInt(operacao);
		JSONArray onibusJSON;
		ArrayList<Onibus> listaOnibus;
		PrintWriter out = response.getWriter();
		switch (op) {
			case Operacoes.GET_POR_LINHA_NOME:
				listaOnibus = onibusDAO.buscaPorLinha(request.getParameter("busca"));
				onibusJSON = new JSONArray(listaOnibus);
				out.println(onibusJSON.toString());
				System.out.println("operacao: " + operacao + ", " + "busca: " + request.getParameter("busca"));
				System.out.println(onibusJSON.toString());
				break;
			
			case Operacoes.GET_POR_LOGRADOURO:
				listaOnibus = onibusDAO.buscaPorLogradouro(request.getParameter("busca"));
				onibusJSON = new JSONArray(listaOnibus);
				out.println(onibusJSON.toString());
				System.out.println("operacao: " + operacao + ", " + "busca: " + request.getParameter("busca"));
				System.out.println(onibusJSON.toString());
				break;
		}
		System.out.println("Resposta post enviada");
		
	}
	
	
}
 