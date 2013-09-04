package br.com.busaojp.utils;

import static br.com.busaojp.persistencia.BDNomes.TABELA_ONIBUS;
import static br.com.busaojp.utils.ProviderConstantes.AUTHORITY;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import br.com.busaojp.persistencia.BDBusaoJP;

public class BusaoProvider extends ContentProvider {
	
	public static final int ONIBUS = 1;
	private static UriMatcher onibusURI;
	private static SQLiteDatabase bd;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return bd.delete(TABELA_ONIBUS, selection, selectionArgs);
		
	}

	@Override
	public String getType(Uri uri) {
		return "br.com.busaojp.onibus/onibus";
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		bd.insert(TABELA_ONIBUS, null, values);
		return null;
	}

	@Override
	public boolean onCreate() {
		onibusURI = new UriMatcher(UriMatcher.NO_MATCH);
		onibusURI.addURI(AUTHORITY, "onibus", ONIBUS);
		bd = new BDBusaoJP(getContext()).getWritableDatabase();
		return true;
		
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = bd.query(TABELA_ONIBUS, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return bd.update(TABELA_ONIBUS, values, selection, selectionArgs);
	}
	
}
