package busaojp.onibus;

import java.util.ArrayList;

import busao.jp.utils.LeitorItinerario;

public class OnibusDAOListVersion implements OnibusDAO {

	private static ArrayList<Onibus> banco;
	
	public OnibusDAOListVersion() {
		if (banco == null) {
			banco = gerarBanco();
		}
	}
	
	private ArrayList<Onibus> gerarBanco() {
		ArrayList<Onibus> lista = LeitorItinerario.pegarItinerarios();
		String[] horarios = {"05:00", "06:00", "07:00", "08:00", "09:00",
				"10:00", "11:00"};
		
		for (Onibus bus : lista) {
			bus.setHorarios(horarios);
		}
		
		return lista;
	}

	@Override
	public ArrayList<Onibus> lista() {
		return banco;
	}

	@Override
	public Onibus getOnibus(String linha) {
		for (Onibus onibus : banco) {
			if (onibus.getLinha().equals(linha))
				return onibus;
		}
		return new Onibus("0", "0");
	}

	@Override
	public ArrayList<Onibus> buscaPorLinha(String linha) {
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		for (Onibus onibus : banco) {
			if (onibus.getLinha().contains(linha) || onibus.getNome().contains(linha))
				lista.add(onibus);
		}
		return lista;
	}

	@Override
	public ArrayList<Onibus> buscaPorLogradouro(String logradouro) {
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		for (Onibus onibus : banco) {
			for (String rua : onibus.getRota().getIda()) {
				if (rua.contains(logradouro)) { 
					lista.add(onibus);
					break;
				}
			}
		}
		return lista;
	}
}
