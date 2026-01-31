package boids.behaviours;

import java.util.List;

import boids.Boid;
import ecosystem.CellBodyProxy;
import ecosystem.WorldConstants;
import physics.Body;
import processing.core.PVector;

public class ConditionalBrakeSpoiled extends Behaviour {

	public ConditionalBrakeSpoiled(float weight) {
		super(weight);
	}
	
	@Override
	public boolean isActive(Boid me) {
		if(getFoodCount(me.eye.getNearSight()) < me.dna.hibernationThreshold) {
			return true;
		}
		return false;
	}
	
	private static int getFoodCount(List<Body> bodies) {
		int i = 0;
		for(Body b: bodies) {
			if(b instanceof CellBodyProxy cell && cell.getPatch().getState() == WorldConstants.PatchType.SPOILED.ordinal()) i++;
		}
		return i;
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		return new PVector();
	}

}
