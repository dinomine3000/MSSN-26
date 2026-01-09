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
		Body target = me.eye.target;
		float dist = PVector.sub(me.getPos(), target.getPos()).mag();
		PVector d = target.getVel().mult(me.dna.deltaTPursuit);
		PVector tar = PVector.add(target.getPos(), d);
		PVector vd = PVector.sub(tar,  me.getPos());
		float mag = (float) (1/Math.pow(dist, 4));
		return vd.mult(-1*me.dna.maxSpeed);
		//return vd.mult(-1*Math.min(mag, me.dna.maxSpeed));
	}

}
