package boids;

import java.util.ArrayList;
import java.util.List;

import physics.Body;
import processing.core.PVector;

public class Eye {
	private List<Body> trackBodies;
	private List<Body> farSight;
	private List<Body> nearSight;
	private Boid me;
	public Body target;
	
	public Eye(Boid me, List<Body> trackBodies) {
		this.me = me;
		this.trackBodies = trackBodies;
		target =  this.trackBodies.get(0);
	}
	
	public void look() {
		farSight = new ArrayList<Body>();
		nearSight = new ArrayList<Body>();
		for (Body b: trackBodies) {
			if (farSight(b.getPos())) {
				farSight.add(b);
			}
			if (nearSight(b.getPos())) {
				nearSight.add(b);
			}
		}
	}

	public List<Body> getFarSight(){
		return farSight;
	}
	
	public List<Body> getNearSight(){
		return nearSight;
	}
	
	private boolean inSight(PVector t, float maxDistance, float maxAngle) {
		PVector r = PVector.sub(t, me.getPos());
		float d = r.mag();
		float angle = PVector.angleBetween(r, me.getVel());
		return ((d > 0) && (d < maxDistance) && (angle < maxAngle));
	}

	private boolean farSight(PVector t) {
		return inSight(t, me.dna.visionDistance, me.dna.visionAngle);
	}
	
	private boolean nearSight(PVector t) {
		return inSight(t, me.dna.visionSafeDistance, (float)Math.PI);
	}

}
