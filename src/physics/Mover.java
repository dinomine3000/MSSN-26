package physics;


import processing.core.PVector;

public class Mover {
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	protected float mass;
	private static double G = 6.67e-11;
	
	protected Mover(PVector pos, PVector vel, float mass) {
		this.pos = pos.copy();
		this.vel = vel;
		this.mass = mass;
		acc = new PVector();
	}

	/**
	 * gravity THIS applies on m
	 * @param m
	 * @return
	 */
	public PVector gravity(Mover m) {
		PVector r = PVector.sub(pos, m.pos);
		float dist = r.mag();
		float strength = (float) (G * mass * m.mass / Math.pow(dist, 2));
		return r.normalize().mult(strength);
	}
	
	public void applyForce(PVector force) {
		acc.add(PVector.div(force, mass));
		
	}
	
	public void move(float dt) {
		vel.add(acc.mult(dt));
		pos.add(PVector.mult(vel, dt));
		acc.mult(0);
	}
	
	public void setPos(PVector pos) {
		this.pos = pos;
	}
	
	public PVector getPos() {
		return this.pos;
	}
	
	public void setVel(PVector vel) {
		this.vel = vel;
	}

	public PVector getAcc() {
		return this.acc;
	}
	
	public PVector getVel() {
		return this.vel;
	}
}
