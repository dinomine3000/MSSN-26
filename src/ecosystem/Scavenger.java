package ecosystem;

import hello.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class Scavenger extends Animal {
    public Scavenger(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, int id) {
        super(pos, mass, radius, color, parent, plt, id);
        energy = WorldConstants.INI_SCAV_ENERGY;
        this.dna.maxSpeed *= 2; 
    }


	protected Scavenger(Animal a, boolean mutate, PApplet p, SubPlot plt) {
		super(a, mutate, p, plt);
        energy = WorldConstants.INI_SCAV_ENERGY;
	}


    @Override
    public void eat(Terrain terrain){
        Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
        if(patch.getState() == WorldConstants.PatchType.FOOD.ordinal()){
            energy += WorldConstants.SCAV_ENERGY_FROM_PLANT;
            patch.setFertile();
        }
        if(patch.getState() == WorldConstants.PatchType.SPOILED.ordinal()){
            energy += -WorldConstants.ENERGY_FROM_PLANT;
            patch.setFertile();
        }
    }

    @Override
    public Animal reproduce(boolean mutate){
        Animal child = null;
        if(energy > WorldConstants.SCAV_ENERGY_TO_REPRODUCE){
            energy -= WorldConstants.INI_SCAV_ENERGY;
            child = new Scavenger(this, mutate, p, plt);
            if(mutate) child.mutateBehaviours();
        }
        return child;
    }
}
