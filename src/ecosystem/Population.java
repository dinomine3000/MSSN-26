package ecosystem;

import java.util.ArrayList;
import java.util.List;

import boids.Eye;
import boids.behaviours.AvoidObstacle;
import boids.behaviours.ConditionalBrake;
import boids.behaviours.Wander;
import ca.CellBodyProxy;
import hello.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Population {
	private List<Animal> allAnimals;
	private double[] window;
	private boolean mutate = true;
	private PImage imgRabbit, imgRaccoon;
	
	public Population(PApplet p, SubPlot plt, Terrain terrain) {
		imgRabbit = p.loadImage("rabbit.png");
		imgRaccoon = p.loadImage("raccoon.png");
		
		window = plt.getWindow();
		allAnimals = new ArrayList<Animal>();

		List<CellBodyProxy> spoiledFood = terrain.getCells(WorldConstants.PatchType.SPOILED.ordinal());
		List<CellBodyProxy> food = terrain.getCells(WorldConstants.PatchType.FOOD.ordinal());

		for(int i = 0; i < WorldConstants.INI_PREY_POPULATION; i++) {
			PVector pos = new PVector(p.random((float)window[0], (float)window[1]),
					p.random((float)window[2], (float)window[3]));
			int color = p.color(
					WorldConstants.PREY_COLOR[0],
					WorldConstants.PREY_COLOR[1],
					WorldConstants.PREY_COLOR[2]);
			Animal a = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, color, p, plt, WorldConstants.PREY_ID);
			a.addBehaviour(new Wander(1));
			if(WorldConstants.DO_HIBERNATION) {
				a.addBehaviour(new ConditionalBrake(1));
				Eye<CellBodyProxy> eye = new Eye<>(a, food);
				a.setEye(eye);
			}
			a.setImage(imgRabbit);
			//a.addBehaviour(new AvoidObstacle(0));
			//a.addBehaviour(new SmellDetection(Math.abs(a.dna.smellStrength)));
			allAnimals.add(a);
		}
		for(int i = 0; i < WorldConstants.INI_SCAV_POPULATION; i++) {
			PVector pos = new PVector(p.random((float)window[0], (float)window[1]),
					p.random((float)window[2], (float)window[3]));
			int color = p.color(
					WorldConstants.SCAV_COLOR[0],
					WorldConstants.SCAV_COLOR[1],
					WorldConstants.SCAV_COLOR[2]);
			Animal a = new Scavenger(pos, WorldConstants.SCAV_MASS, WorldConstants.SCAV_SIZE, color, p, plt, WorldConstants.SCAV_ID);
			a.addBehaviour(new Wander(1));
			if(WorldConstants.DO_HIBERNATION) {
				a.addBehaviour(new ConditionalBrake(1));
				Eye<CellBodyProxy> eye = new Eye<>(a, spoiledFood);
				a.setEye(eye);
			}
			//a.addBehaviour(new AvoidObstacle(0));
			//a.addBehaviour(new SmellDetection(Math.abs(a.dna.smellStrength)));
			a.setImage(imgRaccoon);
			allAnimals.add(a);
		}
	}
	
	public void update(float dt, Terrain terrain) {
		move(terrain, dt);
		eat(terrain);
		energy_consumption(dt, terrain);
		reproduce(mutate);
		die();
	}
	
	private void move(Terrain terrain, float dt) {
		for(Animal a: allAnimals) a.applyBehaviours(dt);
	}
	
	private void eat(Terrain terrain) {
		for(Animal a: allAnimals) a.eat(terrain);
	}
	
	private void energy_consumption(float dt, Terrain terrain) {
		for(Animal a: allAnimals) a.energy_consumption(dt, terrain);
	}
	
	private void reproduce(boolean mutate) {
	    for (int i = allAnimals.size() - 1; i >= 0; i--) {
	    	Animal a = allAnimals.get(i);
			Animal child = a.reproduce(mutate);
			if(child != null) allAnimals.add(child.withImage(a.img));
	    }
	}
	
	private void die() {
	    for (int i = allAnimals.size() - 1; i >= 0; i--) {
	    	Animal a = allAnimals.get(i);
			if(a.die()) allAnimals.remove(a);
		}
	}
	
	public void display(PApplet p, SubPlot plt) {
		for(Animal a: allAnimals) a.display(p, plt);
	}
	
	private List<Animal> getAnimalsOfId(int id){
		if(id < 0) return allAnimals;
		return allAnimals.stream().filter(a -> a.id == id).toList();
	}
	
	public int getNumAnimals() {return getNumAnimals(-1);}
	
	public int getNumAnimals(int id) {
		return getAnimalsOfId(id).size();
	}

	
	public float[] getMeanWeights() {
		float[] sums = new float[2];
		for(Animal a: allAnimals) {
			sums[0] += a.getBehaviours().get(0).getWeight();
			sums[1] += a.getBehaviours().get(1).getWeight();
		}
		sums[0] /= allAnimals.size();
		sums[1] /= allAnimals.size();
		return sums;
	}
	
	public float getPreyNumber() {return getNumAnimals(1);}
	public float getScavNumber() {return getNumAnimals(2);}

}
