package boids.behaviours;

import java.util.List;

import boids.Boid;
import ecosystem.CellBodyProxy;
import ecosystem.WorldConstants;
import physics.Body;
import processing.core.PVector;

public class PursuitFood extends Behaviour {

	public PursuitFood(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}
	
	private Body getTarget(Boid me) {
		CellBodyProxy curTar = (CellBodyProxy) me.eye.target;
		if(curTar == null || curTar.getPatch().getState() != WorldConstants.PatchType.FOOD.ordinal()) {
			return recalculateTarget(me);
		}
		return curTar;
		
	}
	
	private Body recalculateTarget(Boid me) {
		List<Body> matches = me.eye.getFarSight();
		double dist = Integer.MAX_VALUE;
		Body closest = null;
		for(Body body: matches) {
			if(body instanceof CellBodyProxy b) {
				if(b.getPatch().getState() == WorldConstants.PatchType.FOOD.ordinal()) {
					double testDist = me.getPos().dist(b.getPatch().getCenter());
					if(testDist < dist) {
						closest = b;
						dist = testDist;
					}
				}
			}
		}
		return closest;
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body target = getTarget(me);
		if(target == null) return new PVector();
		return target.getPos().sub(me.getPos()).mult(me.dna.maxSpeed);
	}

}
