package busao.jp.utils;

import java.util.ArrayList;

public class RotaMapeada {
	private ArrayList<Posicao> rota;
	private ArrayList<Marcador> marcadores;		
	
	public RotaMapeada(ArrayList<Posicao> rota, ArrayList<Marcador> marcadores) {
		this.rota = rota;
		this.marcadores = marcadores;
	}
	public ArrayList<Posicao> getRota() {
		return rota;
	}
	public void setRota(ArrayList<Posicao> rota) {
		this.rota = rota;
	}
	public ArrayList<Marcador> getMarcadores() {
		return marcadores;
	}
	public void setMarcadores(ArrayList<Marcador> marcadores) {
		this.marcadores = marcadores;
	}
	
	
}
