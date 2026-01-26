package boids.behaviours;

import boids.Boid;
import processing.core.PVector;

public class ConditionalBrake extends Behaviour {

	public ConditionalBrake(float weight) {
		super(weight);
	}
	
	@Override
	public boolean isActive(Boid me) {
		if(me.eye.getNearSight().size() < me.dna.hibernationThreshold) {
			return true;
		}
		return false;
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		return new PVector();
	}

}
