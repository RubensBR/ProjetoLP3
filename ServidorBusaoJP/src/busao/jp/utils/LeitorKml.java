package busao.jp.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class LeitorKml {
	
	public static final String IDA = "_ida";
	public static final String VOLTA = "_volta";

	private final String CAMINHO = "C:\\Users\\JSN-BIA\\Videos\\bia\\Projects\\Busao_JP\\ProjetoLP3\\ServidorBusaoJP\\resources";
	//private final String CAMINHO = "C:\\Desenvolvedor\\ProjetoLP3\\ServidorBusaoJP\\resources\\";
	//private final String CAMINHO = "C:\\Users\\Rubens\\Documents\\GitHub\\ProjetoLP3\\ServidorBusaoJP\\resources\\";
	
	public RotaMapeada pegarRotaMapeada(String linha, String sentido) {		
		
		ArrayList<Posicao> rota = new ArrayList<Posicao>();
		ArrayList<Marcador> marcadores = new ArrayList<Marcador>();
		try {			
			FileReader kml = new FileReader(CAMINHO + linha + sentido + ".kml");			
			BufferedReader rd = new BufferedReader(kml);
			while (rd.ready()) {			
				String row = rd.readLine();
				if (row.contains("<Placemark>")) { //inicio de nova marca��o
					rd.readLine(); //<styleUrl> 
					String aux = rd.readLine();
					String name = aux.replace("<name>", "").replace("</name>","").trim();
					
					String tipo = rd.readLine();					
					tipo = tipo.contains("<description>") ? rd.readLine() : tipo;
					
					if (tipo.contains("<LineString>")) {
						rd.readLine(); //descarta pode ser <tessellate> ou <description>
						String coord = rd.readLine();
						if (coord.contains("<coordinates>")) {
							coord = coord.replace("<coordinates>","").replace("</coordinates>", "").replace("0.0 ", "");							
							coord = coord.trim();
							System.out.println(coord);							
							Scanner sc = new Scanner(coord);
							sc.useDelimiter(",");
							sc.toString();
							System.out.println("teste: " + sc.hasNext());
							int i = 0;
							
							while (sc.hasNext()) {
								double longitude = Double.parseDouble(sc.next());							
								if (sc.hasNext()) {
									double latitude = Double.parseDouble(sc.next());
									rota.add(new Posicao(latitude, longitude));
									System.out.println("latitude, longidude: " + latitude + ", " + longitude + ", i: " + (++i));
								}
								else {
									break;
								}
							}	
							sc.close();
						}
					} else if (tipo.contains("<Point>")) {
						if (tipo.contains("<Point>")) {
							System.out.println("Ponto");
							String s = rd.readLine(); //descarta pode ser <tessellate> ou <description>						
							String coord = s.contains("<description>") ? rd.readLine() : s;
							System.out.println("Marcador: " + name);
							coord = coord.replace("<coordinates>","").replace("</coordinates>", "").replace("0.0 ", "");							
							coord = coord.trim();						
							Scanner sc = new Scanner(coord);
							sc.useDelimiter(",");
							sc.toString();							
							if (sc.hasNext()) {
								double pos_longitude = Double.parseDouble(sc.next());
								double pos_latitude = Double.parseDouble(sc.next());
								marcadores.add(new Marcador(name, new Posicao(pos_latitude, pos_longitude)));
							}			
							sc.close();
						}
					}
				}				
			}	
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return new RotaMapeada(rota, marcadores);
	}

}
