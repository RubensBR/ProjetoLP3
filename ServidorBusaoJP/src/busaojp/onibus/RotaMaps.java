package busaojp.onibus;

import busao.jp.utils.RotaMapeada;

public class RotaMaps {

	private RotaMapeada ida;
	private RotaMapeada volta;
	
	public RotaMaps(RotaMapeada ida, RotaMapeada volta) {
		this.ida = ida;
		this.volta = volta;
	}

	public RotaMapeada getIda() {
		return ida;
	}

	public void setIda(RotaMapeada ida) {
		this.ida = ida;
	}

	public RotaMapeada getVolta() {
		return volta;
	}

	public void setVolta(RotaMapeada volta) {
		this.volta = volta;
	}	
}
