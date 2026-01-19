package ca;

import physics.Body;
import processing.core.PVector;

public class CellBodyProxy extends Body {

	private int state;
	public CellBodyProxy(PVector centerCell, int state) {
		super(centerCell);
		this.state = state;
	}
	
	public int getState() {
		return state;
	}

}
