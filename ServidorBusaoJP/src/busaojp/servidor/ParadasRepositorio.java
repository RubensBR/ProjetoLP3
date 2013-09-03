package busaojp.servidor;

import java.util.ArrayList;

import busao.jp.utils.Posicao;

public class ParadasRepositorio {
	private static ArrayList<Posicao> paradas;
	
	public static ArrayList<Posicao> getParadas() {
		if (paradas == null)
			paradas = new ArrayList<Posicao>();
		
		return paradas;
	}
}
