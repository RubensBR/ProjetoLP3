package busaojp.onibus;

import java.util.ArrayList;

public interface OnibusDAO {

	ArrayList<Onibus> lista();
	Onibus getOnibus(String linha);
	
}
