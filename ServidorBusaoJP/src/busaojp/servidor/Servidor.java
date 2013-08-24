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

import busaojp.onibus.Onibus;
import busaojp.onibus.OnibusDAO;
import busaojp.onibus.OnibusDAOListVersion;

/**
 * Servlet implementation class Servidor
 */
@WebServlet("/Servidor")
public class Servidor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Servidor() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Requisição get recebida");
		PrintWriter out = response.getWriter();
		
		OnibusDAO dao = new OnibusDAOListVersion();
		ArrayList<Onibus> onibus = dao.lista();
		JSONArray jsonArray = new JSONArray(onibus);
		try {
			JSONArray ar = new JSONArray(jsonArray.toString());
				for (int i = 0; i < ar.length(); ++i) {
				JSONObject json = ar.getJSONObject(i);
				JSONObject rota = json.getJSONObject("rota");
				JSONArray ida = rota.getJSONArray("ida");
				JSONArray volta = rota.getJSONArray("volta");
				String linha = json.getString("linha");
				String nome = json.getString("nome");
			
				out.println("=== " + linha + " - " + nome + " ===");
				out.println("Rota Ida:");
				for (int rua = 0; rua < ida.length(); ++rua) {
					String r = ida.getString(rua);
					out.println(r);					
				}
				out.println("Rota volta:");
				for (int rua = 0; rua < volta.length(); ++rua) {
					String r = volta.getString(rua);
					out.println(r + "");					
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
 