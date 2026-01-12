package ecosystem;

import boids.behaviours.Behaviour;
import boids.Boid;
import boids.DNA;
import boids.Eye;
import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import hello.SubPlot;

import java.util.List;

public abstract class Animal extends Boid implements IAnimal{
    protected float energy;
    protected PImage img;

    protected Animal(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt){
        super(pos, mass, radius, color, p, plt);
        this.img = img;
    }
    

    protected Animal(Animal a, PApplet p, SubPlot plt){
        this(a.pos, a.mass, a.radius, a.color, p, plt);
        for(Behaviour b: a.behaviours){
            this.addBehaviour(b);
        }
        if(a.eye != null){
            eye = new Eye(this, a.eye);
        }
        dna = new DNA(a.dna);
    }


    @Override
    public void energy_consumption(float dt, Terrain terrain) {
        energy -= dt; //basic metabolism
        Patch patch = (Patch)terrain.world2Cell(pos.x,pos.y);
        if(patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()){
            energy -= 50*dt;
        }
    }

    @Override
    public boolean die(){
        return (energy < 0);
    }
}