package boids.behaviours;

import boids.Boid;
import physics.Body;
import processing.core.PVector;

public class Pursuit extends Behaviour{

	public Pursuit(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body target = me.eye.target;
		PVector d = target.getVel().copy().mult(me.dna.deltaTPursuit);
		PVector tar = PVector.add(target.getPos(), d);
		return PVector.sub(tar,  me.getPos());
	}

}
