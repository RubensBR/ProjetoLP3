package br.com.busaojp.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {
	
	public static String urlContentGet(String endereco) throws IOException, ClientProtocolException {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(endereco);
		ResponseHandler<String> handler = new BasicResponseHandler();
		return (client.execute(httpGet, handler));
	}
	
	public static String urlContentPost(String endereco, String... parametros) throws IOException, ClientProtocolException {
		
		HttpClient client = new DefaultHttpClient();		
		HttpPost httpPost = new HttpPost(endereco);		
		List<NameValuePair> params = new ArrayList<NameValuePair>();		
		
		for (int i = 0; i < parametros.length; i+=2) {		
			String nomeParametro = parametros[i];		
			String valorParametro = parametros[i + 1];		
			params.add(new BasicNameValuePair(nomeParametro, valorParametro));		
		}
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");		
		httpPost.setEntity(entity);		
		ResponseHandler<String> handler = new BasicResponseHandler();		
		return (client.execute(httpPost, handler));
	}
}
