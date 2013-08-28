package br.com.busaojp.onibus;

import java.util.ArrayList;

import br.com.busaojp.rotamaps.RotaMaps;

public interface OnibusDAO {
	
	ArrayList<Onibus> lista();
	Onibus getOnibus(String linha);	
	ArrayList<Onibus> buscaPorLinha(String linha);
	ArrayList<Onibus> buscaPorLogradouro(String logradouro);
	RotaMaps buscaRotaMaps(String linha);
}
