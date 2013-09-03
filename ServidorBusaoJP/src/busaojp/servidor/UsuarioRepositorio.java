package busaojp.servidor;

import java.util.ArrayList;

public class UsuarioRepositorio {

	private static ArrayList<Usuario> lista;
	
	public static ArrayList<Usuario> getUsuarios() {
		if (lista == null)
			lista = new ArrayList<Usuario>();
		return lista;
	}
}
 

