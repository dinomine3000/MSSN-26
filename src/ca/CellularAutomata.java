package ca;

import processing.core.PApplet;

public class CellularAutomata {
	
	protected int nrows, ncols, nStates, radiusNeigh, w, h;
	protected Cell[][] cells;
	protected boolean moore;
	protected int[] colors;
	protected PApplet p;
	protected int offsetY = 0;
	
	public CellularAutomata(PApplet p, int nrows, int ncols, int nStates, int radiusNeigh, boolean moore) {
		
		this.p = p;
		this.nrows = nrows;
		this.ncols = ncols;
		this.nStates = nStates;
		this.radiusNeigh = radiusNeigh;
		this.moore = moore;
		w = p.width/ncols;
		h = p.height/nrows;
		cells = new Cell[nrows][ncols];
		createCells();
		colors = new int[nStates];
	}
	
	protected void createCells() {
		
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				cells[i][j] = new Cell(this, i, j);
			}
		}
		if(moore) setMoreNeighbours();
		else setNeighbours4();
	}
	
	private void setNeighbours4() {
		// TODO Auto-generated method stub
		
	}
	
	public Cell getCellGrid(int row, int col) {
		return cells[row][col];
	}
	
    public void setRandomStates() {
        for(int i = 0; i < nrows; i++) {
            for(int j = 0; i < ncols; j++) {
                cells[i][j].setState((int) p.random(nStates));
            }
        }
    }
	
	public void setMoreNeighbours() {
		for(int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				Cell[] neigh = new Cell[(int) Math.pow(2 * radiusNeigh + 1, 2)];
				int n = 0;
				for(int ii = -radiusNeigh; ii <= radiusNeigh; ii++) { 
					for(int jj = -radiusNeigh; jj <= radiusNeigh; jj++) {
						int row = (i + ii + nrows) % nrows;
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighbours(neigh); 
			}
		}
    }
	
	public void reset() {
        for(int i = 0; i < nrows; i++) {
            for(int j = 0; j < ncols; j++) {
                cells[i][j].setState(0);
            }
        }
	}
	
	public int neighbourStateColors(int state, int Neighbours){

		switch (state){
			case 0:
				return p.color(0);
			case 1:
				switch (Neighbours){
					case 1:
						return p.color(160,32,240); //roxo
					case 2:
						return p.color(0,0,255); //azul
					case 3:
						return p.color(0,255,255); //ciano
					case 4:
						return p.color(0,255,0); //verde
					case 5:
						return p.color(255,255,0); //amarelo
					case 6:
						return p.color(255,165,0); //laranja
					case 7:
						return p.color(255,0,0); //vermelho
					case 8:
						return p.color(255);
				}
		}
		return p.color(0);
	}

    public void setStateColors(int[] colors) {
        this.colors = colors;
    }

    public int[] getStateColors() {
        return colors;
    }

    public int getCelulaWidth() {
        return w;
    }

    public int getCelulaHeight() {
        return h;
    }

    public int getNumberOfStates() {
        return nStates;
    }

    public Cell getCell(int x, int y) {
        int row = y/h;
        int col = x/h;
        if(row >= nrows) row = nrows - 1;
        if(col >= ncols) col = ncols - 1;

        return cells[row][col];
    }

    public void setRandomBinaryStates(float prob) {
    	for(int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				boolean b = p.random(1f) < prob;
				int s = b ? 1 : 0;
				cells[i][j].setState(s);
			}
		}
    }
    
    public void setOffsetY(int offset) {
        this.offsetY = offset;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void display() {
        for(int i = 0; i < nrows; i++) {
            for(int j = 0; j < ncols; j++) {
                cells[i][j].display(p);
            }
        }
    }
    
}
