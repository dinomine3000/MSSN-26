package boids.behaviours;

import boids.Boid;
import processing.core.PVector;

public abstract class Behaviour implements IBehaviour{
	public float weight;
	
	public Behaviour(float weight) {
		setWeight(weight);
	}

	@Override
	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public float getWeight() {
		return this.weight;
	}
	
	public boolean isActive(Boid me) {return true;}
}
