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
				"10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
				"20:00", "21:00", "22:00", "23:00"};
		
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
		linha = linha.toLowerCase();
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		for (Onibus onibus : banco) {
			
			if (onibus.getLinha().toLowerCase().contains(linha) || onibus.getNome().toLowerCase().contains(linha))
				lista.add(onibus);
		}
		return lista;
	}

	@Override
	public ArrayList<Onibus> buscaPorLogradouro(String logradouro) {
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		logradouro = logradouro.toLowerCase();
		boolean achouNaIda;
		for (Onibus onibus : banco) {
			achouNaIda = false;
			for (String rua : onibus.getRota().getIda()) {
				rua = rua.toLowerCase();
				if (rua.contains(logradouro)) { 
					lista.add(onibus);
					achouNaIda = true;
					break;
				}
			}
			if (!achouNaIda) {
				System.out.println("entrouuuuu");
				for (String rua : onibus.getRota().getVolta()) {
					rua = rua.toLowerCase();
					if (rua.contains(logradouro)) {						
						lista.add(onibus);	
						break;
					}
				}
			}
		}
		return lista;
	}
}
