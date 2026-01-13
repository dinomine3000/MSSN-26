package ecosystem;

import java.util.ArrayList;
import java.util.List;

import boids.Eye;
import boids.behaviours.AvoidObstacle;
import boids.behaviours.Wander;
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
		
		List<Body> obstacles = terrain.getObstacles();
		
		for(int i = 0; i < WorldConstants.INI_PREY_POPULATION; i++) {
			PVector pos = new PVector(p.random((float)window[0], (float)window[1]),
					p.random((float)window[2], (float)window[3]));
			int color = p.color(
					WorldConstants.PREY_COLOR[0],
					WorldConstants.PREY_COLOR[1],
					WorldConstants.PREY_COLOR[2]);
			Animal a = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, color, p, plt);
			a.addBehaviour(new Wander(1));
			a.addBehaviour(new AvoidObstacle(0));
			Eye eye = new Eye(a, obstacles);
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
	
	public int getNumAnimals() {
		return allAnimals.size();
	}

	public float getMeanMaxSpeed() {
		float sum = 0;
		for(Animal a: allAnimals)
			sum += a.getDNA().maxSpeed;
		return sum / allAnimals.size();
	}

	public float getStdMaxSpeed() {
		float mean = getMeanMaxSpeed();
		float sum = 0;
		for(Animal a: allAnimals)
			sum += Math.pow(a.getDNA().maxSpeed - mean, 2);
		return (float) Math.sqrt(sum/allAnimals.size());
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
