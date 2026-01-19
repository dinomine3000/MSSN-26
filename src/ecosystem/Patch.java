package ecosystem;

import ca.Cell;
import ca.MajorityCell;

public class Patch extends MajorityCell {
    private long eatenTime;
    private final int timeToGrow;
    private long grownTime;
    private final int timeToSpoil;

    public Patch(Terrain terrain, int row, int col, int timeToGrow, int timeToSpoil){
        super(terrain, row, col);
        this.timeToGrow = timeToGrow;
        eatenTime = System.currentTimeMillis();
        this.timeToSpoil = timeToSpoil;
        grownTime = System.currentTimeMillis();
    }

    public void setFertile(){
        state = WorldConstants.PatchType.FERTILE.ordinal();
        eatenTime = System.currentTimeMillis();
    }

    public void regenerate(Terrain terrain){
        if(state == WorldConstants.PatchType.FERTILE.ordinal()
                    && System.currentTimeMillis() > (eatenTime + timeToGrow)){
        	int foodCount = 0;
        	for(Cell cell: getNeighbours()) {
        		if(cell instanceof Patch pCell) {
        			if(pCell.state == WorldConstants.PatchType.FOOD.ordinal()) foodCount += 1;
        		}
        	}
        	if((1 + foodCount)/9f > Math.random()) {
        		state = WorldConstants.PatchType.FOOD.ordinal();
        		grownTime = System.currentTimeMillis();
        	}
        	else {
        		eatenTime = System.currentTimeMillis();
        	}
        }

    }
    
    public void spoil(Terrain terrain) {
    	if(state != WorldConstants.PatchType.FOOD.ordinal()) return;
        if(System.currentTimeMillis() > (grownTime + timeToSpoil)){
    		state = WorldConstants.PatchType.SPOILED.ordinal();
        }
    	
    }

}
