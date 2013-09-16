package busao.jp.utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import busaojp.onibus.Onibus;
import busaojp.onibus.Rota;


public class LeitorItinerario {

	private static String ARQUIVO1 = "C:\\Users\\Rubens\\Documents\\GitHub\\ProjetoLP3\\ServidorBusaoJP\\resources\\itinerarios_parte1.txt";
	//private static String ARQUIVO1 = "C:\\Users\\JSN-BIA\\Videos\\bia\\Projects\\Busao_JP\\ProjetoLP3\\ServidorBusaoJP\\resources\\itinerarios_parte1.txt";
	private static String MARCADOR = "<linha>";
	
	public static ArrayList<Onibus> pegarItinerarios() {
		ArrayList<Onibus> lista = new ArrayList<Onibus>();
		try {
			FileReader arquivo = new FileReader(ARQUIVO1);
			BufferedReader rd = new BufferedReader(arquivo);
			Rota rota = null;
			ArrayList<String> itinerarioIda = null;
			ArrayList<String> itinerarioVolta = null;
			Onibus onibus = null;
			boolean ida = false;
			boolean volta = false;
			while (rd.ready()) {
				String s = rd.readLine().trim().replace(";", "");
				if (s.equals(MARCADOR)) {
					if (onibus != null) {
						rota = new Rota();
						rota.setIda(itinerarioIda);
						rota.setVolta(itinerarioVolta);
						onibus.setRota(rota);
						lista.add(onibus);
					}
					onibus = new Onibus();
					itinerarioIda = new ArrayList<String>();
					itinerarioVolta = new ArrayList<String>();
					String linha = rd.readLine().trim().replace(";", "");
					String nome = rd.readLine().trim().replace(";", "");
					onibus.setLinha(linha);
					onibus.setNome(nome);
					ida = volta = false;
										
				} else if (s.equals("IDA:")) {
					ida = true;
					volta = false;
				} else if (s.equals("VOLTA:")) {
					ida = false;
					volta = true;
				} else {					
					if (ida && !volta) {
						itinerarioIda.add(s);
					} else if (!ida && volta) {
						itinerarioVolta.add(s);
					}					
				}
				
			}//while	
			if (onibus != null) {
				rota.setIda(itinerarioIda);
				rota.setVolta(itinerarioVolta);
				onibus.setRota(rota);
				lista.add(onibus);
			}
			rd.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return lista;
	}
}
