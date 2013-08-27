package busaojp.servidor;

import busaojp.onibus.OnibusDAO;
import busaojp.onibus.OnibusDAOListVersion;

public class OnibusDAOFactory {
	private static OnibusDAO dao;
	
	public static OnibusDAO getFactory() {
		if (dao == null) {
			dao = new OnibusDAOListVersion();
		}
		return dao;
	}
}
