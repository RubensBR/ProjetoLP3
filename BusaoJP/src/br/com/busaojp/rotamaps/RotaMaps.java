package br.com.busaojp.rotamaps;

import java.io.Serializable;

public class RotaMaps implements Serializable {

	private static final long serialVersionUID = -4194474482544587037L;
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
