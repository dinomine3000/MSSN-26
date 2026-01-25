package ecosystem;

import ca.Cell;
import ca.MajorityCell;

public class Patch extends MajorityCell {
    private long eatenTime;
    private final long timeToGrow;

    public Patch(Terrain terrain, int row, int col, long timeToGrow){
        super(terrain, row, col);
        this.timeToGrow = timeToGrow;
        eatenTime = System.currentTimeMillis();
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
    	long timestampCheck = eatenTime + timeToGrow + WorldConstants.GROW_TIME_MODIFIERS[terrain.season];
        if(state == WorldConstants.PatchType.FERTILE.ordinal()
                    && System.currentTimeMillis() > timestampCheck){
    		state = WorldConstants.PatchType.FOOD.ordinal();
        }

    }

}
