package br.com.busaojp.onibus;

import static br.com.busaojp.persistencia.BDNomes.*;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.busaojp.persistencia.BDBusaoJP;
import br.com.busaojp.rotamaps.Marcador;
import br.com.busaojp.rotamaps.Posicao;
import br.com.busaojp.rotamaps.RotaMapeada;
import br.com.busaojp.rotamaps.RotaMaps;

public class OnibusDAOSQL implements OnibusDAO {

	private BDBusaoJP bd;
	
	public OnibusDAOSQL(Context context) {
		bd = new BDBusaoJP(context);
	}
	
	@Override
	public ArrayList<Onibus> lista() {
		ArrayList<Onibus> res = new ArrayList<Onibus>();
		SQLiteDatabase sql = bd.getReadableDatabase();		
		String consulta = "SELECT linha, nome FROM onibus";
		
		Cursor cursor = sql.rawQuery(consulta, null);
		
		while (cursor.moveToNext()) {
			String linha = cursor.getString(cursor.getColumnIndex(LINHA));
			String nome = cursor.getString(cursor.getColumnIndex(NOME));
			res.add(new Onibus(linha, nome));
		}
		return res;
	}

	@Override
	public Onibus getOnibus(String linha) {
		Onibus onibus = null;
		SQLiteDatabase sql = bd.getReadableDatabase();		
		String consulta = "SELECT linha, nome FROM onibus WHERE linha = '@linha'".replace("@linha", linha);
		
		Cursor cursor = sql.rawQuery(consulta, null);
		if (cursor.moveToNext()) {
			String l = cursor.getString(cursor.getColumnIndex(LINHA));
			String nome = cursor.getString(cursor.getColumnIndex(NOME));
			onibus = new Onibus(l, nome);
		}
		
		return onibus;
	}
	
	public ArrayList<String> getHorarios(String linha) {
		ArrayList<String> res = new ArrayList<String>();
		SQLiteDatabase sql = bd.getReadableDatabase();		
		String consulta = "SELECT horario_id, hora FROM horarios WHERE linha = '@linha' ORDER BY horario_id;"
				.replace("@linha", linha);
		
		Cursor cursor = sql.rawQuery(consulta, null);
		
		while (cursor.moveToNext()) {
			String hora = cursor.getString(cursor.getColumnIndex(HORA));			
			res.add(hora);
		}
		return res;
	}
	
	public Rota getRota(String linha) {
		ArrayList<String> ida = new ArrayList<String>();
		ArrayList<String> volta = new ArrayList<String>();
		
		SQLiteDatabase sql = bd.getReadableDatabase();		
		String consultaIda = "SELECT ordem, logradouro FROM itinerario WHERE linha = '@linha' AND " +
				"sentido = '@sentido' ORDER BY ordem";
		consultaIda = consultaIda.replace("@linha", linha).replace("@sentido", IDA);		
		
		Cursor cursor = sql.rawQuery(consultaIda, null);
		
		while (cursor.moveToNext()) {
			String logradouro = cursor.getString(cursor.getColumnIndex(LOGRADOURO));			
			ida.add(logradouro);			
		}
		
		String consultaVolta = "SELECT ordem, logradouro FROM itinerario WHERE linha = '@linha' AND " +
				"sentido = '@sentido' ORDER BY ordem";
				consultaVolta = consultaVolta.replace("@linha", linha).replace("@sentido", VOLTA);
		
		cursor = sql.rawQuery(consultaVolta, null);
		
		while (cursor.moveToNext()) {
			String logradouro = cursor.getString(cursor.getColumnIndex(LOGRADOURO));			
			volta.add(logradouro);		
		}		
		return new Rota(ida, volta);		
	}
	
	public ArrayList<Marcador> getMarcadores(String linha, String sentido) {
		ArrayList<Marcador> lista = new ArrayList<Marcador>();
		SQLiteDatabase sql = bd.getReadableDatabase();		
		String consulta = "SELECT latitude, longitude, descricao FROM marcadores WHERE linha = '@linha' AND sentido = '@sentido';"
				.replace("@linha", linha).replace("@sentido", sentido);
		
		Cursor cursor = sql.rawQuery(consulta, null);
		
		while (cursor.moveToNext()) {			
			String nome = cursor.getString(cursor.getColumnIndex(DESCRICAO));
			double latitude = cursor.getDouble(cursor.getColumnIndex(LATITUDE));
			double longitude = cursor.getDouble(cursor.getColumnIndex(LONGITUDE));
			lista.add(new Marcador(nome, new Posicao(latitude, longitude)));			
		}
		return lista;
		
	}
	
