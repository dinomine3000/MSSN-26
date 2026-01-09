package ca;

import processing.core.PApplet;
import hello.SubPlot;

public class MajorityCa extends CellularAutomata{
    public MajorityCa(PApplet p, SubPlot plt, int nrows, int ncols, int numberOfStates, int radius){
        super(p, plt, nrows, ncols, numberOfStates, radius);
    }

    @Override
    protected void createCells(){
        for(int i = 0; i < nrows; i++){
            for(int j = 0; j < ncols; j++){
                cells[i][j] = new MajorityCell(this, i, j);
            }
        }
        setMooreNeighbours();
    }

    public boolean majorityRule(){
        for(int i = 0; i < nrows; i++){
            for(int j = 0; j < ncols; j++){
                ((MajorityCell) cells[i][j]).computeHistogram();
            }
        }
        boolean anyChanged = false;
        for(int i = 0; i < nrows; i++){
            for(int j = 0; j < ncols; j++){
                boolean changed = ((MajorityCell) cells[i][j]).applyMajorityRule();
                if(changed) anyChanged = true;
            }
        }
        return anyChanged;
    }
}