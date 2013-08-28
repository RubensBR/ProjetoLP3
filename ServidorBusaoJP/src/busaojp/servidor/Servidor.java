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
import org.json.JSONObject;

import busao.jp.utils.LeitorKml;
import busao.jp.utils.Marcador;
import busao.jp.utils.Posicao;
import busao.jp.utils.RotaMapeada;
import busaojp.onibus.Onibus;
import busaojp.onibus.OnibusDAO;
import busaojp.onibus.OnibusDAOListVersion;
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
		onibusDAO = new OnibusDAOListVersion();
		ArrayList<Onibus> onibus = onibusDAO.lista();
		JSONArray jsonArray = new JSONArray(onibus);
		out.println(jsonArray.toString());
		System.out.println("Resposta enviada");
		
		
		
		/*PrintWriter out = response.getWriter();
		RotaMaps rota = pegarRotaMaps("1502");
		JSONObject json = new JSONObject(rota);
		out.println(json.toString()); 
		System.out.println(json.toString());*/
		
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
				
			case Operacoes.GET_ROTA_MAPS:
				RotaMaps rota = pegarRotaMaps(request.getParameter("busca"));
				JSONObject json = new JSONObject(rota);
				out.println(json.toString()); 
				System.out.println("tamanho: " + json.toString().length());
				
				break;
		}
		System.out.println("Resposta post enviada");
		
	}
	
	public RotaMaps pegarRotaMaps(String linha) {
		RotaMapeada ida = new LeitorKml().pegarRotaMapeada(linha, LeitorKml.IDA);
		//RotaMapeada volta = new LeitorKml().pegarRotaMapeada("linha", LeitorKml.VOLTA);
		//RotaMapeada volta = new RotaMapeada(new ArrayList<Posicao>(), new ArrayList<Marcador>());		
		/*try {
			JSONObject j1 = json.getJSONObject("volta");
			JSONArray rota = j1.getJSONArray("rota");
			for (int i = 0; i < rota.length(); ++i) {
				JSONObject posicao = rota.getJSONObject(i);
			}
			JSONArray marcadores = j1.getJSONArray("marcadores");
			for (int i = 0; i < marcadores.length(); ++i) {
				JSONObject marcador = marcadores.getJSONObject(i);
				JSONObject marcadorPosicao = marcador.getJSONObject("posicao");
				
			}			
			
			JSONObject j2 = json.getJSONObject("ida");
			JSONArray rota2 = j2.getJSONArray("rota");
			for (int i = 0; i < rota2.length(); ++i) {
				JSONObject posicao = rota2.getJSONObject(i);
			}		
			
			marcadores = j2.getJSONArray("marcadores");
			for (int i = 0; i < marcadores.length(); ++i) {
				JSONObject marcador = marcadores.getJSONObject(i);
				JSONObject marcadorPosicao = marcador.getJSONObject("posicao");				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return new RotaMaps(ida, ida);
	}
	
	
}
 