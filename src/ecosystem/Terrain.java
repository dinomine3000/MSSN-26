package ecosystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.MajorityCa;
import hello.SubPlot;
import physics.Body;
import processing.core.PApplet;

public class Terrain extends MajorityCa {
	protected int season = WorldConstants.Season.FALL.ordinal();
	private long lastChangeStamp = 0;

    public Terrain(PApplet p, SubPlot plt){
        super(p, plt, WorldConstants.NROWS, WorldConstants.NCOLS, WorldConstants.NSTATES, 1);
        lastChangeStamp = System.currentTimeMillis();
    }

    protected void createCells(){
        long minRT = (long)(WorldConstants.REGENERATION_TIME[0]*1000);
        long maxRT = (long)(WorldConstants.REGENERATION_TIME[1]*1000);

        for(int i = 0; i < nrows; i++){
            for(int j = 0; j < ncols; j++){
                long timeToGrow = (int)(minRT + (maxRT - minRT) * Math.random());
                cells[i][j] = new Patch(this, i, j, timeToGrow);
            }
        }
        setMooreNeighbours();
    }
    
    public void tickSeason() {
    	if(System.currentTimeMillis() - lastChangeStamp > WorldConstants.TIME_TO_CHANGE_SEASONS*1000) {
    		lastChangeStamp = System.currentTimeMillis();
    		season = nextSeason(season);
    	}
    }
    
    public void advanceSeasons() {
		lastChangeStamp = System.currentTimeMillis();
    	season = nextSeason(season);
    }
    
    private int nextSeason(int currentSeason) {
        if (currentSeason == WorldConstants.Season.FALL.ordinal()) {
            return WorldConstants.Season.WINTER.ordinal();
        }

        if (currentSeason == WorldConstants.Season.WINTER.ordinal()) {
            return WorldConstants.Season.FALL.ordinal();
        }

//        if (currentSeason == WorldConstants.Season.SPRING.ordinal()) {
//            return WorldConstants.Season.SUMMER.ordinal();
//        }
//
//        if (currentSeason == WorldConstants.Season.SUMMER.ordinal()) {
//            return WorldConstants.Season.FALL.ordinal();
//        }

        return WorldConstants.Season.FALL.ordinal();
    }


    public void regenerate(){
        for(int i = 0; i < nrows; i++){
            for (int j = 0; j < ncols; j++){
                ((Patch)cells[i][j]).regenerate(this);
            }
        }
    }
    
    @Override
    public void display(PApplet p) {
    	super.display(p);

        p.pushStyle();
        p.noStroke();

        float[] window = this.plt.getBoundingBox();
//        if (season == WorldConstants.Season.SUMMER.ordinal()) {
//            // yellow tint
//            p.fill(255, 255, 0, 60); // RGBA, low alpha
//            p.rect(window[0], window[1], window[2], window[3]);
//        }

        if (season == WorldConstants.Season.WINTER.ordinal()) {
            // blue tint
            p.fill(0, 120, 255, 40);
            p.rect(window[0], window[1], window[2], window[3]);
        }

        p.popStyle();
    }
    
    public List<CellBodyProxy> getCells(int patchType){
    	List<CellBodyProxy> bodies = new ArrayList<>();
    	for(int i = 0; i < nrows; i++) {
    		for(int j = 0; j < ncols; j++) {
    			if(cells[i][j].getState() == patchType) {
    				CellBodyProxy b = new CellBodyProxy((Patch) cells[i][j]);
    				bodies.add(b);
    			}
    		}
    	}
    	return bodies;
    }
}
