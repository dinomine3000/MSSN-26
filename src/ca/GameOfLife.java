package ca;

import processing.core.PApplet;

public class GameOfLife extends CellularAutomata{

	public GameOfLife(PApplet p, int nrows, int ncols, int offsetY) {
		super(p, nrows, ncols, 2, 1, true);
	}
	
	public void initRandom(float prob) {
		setRandomBinaryStates(prob);
	}
	
	@Override
	protected void createCells() {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				cells[i][j] = new LifeCell(this, i, j);
			}
		}
		setMoreNeighbours();
	}


	
	public void run() {
		LifeCell c;
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				c = (LifeCell) cells[i][j];
				c.countAlives();
			}
		}
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				c = (LifeCell) cells[i][j];
				c.applyRule();
			}
		}
	}
}
