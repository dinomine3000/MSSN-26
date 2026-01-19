package ecosystem;

import java.util.ArrayList;
import java.util.List;

import boids.Eye;
import boids.behaviours.AvoidObstacle;
import boids.behaviours.SmellDetection;
import boids.behaviours.Wander;
import ca.CellBodyProxy;
import hello.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Population {
	private List<Animal> allAnimals;
	private double[] window;
	private boolean mutate = true;
	
	public Population(PApplet p, SubPlot plt, Terrain terrain) {
		window = plt.getWindow();
		allAnimals = new ArrayList<Animal>();

		List<CellBodyProxy> obstacles = terrain.getCells(WorldConstants.PatchType.OBSTACLE.ordinal());
		List<CellBodyProxy> spoiledFood = terrain.getCells(WorldConstants.PatchType.SPOILED.ordinal());
		List<CellBodyProxy> food = terrain.getCells(WorldConstants.PatchType.FOOD.ordinal());
		obstacles.addAll(spoiledFood);
		obstacles.addAll(food);

		for(int i = 0; i < WorldConstants.INI_PREY_POPULATION; i++) {
			PVector pos = new PVector(p.random((float)window[0], (float)window[1]),
					p.random((float)window[2], (float)window[3]));
			int color = p.color(
					WorldConstants.PREY_COLOR[0],
					WorldConstants.PREY_COLOR[1],
					WorldConstants.PREY_COLOR[2]);
			Animal a = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, color, p, plt, WorldConstants.PREY_ID);
			a.addBehaviour(new Wander(1));
			a.addBehaviour(new AvoidObstacle(0));
			a.addBehaviour(new SmellDetection(0));
			Eye<CellBodyProxy> eye = new Eye<>(a, obstacles);
			a.setEye(eye);
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
			a.addBehaviour(new AvoidObstacle(0));
			a.addBehaviour(new SmellDetection(0));
			Eye<CellBodyProxy> eye = new Eye<>(a, obstacles);
			a.setEye(eye);
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
			if(child != null) allAnimals.add(child);
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
	public float getMeanSmellWeight() {return getMeanSmellWeight(-1);}
	
	public float getMeanSmellWeight(int id) {
		float sum = 0;
		for(Animal a: getAnimalsOfId(id))
			sum += a.getDNA().smellStrength;
		return sum / getNumAnimals(id);
	}
	
	public float getMeanImmuneWeight() {return getMeanImmuneWeight(-1);}

	public float getMeanImmuneWeight(int id) {
		float sum = 0;
		for(Animal a: getAnimalsOfId(id))
			sum += a.getDNA().immuneSystem;
		return sum / getNumAnimals(id);
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

}
