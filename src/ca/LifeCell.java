package ca;

public class LifeCell extends Cell{

	private int nAlives;
	
	public LifeCell(CellularAutomata ca, int row, int col) {
		super(ca, row, col);
	}
	
	public void flipState() {
		if(state == 0) state = 1;
		else state = 0;
	}
	
	public void countAlives() {
		nAlives = 0;
		for(Cell c : neighbours) nAlives += c.state;
		nAlives -= this.state;
	}
	
	public void applyRule() {
		if(state == 1 && (nAlives < 2 || nAlives > 3)) state = 0;
		if(state == 0 && nAlives == 3) state = 1;
	}
}
