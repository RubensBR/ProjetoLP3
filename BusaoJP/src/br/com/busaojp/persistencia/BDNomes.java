package br.com.busaojp.persistencia;

public interface BDNomes {
	public static final String TABELA_ONIBUS = "onibus";
	public static final String LINHA = "linha";
	public static final String NOME = "nome";
	
	public static final String TABELA_ITINERARIO = "itinerario";
	public static final String ORDEM = "ordem";
	public static final String LOGRADOURO = "logradouro";
	public static final String SENTIDO = "sentido";
	
	public static final String TABELA_COORDENADAS = "coordenadas";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	
	public static final String TABELA_MARCADORES = "marcadores";
	public static final String MARCADOR_ID = "marcador_id";
	public static final String DESCRICAO = "descricao";
	
	public static final String TABELA_PARADAS = "paradas";
	public static final String PARADA_ID = "parada_id";	
	
	public static final String TABELA_HORARIOS = "horarios";
	public static final String HORARIO_ID = "horario_id";
	public static final String HORA = "hora";
	
	public static final String IDA = "I";
	public static final String VOLTA = "V";
}
