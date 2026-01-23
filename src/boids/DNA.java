package boids;

import physics.PSControl;

public class DNA {
	public float maxSpeed;
	public float maxForce;
	public float visionDistance;
	public float visionSafeDistance;
	public float visionAngle;
	public float deltaTPursuit;
	public float radiusArrive;
	public float deltaTWander;
	public float radiusWander;
	public float deltaPhiWander;
	
	public float smellStrength;
	
	
	public DNA() {
		//phyics
		maxSpeed = random(1f, 2f);
		maxForce = random(4f, 7f);
		//vision
		visionDistance = random(1.5f, 2.5f);
		visionSafeDistance = 0.25f * visionDistance;
		visionAngle = (float)Math.PI * 0.3f;
		//pursuit
		deltaTPursuit = random(0.5f, 1f);
		//arive
		radiusArrive = random(3f, 5f);
		//wander
		deltaTWander = random(0.3f, 0.6f);
		radiusWander = random(1f, 3f);
		deltaPhiWander = (float)Math.PI/8;

		smellStrength = random(-1f, 1f);
	}
	
	public DNA(DNA dna, boolean mutate) {
		maxSpeed = dna.maxSpeed;
		maxForce = dna.maxForce;
		
		visionDistance = dna.visionDistance;
		visionSafeDistance = dna.visionSafeDistance;
		visionAngle = dna.visionAngle;
		
		deltaTPursuit = dna.deltaTPursuit;
		radiusArrive = dna.radiusArrive;
		
		deltaTWander = dna.deltaPhiWander;
		deltaPhiWander = dna.deltaPhiWander;
		radiusWander = dna.radiusWander;
		
		smellStrength = dna.smellStrength;
		if(mutate) mutate();
	}
	
	private void mutate() {
		maxSpeed += random(-0.2f, 0.2f);
		maxSpeed = Math.max(0, maxSpeed);
		
		smellStrength += random(-1f, 1f);
		smellStrength = Math.max(-10, smellStrength);
		smellStrength = Math.min(10, smellStrength);
	}

	public static float random(float min, float max) {
		return (float) (min + (max - min) * Math.random());
	}

	public void alterMaxSpeed(float speed) {
		if ((maxSpeed + speed < 25) && (maxSpeed + speed > 0))
			{
				maxSpeed += speed;
				System.out.println("ALtered speed by: " + speed);
			}
		else
			{System.out.println("Speed limit reached");}
		
	}
	
	public void alterMaxForce(float force) {
		if ((maxForce < 25)||(maxForce > 5))
			{maxForce += force;}
		else
			{System.out.println("Force limit reached");}
	}
}
