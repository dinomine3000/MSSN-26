package ecosystem;

import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import hello.SubPlot;

import java.util.List;

public class Prey extends Animal{

    private final PApplet p;
    private final SubPlot plt;

    public Prey(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt) {
        super(pos, mass, radius, color, parent, plt);
        this.p = parent;
        this.plt = plt;
        energy = WorldConstants.INI_PREY_ENERGY;
    }

    public Prey(Prey prey, boolean mutate, PApplet p, SubPlot plt){
        super(prey, mutate, p, plt);
        this.p = p;
        this.plt = plt;
        energy = WorldConstants.INI_PREY_ENERGY;
    }

    @Override
    public void eat(Terrain terrain){
        Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
        if(patch.getState() == WorldConstants.PatchType.FOOD.ordinal()){
            energy += WorldConstants.ENERGY_FROM_PLANT;
            patch.setFertile();
        }
    }

    @Override
    public Animal reproduce(boolean mutate){
        Animal child = null;
        if(energy > WorldConstants.PREY_ENERGY_TO_REPRODUCE){
            energy -= WorldConstants.INI_PREY_ENERGY;
            child = new Prey(this, mutate, p, plt);
            if(mutate) child.mutateBehaviours();
        }
        return child;
    }
}