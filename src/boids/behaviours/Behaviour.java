package boids.behaviours;

import processing.core.PVector;

public abstract class Behaviour implements IBehaviour{
	private float weight;
	
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
}
