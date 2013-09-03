package br.com.busaojp.onibus;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.busaojp.rotamaps.RotaMaps;

public class Onibus implements Serializable {
	
	private static final long serialVersionUID = -2525266224550789952L;
	private String linha;
	private String nome;
	private Rota rota;
	private ArrayList<String> horarios;
	private RotaMaps rotaMaps;
	
	public RotaMaps getRotaMaps() {
		return rotaMaps;
	}

	public void setRotaMaps(RotaMaps rotaMaps) {
		this.rotaMaps = rotaMaps;
	}

	public Onibus(String linha, String nome) {
		this.linha = linha;
		this.nome = nome;
	}
	
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
