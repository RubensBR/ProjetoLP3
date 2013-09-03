package br.com.busaojp.onibus;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.busaojp.R;

public class OnibusListAdapter extends BaseAdapter {

	private ArrayList<Onibus> lista;
	private LayoutInflater layoutInflater;
	private boolean favoritos = false;
	
	public OnibusListAdapter(ArrayList<Onibus> lista, Context context) {
		this.lista = lista;
		layoutInflater = LayoutInflater.from(context);
	}
	
	public OnibusListAdapter(ArrayList<Onibus> lista, Context context, boolean favoritos) {
		this(lista, context);
		this.favoritos = favoritos;
	}
	
	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (!favoritos) {
			Holder holder;
			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.onibus_listview, null);		
				holder = new Holder();
				holder.linhaNome = (TextView) convertView.findViewById(R.id.linha_nome);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Onibus onibus = lista.get(position);
			holder.linhaNome.setText(onibus.getLinha() + " - " + onibus.getNome());			
		} else {
			Holder holder;
			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.favoritos_listview, null);
				holder = new Holder();
				holder.linhaNome = (TextView) convertView.findViewById(R.id.linha_nome_favorito);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();				
			}			
			Onibus onibus = lista.get(position);
			holder.linhaNome.setText(onibus.getLinha() + " - " + onibus.getNome());
			
		}	
		return convertView;
	}
	
	private class Holder {
		TextView linhaNome;
	}
}
