package br.com.busaojp.utils;

import android.net.Uri;
import android.provider.BaseColumns;

public interface ProviderConstantes extends BaseColumns {
	final String TABLE_NAME = "evento";
	final String AUTHORITY = "br.com.busao.utils";
	final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
	final String TEMPERATURA = "temperatura";
}
