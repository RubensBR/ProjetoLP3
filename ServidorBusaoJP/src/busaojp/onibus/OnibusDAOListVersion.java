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
			Onibus onibus = new Onibus("000" + linha, "Onibus " + linha);
			Rota rota = new Rota();
			ArrayList<String> ruas = new ArrayList<String>();
			for (int rua = 1; rua <= 10; ++rua) {
				ruas.add("rua " + rua); 
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
		return new Onibus("0000", "Não encontrado");
	}
}
