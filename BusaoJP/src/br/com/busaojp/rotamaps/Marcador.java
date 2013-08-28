package br.com.busaojp.rotamaps;

import java.io.Serializable;

public class Marcador implements Serializable {

	private static final long serialVersionUID = -3570960838807412550L;
	private String nome;
	private Posicao posicao;
	
	public Marcador(String nome, Posicao posicao) {
		this.nome = nome;
		this.posicao = posicao;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Posicao getPosicao() {
		return posicao;
	}
	public void setPosicao(Posicao posicao) {
		this.posicao = posicao;
	}
	
	
}
