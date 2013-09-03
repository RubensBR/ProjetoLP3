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
				
			case Operacoes.SALVAR_PARADA:
				double latitude = Double.parseDouble(request.getParameter("latitude"));
				double longitude = Double.parseDouble(request.getParameter("longitude"));
				ParadasRepositorio.getParadas().add(new Posicao(latitude, longitude));
				System.out.println("Latitude, Longitude: " + latitude + ", " + longitude);
				break;
			
			case Operacoes.GET_PARADAS:
				JSONObject jsonParadas = new JSONObject(new ParadasRepositorio());
				out.println(jsonParadas.toString());
				System.out.println("get_paradas: " + jsonParadas.toString());
				break;
				
			case Operacoes.LOGIN:
				ValidacaoLogin valida = new ValidacaoLogin();
				valida.validarLogin(request.getParameter("login"), request.getParameter("senha"));
				JSONObject jsonSucesso = new JSONObject(valida);
				out.println(jsonSucesso.toString());
				System.out.println("login: " + jsonSucesso.toString());
				break;
				
			case Operacoes.CADASTRO:
				String login = request.getParameter("login");
				String senha = request.getParameter("senha");
				ArrayList<Usuario> usuarios = UsuarioRepositorio.getUsuarios();
				boolean teste = false;
				for (Usuario usr : usuarios) {
					if (usr.getLogin().equals(login)) {						
						teste = true;
						break;
					}
				}
				if (teste) {
					out.println("{\"sucesso\":false}");
				} else {
					usuarios.add(new Usuario(login, senha));
					out.println("{\"sucesso\":true}");
				}
				break;
		}
		System.out.println("Resposta post enviada");		
	}
	
	public RotaMaps pegarRotaMaps(String linha) {
		RotaMapeada ida = new LeitorKml().pegarRotaMapeada(linha, LeitorKml.IDA);		
		return new RotaMaps(ida, ida);
	}
	
	
}
 