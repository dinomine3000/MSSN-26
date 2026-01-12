package ca;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Cell {
	
	protected CellularAutomata au;
	protected int row, col;
	protected int state;
	protected Cell[] neighbours;
	protected float w, h;
	
	public Cell(CellularAutomata au, int row, int col) {
		
		this.au = au;
		this.row = row;
		this.col = col;
		this.state = 0;
		this.neighbours = null;
		w = au.getCelulaWidth();
		h = au.getCelulaHeight();
	}
	
	public void setNeighbours(Cell[] neigh) {
		this.neighbours = neigh;
	}
	
	public Cell[] getNeighbours() {
		return neighbours;
	}
	
	public int neighboursNum() {
		int liveNeighbours = 0;

		if (neighbours != null) {
			for (Cell neighbour : neighbours) {
				if (neighbour != null && neighbour.getState() == 1) {
					liveNeighbours++;
				}
			}
		}

		return liveNeighbours;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}

	public PVector getCenter() {
		float x = (col + 0.5f)*w;
		float y = (row * 0.5f)*h;
		return new PVector(x, y);
	}
	
	public void display(PApplet p) {
		p.pushStyle();
		p.noStroke();
		p.fill(au.getStateColors()[state]);
		p.rect(au.xmin + col * w, au.ymin + row * h, w, h);
		//p.ellipseMode(PConstants.CORNER);
		p.popStyle();
	}
	
}
