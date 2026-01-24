package ecosystem;

import ca.Cell;
import ca.MajorityCell;

public class Patch extends MajorityCell {
    private long eatenTime;
    private final int timeToGrow;
    private final int timeToSpoil;

    public Patch(Terrain terrain, int row, int col, int timeToGrow, int timeToSpoil){
        super(terrain, row, col);
        this.timeToGrow = timeToGrow;
        eatenTime = System.currentTimeMillis();
        this.timeToSpoil = timeToSpoil;
    }

    public void setFertile(){
    	if(state == WorldConstants.PatchType.FOOD.ordinal()) {
    		if(Math.random() < WorldConstants.SPOIL_CHANCE)
    			state = WorldConstants.PatchType.SPOILED.ordinal();
    		else
    			state = WorldConstants.PatchType.FERTILE.ordinal();
    	} else if(state == WorldConstants.PatchType.SPOILED.ordinal()) {
            state = WorldConstants.PatchType.FERTILE.ordinal();
            eatenTime = System.currentTimeMillis();	
    	}
    }

    public void regenerate(Terrain terrain){
        if(state == WorldConstants.PatchType.FERTILE.ordinal()
                    && System.currentTimeMillis() > (eatenTime + timeToGrow)){
    		state = WorldConstants.PatchType.FOOD.ordinal();
        }

    }
    
    public void spoil(Terrain terrain) {    	
    }

}
