package boids.behaviours;

import boids.Boid;
import ca.CellBodyProxy;
import ecosystem.WorldConstants;
import physics.Body;
import processing.core.PVector;

public class SmellDetection extends Behaviour {

	public SmellDetection(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector sum = new PVector();
		int i = 0;
		for(Body body: me.eye.getNearSight()) {
			if(!(body instanceof CellBodyProxy patch)) continue;
			if(patch.getState() != WorldConstants.PatchType.SPOILED.ordinal()) continue;
			sum = PVector.add(sum, patch.getPos());
			i++;
		}
		if(i == 0) return new PVector();
		PVector centroide = PVector.mult(sum, 1/i);
		//vd: cells -> pos.
		//if smell is positive, it wants to go away. if its negative it wants to go towards.
		PVector vd = PVector.sub(me.getPos(), centroide);
		return me.dna.smellStrength > 0 ? vd : vd.mult(-1);
	}

}