	public ArrayList<Posicao> getCoordenadasDaRota(String linha, String sentido) {
		ArrayList<Posicao> lista = new ArrayList<Posicao>();
		SQLiteDatabase sql = bd.getReadableDatabase();		
		String consulta = "SELECT ordem, latitude, longitude FROM coordenadas WHERE linha = '@linha' AND " +
				"sentido = '@sentido' ORDER BY ordem";
		consulta = consulta.replace("@linha", linha).replace("@sentido", sentido);
				
		Cursor cursor = sql.rawQuery(consulta, null);
		
		while (cursor.moveToNext()) {			
			double latitude = cursor.getDouble(cursor.getColumnIndex(LATITUDE));
			double longitude = cursor.getDouble(cursor.getColumnIndex(LONGITUDE));
			lista.add(new Posicao(latitude, longitude));			
		}
		return lista;
	}
	
	public boolean salvarOnibus(Onibus onibus) {
		SQLiteDatabase sql = bd.getWritableDatabase();
		Onibus teste = getOnibus(onibus.getLinha());
		if (teste != null)
			return false;
		ContentValues valores = new ContentValues();
		valores.put(NOME, onibus.getNome());
		valores.put(LINHA, onibus.getLinha());
		sql.insertOrThrow(TABELA_ONIBUS, null, valores);
		
		salvarItinerarios(onibus);
		salvarHorarios(onibus);
		salvarMarcadores(onibus);
		salvarCoordenadas(onibus);
		
		return true;		
	}
	
	private void salvarItinerarios(Onibus onibus) {
		SQLiteDatabase sql = bd.getWritableDatabase();
		ContentValues valores;
		ArrayList<String> ida = onibus.getRota().getIda();
		for (int i = 0; i < ida.size(); ++i) {
			valores = new ContentValues();
			valores.put(LINHA, onibus.getLinha());
			valores.put(ORDEM, i + 1);
			valores.put(LOGRADOURO, ida.get(i));
			valores.put(SENTIDO, IDA);
			sql.insertOrThrow(TABELA_ITINERARIO, null, valores);
		}
		
		ArrayList<String> volta = onibus.getRota().getVolta();
		for (int i = 0; i < volta.size(); ++i) {
			valores = new ContentValues();
			valores.put(LINHA, onibus.getLinha());
			valores.put(ORDEM, i + 1);
			valores.put(LOGRADOURO, ida.get(i));
			valores.put(SENTIDO, VOLTA);
			sql.insertOrThrow(TABELA_ITINERARIO, null, valores);
		}
	}
	
	private void salvarHorarios(Onibus onibus) {
		SQLiteDatabase sql = bd.getWritableDatabase();
		ContentValues valores;
		ArrayList<String> horarios = onibus.getHorarios();
		for (int i = 0; i < horarios.size(); ++i) {
			valores = new ContentValues();
			valores.put(LINHA, onibus.getLinha());
			valores.put(HORA, horarios.get(i));
			sql.insertOrThrow(TABELA_HORARIOS, null, valores);
		}		
	}
	
	private void salvarMarcadores(Onibus onibus) {
		SQLiteDatabase sql = bd.getWritableDatabase();
		ContentValues valores;
		ArrayList<Marcador> marcadores = onibus.getRotaMaps().getIda().getMarcadores();
		for (int i = 0; i < marcadores.size(); ++i) {
			valores = new ContentValues();
			valores.put(LINHA, onibus.getLinha());
			valores.put(LATITUDE, marcadores.get(i).getPosicao().getLatitude());
			valores.put(LONGITUDE, marcadores.get(i).getPosicao().getLongitude());
			valores.put(DESCRICAO, marcadores.get(i).getNome());
			valores.put(SENTIDO, IDA);
			sql.insertOrThrow(TABELA_MARCADORES, null, valores);
		}
		
		marcadores = onibus.getRotaMaps().getVolta().getMarcadores();
		for (int i = 0; i < marcadores.size(); ++i) {
			valores = new ContentValues();
			valores.put(LINHA, onibus.getLinha());
			valores.put(LATITUDE, marcadores.get(i).getPosicao().getLatitude());
			valores.put(LONGITUDE, marcadores.get(i).getPosicao().getLongitude());
			valores.put(DESCRICAO, marcadores.get(i).getNome());
			valores.put(SENTIDO, VOLTA);
			sql.insertOrThrow(TABELA_MARCADORES, null, valores);
		}
	}
	
