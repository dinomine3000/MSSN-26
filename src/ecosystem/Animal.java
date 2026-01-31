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
    protected final PApplet p;
    protected final SubPlot plt;
    protected int id;

    protected Animal(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt, int id){
        super(pos, mass, radius, color, p, plt);
        this.p = p;
        this.plt = plt;
        this.id = id;
    }
    

    protected Animal(Animal a, boolean mutate, PApplet p, SubPlot plt){
        this(a.pos, a.mass, a.radius, a.color, p, plt, a.id);
        for(Behaviour b: a.behaviours){
            this.addBehaviour(b);
        }
        if(a.eye != null){
            eye = new Eye(this, a.eye);
        }
        dna = new DNA(a.dna, mutate);
    }

    @Override
    public void energy_consumption(float dt, Terrain terrain) {
        energy -= dt; //basic metabolism
        energy -= mass*Math.pow(vel.mag(), 2)*dt;
        Patch patch = (Patch)terrain.world2Cell(pos.x,pos.y);
        if(patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()){
            energy -= 50*dt;
        }
    }

    @Override
    public boolean die(){
        return (energy < 0);
    }
    
    public void setImage(PImage img) {
    	this.img = img;
    }

    public Animal withImage(PImage img) {
    	this.img = img;
    	return this;
    }

    private static final float SCALE = 0.2f;
    @Override
	public void display(PApplet p, SubPlot plt) {
    	if(this.img == null) {
    		super.display(p, plt);
    		return;
    	}
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		//coordenadas da imagem: x,y  e  largura e altura de cada imagem: w,h;
		
		p.image(img, pp[0]-img.width*SCALE*radius/2, pp[1]-img.height*SCALE*radius/2, 
				img.width*SCALE*radius, img.height*SCALE*radius);
    }
}