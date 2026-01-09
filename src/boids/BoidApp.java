package boids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import boids.behaviours.Arrive;
import boids.behaviours.Brake;
import boids.behaviours.Flee;
import boids.behaviours.Seek;
import boids.behaviours.Wander;
import hello.IProcessingApp;
import hello.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class BoidApp implements IProcessingApp{
	
	private Boid b;
	private double[] window = {-10, 10, -10, 10};
	private float[] viewport = {0, 0, 1, 1};
	private SubPlot plt;
	private Body target;
	private List<Body> allTrackingBodies;
	private int index = 0;
	
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		b = new Boid(new PVector(), 1, 0.5f, p.color(0), p, plt);
		b.addBehaviour(new Arrive(1f));
		b.addBehaviour(new Seek(1f));
		b.addBehaviour(new Flee(1f));
		b.addBehaviour(new Wander(1f));
		b.addBehaviour(new Brake(1f)); 
		
		target = new Body(new PVector(), new PVector(), 1f, 0.2f, p.color(255, 0, 0));
		allTrackingBodies = new ArrayList<Body>();
		allTrackingBodies.add(target);
		Eye eye = new Eye(b, allTrackingBodies);
		b.setEye(eye);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);
		p.fill(0);            // sets text fill color
		p.textSize(12);       // optional, but helps ensure visibility
		p.text("Current Behaviour Index: " + index, 10, 20);
		
		
		b.applyBehaviour(index, dt);
		
		b.display(p, plt);
	}

	@Override
	public void mousePressed(PApplet p) {
		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
		target.setPos(new PVector((float)ww[0],(float)ww[1]));
		System.out.println(Arrays.toString(ww));
	}

	@Override
	public void keyPressed(PApplet p) {
		//Mudar entre os behaviors
		if (p.key == 'q')
			//Arrive
			index = 0;
		if (p.key == 'w')
			//Seek
			index = 1;
		if (p.key == 'e')
			//Flee
			index = 2;
		if (p.key == 'r')
			//Wander
			index = 3;
		//Travar
		if (p.key == 't')
			//Brake
			index = 4;
		
		//Mudar speed
		if (p.key == 'p')
			b.getDNA().alterMaxSpeed(0.5f);
		if (p.key == 'ç')
			b.getDNA().alterMaxSpeed(-0.5f);
			
		//Mudar force
		if (p.key == 'o')
			b.getDNA().alterMaxForce(0.5f);
		if (p.key == 'l')
			b.getDNA().alterMaxForce(-0.5f);
		
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(PApplet p) {
		// TODO Auto-generated method stub
		
	}
}
