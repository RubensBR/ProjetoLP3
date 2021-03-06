package busaojp.onibus;

public class Onibus {

	private String linha;
	private String nome;
	private Rota rota;
	private String[] horarios;
	
	public Onibus() {}
	
	public Onibus(String linha, String nome) {
		this.nome = nome;
		this.linha = linha;
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

	public String[] getHorarios() {
		return horarios;
	}
	public void setHorarios(String[] horarios) {
		this.horarios = horarios;
	}
	
}
