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
		vd = vd.normalize().mult(me.dna.maxSpeed);
		float R = me.dna.radiusArrive;
		if (dist < R) {
			vd.mult((float)(Math.pow(dist/R, k)));
		}
		return vd;
	}

}
