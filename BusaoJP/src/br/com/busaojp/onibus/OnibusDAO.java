package br.com.busaojp.onibus;

import java.util.ArrayList;

public interface OnibusDAO {
	
	ArrayList<Onibus> lista();
	Onibus getOnibus(String linha);	
	ArrayList<Onibus> buscaPorLinha(String linha);
	ArrayList<Onibus> buscaPorLogradouro(String logradouro);
}