	private void salvarCoordenadas(Onibus onibus) {
		SQLiteDatabase sql = bd.getWritableDatabase();
		ContentValues valores;
		ArrayList<Posicao> posicoes = onibus.getRotaMaps().getIda().getRota();
		
		for (int i = 0; i < posicoes.size(); ++i) {
			valores = new ContentValues();
			valores.put(LINHA, onibus.getLinha());
			valores.put(ORDEM, i + 1);
			valores.put(LATITUDE, posicoes.get(i).getLatitude());
			valores.put(LONGITUDE, posicoes.get(i).getLongitude());
			valores.put(SENTIDO, IDA);
			sql.insertOrThrow(TABELA_COORDENADAS, null, valores);
		}
		
		posicoes = onibus.getRotaMaps().getVolta().getRota();
		
		for (int i = 0; i < posicoes.size(); ++i) {
			valores = new ContentValues();
			valores.put(LINHA, onibus.getLinha());
			valores.put(ORDEM, i + 1);
			valores.put(LATITUDE, posicoes.get(i).getLatitude());
			valores.put(LONGITUDE, posicoes.get(i).getLongitude());
			valores.put(SENTIDO, VOLTA);
			sql.insertOrThrow(TABELA_COORDENADAS, null, valores);
		}
	}
	
	public void removerFavoritos(String linha) {
		SQLiteDatabase sql = bd.getWritableDatabase();
		String deleteOnibus = "DELETE FROM onibus WHERE linha = '@linha'".replace("@linha", linha);
		
		String deleteHorarios = "DELETE FROM @tabela WHERE linha = '@linha'"
				.replace("@tabela", TABELA_HORARIOS).replace("@linha", linha);
		
		String deleteMarcadores = "DELETE FROM @tabela WHERE linha = '@linha'"
				.replace("@tabela", TABELA_MARCADORES).replace("@linha", linha);
		
		String deleteItinerarios = "DELETE FROM @tabela WHERE linha = '@linha'"
				.replace("@tabela", TABELA_ITINERARIO).replace("@linha", linha);
		
		String deleteCoordenadas = "DELETE FROM @tabela WHERE linha = '@linha'"
				.replace("@tabela", TABELA_COORDENADAS).replace("@linha", linha);
		
		sql.execSQL(deleteOnibus);
		sql.execSQL(deleteHorarios);
		sql.execSQL(deleteMarcadores);
		sql.execSQL(deleteItinerarios);
		sql.execSQL(deleteCoordenadas);		 
	}		

	@Override
	public ArrayList<Onibus> buscaPorLinha(String linhaNome) {
		SQLiteDatabase sql = bd.getWritableDatabase();
		String consulta = "SELECT linha, nome FROM onibus WHERE linha = '@linha' OR nome = '@nome'"
				.replace("@linha", linhaNome).replace("@nome", linhaNome);
		
		ArrayList<Onibus> res = new ArrayList<Onibus>();
		Cursor cursor = sql.rawQuery(consulta, null);
		
		while (cursor.moveToNext()) {
			String linha = cursor.getString(cursor.getColumnIndex(LINHA));
			String nome = cursor.getString(cursor.getColumnIndex(NOME));
			res.add(new Onibus(linha, nome));
		}
		return res;
	}

	@Override
	public ArrayList<Onibus> buscaPorLogradouro(String logradouro) {
		SQLiteDatabase sql = bd.getWritableDatabase();
		String consulta = "SELECT DISTINCT itinerario.linha, onibus.nome FROM onibus, itinerario " +
				"WHERE itinerario.logradouro = '@logradouro' AND itinerario.linha = onibus.linha";
		consulta = consulta.replace("@logradouro", logradouro);
		
		ArrayList<Onibus> res = new ArrayList<Onibus>();
		Cursor cursor = sql.rawQuery(consulta, null);
		
		while (cursor.moveToNext()) {
			String linha = cursor.getString(cursor.getColumnIndex(LINHA));
			String nome = cursor.getString(cursor.getColumnIndex(NOME));
			res.add(new Onibus(linha, nome));
		}
		return res;
	}

	@Override
	public RotaMaps buscaRotaMaps(String linha) {
		ArrayList<Posicao> posicoesIda = getCoordenadasDaRota(linha, IDA);
		ArrayList<Marcador> marcadoresIda = getMarcadores(linha, IDA);
		ArrayList<Posicao> posicoesVolta = getCoordenadasDaRota(linha, VOLTA);
		ArrayList<Marcador> marcadoresVolta = getMarcadores(linha, VOLTA);
		
		return new RotaMaps(new RotaMapeada(posicoesIda, marcadoresIda),
				new RotaMapeada(posicoesVolta, marcadoresVolta));
	}

}
