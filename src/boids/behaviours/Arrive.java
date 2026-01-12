package boids.behaviours;

import boids.Boid;
import processing.core.PVector;

public class Arrive extends Behaviour {

	private static final int k = 2;
	public Arrive(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector vd = PVector.sub(me.eye.target.getPos(), me.getPos());
		float dist = vd.copy().mag();
		float R = me.dna.radiusArrive;
		if (dist < R) {
			vd.mult(dist/R);
		}
		return vd;
	}

}
