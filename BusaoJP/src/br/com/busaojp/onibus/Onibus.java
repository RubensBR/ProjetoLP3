package br.com.busaojp.onibus;

import java.io.Serializable;
import java.util.ArrayList;

public class Onibus implements Serializable {

	private static final long serialVersionUID = 1519181079526438484L;
	
	private String linha;
	private String nome;
	private Rota rota;
	private ArrayList<String> horarios;
	
	public Onibus(String linha, String nome, Rota rota, ArrayList<String> horarios) {
		this.nome = nome;
		this.linha = linha;
		this.rota = rota;
		this.horarios = horarios;
	}
	
	public String getLinha() {
		return linha;
	}
	public void setLinha(String linha) {
		this.linha = linha;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Rota getRota() {
		return rota;
	}
	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public ArrayList<String> getHorarios() {
		return horarios;
	}
	public void setHorarios(ArrayList<String> horarios) {
		this.horarios = horarios;
	}
	
}
