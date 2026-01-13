package physics;

import hello.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class Body extends Mover {
	protected int color;
	protected float radius;
	
	public Body(PVector pos, PVector vel, float mass, float radius, int color) {
		super(pos, vel, mass);
		this.radius = radius;
		this.color = color;
	}

	public Body(PVector centerCell) {
		super(centerCell, new PVector(), 0f);
	}

	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.fill(color);
		float[] r = plt.getVectorCoord(radius, radius);
		
		p.noStroke();
		p.circle(pp[0], pp[1], 2*r[0]);
		
		p.popStyle();
	}
	
	public void setColor(int color) {
		this.color = color;
	}
}
