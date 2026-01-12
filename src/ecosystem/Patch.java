package ecosystem;

import ca.MajorityCell;

public class Patch extends MajorityCell {
    private long eatenTime;
    private final int timeToGrow;

    public Patch(Terrain terrain, int row, int col, int timeToGrow){
        super(terrain, row, col);
        this.timeToGrow = timeToGrow;
        eatenTime = System.currentTimeMillis();
    }

    public void setNonertile(){
        if(state == WorldConstants.PatchType.FOOD.ordinal())
            state = WorldConstants.PatchType.OBSTACLE.ordinal();
    }

    public void setFertile(){
        state = WorldConstants.PatchType.FERTILE.ordinal();
        eatenTime = System.currentTimeMillis();
    }

    public void regenerate(){
        if(state == WorldConstants.PatchType.FERTILE.ordinal()
                    && System.currentTimeMillis() > (eatenTime + timeToGrow)){
            state = WorldConstants.PatchType.FOOD.ordinal();
        }

    }

}
