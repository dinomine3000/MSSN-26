package ecosystem;

import physics.Body;
import processing.core.PVector;

public class CellBodyProxy extends Body {
	private Patch cell;
	
	public CellBodyProxy(Patch cell) {
		super(cell.getCenter());
		this.cell = cell;
	}
	
	public Patch getPatch() {return this.cell;}
	

}
