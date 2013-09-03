package br.com.busaojp.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDBusaoJP extends SQLiteOpenHelper{

	private static final String NOME_BD = "busao_jp.db";
	private static final int VERSAO_BD = 1;
	
	public BDBusaoJP(Context context) {
		super(context, NOME_BD, null, VERSAO_BD);
	}

	@Override
	public void onCreate(SQLiteDatabase bd) {
		criarTabelaOnibus(bd);
		criarTabelaItinerario(bd);
		criarTabelaCoordenadas(bd);
		criarTabelaMarcadores(bd);
		criarTabelaParadas(bd);
		criarTabelaHorarios(bd);
	}

	@Override
	public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
		bd.execSQL("DROP TABLE IF EXISTS " + "onibus");
		bd.execSQL("DROP TABLE IF EXISTS " + "itinerario");
		bd.execSQL("DROP TABLE IF EXISTS " + "coordenadas");
		bd.execSQL("DROP TABLE IF EXISTS " + "marcadores");
		bd.execSQL("DROP TABLE IF EXISTS " + "paradas");
		bd.execSQL("DROP TABLE IF EXISTS " + "horarios");
		onCreate(bd);		
	}
	
	private void criarTabelaOnibus(SQLiteDatabase bd) {
		String tabela = "CREATE TABLE onibus( " +
							"linha VARCHAR(4), "+
							"nome VARCHAR(4) NOT NULL, "+
							"PRIMARY KEY(linha) " +
						 ");";
		bd.execSQL(tabela);
	}
	
	private void criarTabelaItinerario(SQLiteDatabase bd) {
		String tabela = "CREATE TABLE itinerario( " +
							"linha VARCHAR(4), " +
							"ordem INTEGER, " +
							"logradouro TEXT NOT NULL, " +
							"sentido VARCHAR(1), " +
							"PRIMARY KEY(linha, ordem, sentido), " +
							"FOREIGN KEY(linha) REFERENCES onibus(linha) ON DELETE CASCADE " +
						");";
		bd.execSQL(tabela);
	}
	
	private void criarTabelaCoordenadas(SQLiteDatabase bd) {
		String tabela = "CREATE TABLE coordenadas( " +
							"linha VARCHAR(4), " +
							"ordem INTEGER, " +
							"latitude REAL NOT NULL, " +
							"longitude REAL NOT NULL, " +
							"sentido VARCHAR(1), " +
							"PRIMARY KEY(linha, ordem, sentido), " +
							"FOREIGN KEY(linha) REFERENCES onibus(linha) ON DELETE CASCADE " +
						");";
		bd.execSQL(tabela);
	}
	
	private void criarTabelaMarcadores(SQLiteDatabase bd) {
		String tabela = "CREATE TABLE marcadores( " +
							"marcador_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
							"linha VARCHAR(4) NOT NULL, " +
							"latitude REAL NOT NULL, " +
							"longitude REAL NOT NULL, " +
							"descricao TEXT NULL, " +
							"sentido VARCHAR(1) NOT NULL, " +
							"FOREIGN KEY(linha) REFERENCES onibus(linha) ON DELETE CASCADE " +
						");";
		bd.execSQL(tabela);
	}
	
	private void criarTabelaParadas(SQLiteDatabase bd) {
		String tabela = "CREATE TABLE paradas( " +
							"parada_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
							"latitude REAL NOT NULL, " +
							"longitude REAL NOT NULL " +
						");";
		bd.execSQL(tabela);
	}
	
	private void criarTabelaHorarios(SQLiteDatabase bd) {
		String tabela = "CREATE TABLE horarios( " +
							"horario_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
							"linha VARCHAR(4), " +
							"hora VARCHAR(5), " +
							"FOREIGN KEY(linha) REFERENCES onibus(linha) ON DELETE CASCADE " +
						");";
		bd.execSQL(tabela);
	}
}
