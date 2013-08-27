package busaojp.onibus;

import java.util.ArrayList;

public class OnibusDAOListVersion implements OnibusDAO {

	private static ArrayList<Onibus> banco;
	
	public OnibusDAOListVersion() {
		if (banco == null) {
			banco = gerarBanco();
		}
	}
	
	private ArrayList<Onibus> gerarBanco() {
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		String[] horarios = {"05:00", "06:00", "07:00", "08:00", "09:00",
				"10:00", "11:00"};
		
		for (int linha = 1; linha <= 20; ++linha) {
			Onibus onibus = new Onibus("00" + linha, "Onibus " + linha);
			Rota rota = new Rota();
			ArrayList<String> ruas = new ArrayList<String>();
			for (int rua = 1; rua <= 10; ++rua) {
				ruas.add("rua " + rua); 
			}
			
			if (linha == 13) {
				ruas.add("rua especial");
			}
			rota.addRotaIda(ruas);
			rota.addRotaVolta(ruas);
			onibus.setRota(rota);
			onibus.setHorarios(horarios);
			lista.add(onibus);
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
