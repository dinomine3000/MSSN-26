package ecosystem;

import java.util.ArrayList;
import java.util.List;

import ca.CellBodyProxy;
import ca.MajorityCa;
import hello.SubPlot;
import physics.Body;
import processing.core.PApplet;

public class Terrain extends MajorityCa {

    public Terrain(PApplet p, SubPlot plt){
        super(p, plt, WorldConstants.NROWS, WorldConstants.NCOLS, WorldConstants.NSTATES, 1);
    }

    protected void createCells(){
        int minRT = (int)(WorldConstants.REGENERATION_TIME[0]*1000);
        int maxRT = (int)(WorldConstants.REGENERATION_TIME[1]*1000);

        int minST = (int)(WorldConstants.SPOILAGE_TIME[0]*1000);
        int maxST = (int)(WorldConstants.SPOILAGE_TIME[1]*1000);

        for(int i = 0; i < nrows; i++){
            for(int j = 0; j < ncols; j++){
                int timeToGrow = (int)(minRT + (maxRT - minRT) * Math.random());
                int timeToSpoil = (int)(minST + (maxST - minST) * Math.random());
                cells[i][j] = new Patch(this, i, j, timeToGrow, timeToSpoil);
            }
        }
        setMooreNeighbours();
    }


    public void regenerate(){
        for(int i = 0; i < nrows; i++){
            for (int j = 0; j < ncols; j++){
                ((Patch)cells[i][j]).regenerate(this);
            }
        }
    }
    
    public void spoil() {
        for(int i = 0; i < nrows; i++){
            for (int j = 0; j < ncols; j++){
                ((Patch)cells[i][j]).spoil(this);
            }
        }
    }
    
    public List<CellBodyProxy> getCells(int patchType){
    	List<CellBodyProxy> bodies = new ArrayList<CellBodyProxy>();
    	for(int i = 0; i < nrows; i++) {
    		for(int j = 0; j < ncols; j++) {
    			if(cells[i][j].getState() == patchType) {
    				CellBodyProxy b = new CellBodyProxy(this.getCenterCell(i, j), patchType);
    				bodies.add(b);
    			}
    		}
    	}
    	return bodies;
    }
}
