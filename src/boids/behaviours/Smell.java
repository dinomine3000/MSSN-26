package boids.behaviours;

import boids.Boid;
import ecosystem.CellBodyProxy;
import ecosystem.WorldConstants;
import physics.Body;
import processing.core.PVector;

public class Smell extends Behaviour {

	public Smell(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector sum = new PVector();
		int i = 0;
		for(Body body: me.eye.getNearSight()) {
			if(!(body instanceof CellBodyProxy patch)) continue;
			if(patch.getPatch().getState() != WorldConstants.PatchType.SPOILED.ordinal()) continue;
			sum = PVector.add(sum, patch.getPos());
			i++;
		}
		if(i == 0) return getWanderVelocity(me);
		PVector centroide = PVector.mult(sum, 1/i);
		//vd: cells -> pos.
		//if smell is positive, it wants to go away. if its negative it wants to go towards.
		PVector vd = PVector.sub(me.getPos(), centroide);
		return me.dna.smellStrength > 0 ? vd : vd.mult(-1);
	}
	
	private PVector getWanderVelocity(Boid me) {
		PVector center = me.getPos().copy();
		center.add((me.getVel().copy()).mult(me.dna.deltaTWander));
		
		PVector target = new PVector(me.dna.radiusWander * (float) Math.cos(me.phiWander), 
		me.dna.radiusWander * (float) Math.sin(me.phiWander));
		target.add(center);
		
		me.phiWander += 2*(Math.random()-0.5)*me.dna.deltaPhiWander;
		PVector vd = PVector.sub(target, me.getPos()).mult(me.dna.maxSpeed);
		return vd;
	}
	

}
