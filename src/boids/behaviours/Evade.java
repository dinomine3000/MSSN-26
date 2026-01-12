package boids.behaviours;

import boids.Boid;
import physics.Body;
import processing.core.PVector;

public class Evade extends Behaviour{

	public Evade(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.eye.target;
		PVector d = bodyTarget.getVel().mult(me.dna.deltaTPursuit);
		PVector target = PVector.add(bodyTarget.getPos(), d);
		PVector vd = PVector.sub(target, me.getPos());
		return vd.mult(-1);
		//return vd.mult(-1*Math.min(mag, me.dna.maxSpeed));
	}

}
