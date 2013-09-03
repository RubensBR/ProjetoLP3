package br.com.busaojp.onibus;

import java.io.Serializable;
import java.util.ArrayList;

public class Rota implements Serializable {
	private static final long serialVersionUID = -7265884599221416157L;
	
	private ArrayList<String> ida;
	private ArrayList<String> volta;
	private String sentidoIda;
	private String sentidoVolta;
	
	public Rota() {
		ida = new ArrayList<String>();
		volta = new ArrayList<String>();
	}	
	
	public Rota(ArrayList<String> ida, ArrayList<String> volta) {
		this.ida = ida;
		this.volta = volta;
	}
	
	public ArrayList<String> getIda() {
		return ida;
	}
	public void setIda(ArrayList<String> ida) {
		this.ida = ida;
	}
	public ArrayList<String> getVolta() {
		return volta;
	}
	public void setVolta(ArrayList<String> volta) {
		this.volta = volta;
	}
	public String getSentidoIda() {
		return sentidoIda;
	}

	public void setSentidoIda(String sentidoIda) {
		this.sentidoIda = sentidoIda;
	}



	public String getSentidoVolta() {
		return sentidoVolta;
	}



	public void setSentidoVolta(String sentidoVolta) {
		this.sentidoVolta = sentidoVolta;
	}



	public void addRotaIda(ArrayList<String> rota) {
		this.ida = rota;
	}
	
	public void addRotaVolta(ArrayList<String> rota) {
		this.volta = rota;
	}
	
}
