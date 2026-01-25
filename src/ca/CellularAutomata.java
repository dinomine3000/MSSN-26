package ca;

import hello.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;
import util.CustomRandomGenerator;

public class CellularAutomata {
	
	protected int nrows, ncols, nStates, radiusNeigh;
	protected Cell[][] cells;
	protected int[] colors;
	protected int offsetY = 0;
	private float cellWidth, cellHeight;
	public float xmin, ymin;
	protected SubPlot plt;
	
	public CellularAutomata(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
		this.nrows = nrows;
		this.ncols = ncols;
		this.nStates = nStates;
		this.radiusNeigh = radiusNeigh;

		this.plt = plt;
		float[] bb = plt.getBoundingBox();
		xmin = bb[0];
		ymin = bb[1];
		cellWidth = bb[2]/ncols;
		cellHeight = bb[3]/nrows;
		
		cells = new Cell[nrows][ncols];
		colors = new int[nStates];
		setRandomStateColors(p);
		createCells();
	}
	
	public void initRandom() {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				cells[i][j].setState((int)(nStates*Math.random()));
			}
		}
	}
	
	public void initRandom(double[] pmf) {
		CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				cells[i][j].setState(crg.getRandomClass());
			}
		}
	}
	
	private void setRandomStateColors(PApplet p) {
		for(int i = 0; i < nStates; i++) {
			colors[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}

	public void setStateColors(int[] colors) {
		this.colors = colors;
	}
	
	protected void createCells() {
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				cells[i][j] = new Cell(this, i, j);
			}
		}
		setMooreNeighbours();
	}
	
	public Cell getCellGrid(int row, int col) {
		return cells[row][col];
	}
	
    public void setRandomStates() {
        for(int i = 0; i < nrows; i++) {
            for(int j = 0; j < ncols; j++) {
                cells[i][j].setState((int) (Math.random() * nStates));
            }
        }
    }
	
	public void setMooreNeighbours() {
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

    public int[] getStateColors() {
        return colors;
    }

    public float getCelulaWidth() {
        return cellWidth;
    }

    public float getCelulaHeight() {
        return cellHeight;
    }

    public int getNumberOfStates() {
        return nStates;
    }

    public PVector getCenterCell(int row, int col){
        float x = (col + 0.5f) * cellWidth;
        float y = (row + 0.5f) * cellHeight;
        double[] w = plt.getWorldCoord(x,y);
        return new PVector((float)w[0], (float)w[1]);
    }
    
    public Cell world2Cell(double x, double y) {
    	float[] xy = plt.getPixelCoord(x, y);
    	return getCell(xy[0], xy[1]);
    }

    public Cell getCell(float x, float y) {
        int row = (int)((y-ymin)/cellHeight);
        int col = (int)((x-xmin)/cellWidth);
        if(row >= nrows) row = nrows - 1;
        if(col >= ncols) col = ncols - 1;
        if(row < 0) row = 0;
        if(col < 0) col = 0;

        return cells[row][col];
    }

    public void setRandomBinaryStates(float prob) {
    	for(int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				boolean b = Math.random() < prob;
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

    public void display(PApplet p) {
        for(int i = 0; i < nrows; i++) {
            for(int j = 0; j < ncols; j++) {
                cells[i][j].display(p);
            }
        }
    }
    
}
