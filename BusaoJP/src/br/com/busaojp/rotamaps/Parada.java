package br.com.busaojp.rotamaps;

import java.io.Serializable;
import java.util.ArrayList;

public class Parada implements Serializable {
	
	private static final long serialVersionUID = 3962599086692846244L;
	
	private ArrayList<Posicao> paradas;
	
	public Parada() {
		paradas = new ArrayList<Posicao>();
	}	

	public Parada(ArrayList<Posicao> paradas) {
		this.paradas = paradas;
	}

	public ArrayList<Posicao> getParadas() {
		return paradas;
	}

	public void setParadas(ArrayList<Posicao> paradas) {
		this.paradas = paradas;
	}
	
	
}
