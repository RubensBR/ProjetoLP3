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
		for (int linha = 1; linha <= 4; ++linha) {
			Onibus onibus = new Onibus("000" + linha, "Onibus " + linha);
			Rota rota = new Rota();
			ArrayList<String> ruas = new ArrayList<String>();
			for (int rua = 1; rua <= 10; ++rua) {
				ruas.add("rua " + rua); 
			}
			rota.addRotaIda(ruas);
			rota.addRotaVolta(ruas);
			onibus.setRota(rota);
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
