package ecosystem;

import java.util.ArrayList;
import java.util.List;

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

        for(int i = 0; i < nrows; i++){
            for(int j = 0; j < ncols; j++){
                int timeToGrow = (int)(minRT + (maxRT - minRT) * Math.random());
                cells[i][j] = new Patch(this, i, j, timeToGrow);
            }
        }
        setMooreNeighbours();
    }


    public void regenerate(){
        for(int i = 0; i < nrows; i++){
            for (int j = 0; j < ncols; j++){
                ((Patch)cells[i][j]).regenerate();
            }
        }
    }
}
