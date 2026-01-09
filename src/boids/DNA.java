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
	
	
	public DNA() {
		maxSpeed = random(3, 5);
		maxForce = random(10, 20);
		visionDistance = random(3, 8);
		visionSafeDistance = 0.25f * visionDistance;
		visionAngle = (float)Math.PI;
		deltaTPursuit = random(0.5f, 1);
		radiusArrive = 3f;
		deltaTWander = random(1f, 1f);
		radiusWander = random(3, 3);
		deltaPhiWander = (float)Math.PI/4;
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
