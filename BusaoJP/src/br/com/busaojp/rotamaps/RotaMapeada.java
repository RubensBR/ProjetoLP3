package br.com.busaojp.rotamaps;

import java.io.Serializable;
import java.util.ArrayList;

public class RotaMapeada implements Serializable {
	
	private static final long serialVersionUID = 2230420465148422905L;
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
