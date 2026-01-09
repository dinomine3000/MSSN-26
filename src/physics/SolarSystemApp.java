package physics;

import java.util.ArrayList;

import hello.IProcessingApp;
import hello.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class SolarSystemApp implements IProcessingApp {
	
	private float sunMass = 1.989e30f;
	private float earthMass = 5.97e24f;
	private float jupiterMass = 1.9e27f;
	private float AU = 1.496e11f;
	private float earthSpeed = 29782.7f;
	private float jupiterSpeed = 0*13e3f + 600;
	private float moonMass = 7.34e22f;
	private float moonSpeed = earthSpeed + 1022f;
	
	private float[] viewport = {0, 0, 1, 1};
	private float windowScale = 6f;
	private double[] window = {-windowScale*AU, windowScale*AU, -windowScale*AU, windowScale*AU};

	private float speedUp = 60*60*24*7;
	
	private SubPlot plt;
	//sun, earth, jupiter, moon, particles
	private ArrayList<Body> planets =  new ArrayList<>();
	private ParticleSystem particles;
	private PSControl psc;
	private float[] velParams = {PApplet.radians(180), PApplet.radians(360), 3*earthSpeed, 5*earthSpeed};
	private float[] lifetimeParams = {speedUp/2, speedUp};
	private float[] radiusParams = {0.01f*AU, 0.02f*AU};
	private float flow = 0.03f;
	
	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub
		plt = new SubPlot(window, viewport, p.width, p.height);
		
		//sun
		planets.add(new Body(new PVector(), new PVector(), sunMass, AU/5, p.color(255, 128, 0)));
		//earth
		planets.add(new Body(new PVector(0, AU), new PVector(earthSpeed, 0), earthMass, AU/5, 
				p.color(0, 180, 120)));
		//jupiter
		planets.add(new Body(new PVector(0, 5.2f*AU), new PVector(jupiterSpeed, 0), jupiterMass, AU/5,
				p.color(120, 120, 0)));
		//moon
		planets.add(new Body(new PVector(0, 1.002653f*AU), new PVector(moonSpeed, 0), moonMass, AU/10,
				p.color(200, 200, 200)));
		planets.add(new Body(new PVector(0.5f*AU, 1.5f*AU), new PVector(earthSpeed*0.5f, 0), earthMass, AU/15,
				p.color(0, 200, 0)));
		
		
		psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, p.color(255));
		particles = new ParticleSystem(new PVector(0, -AU), new PVector(-earthSpeed, 0), earthMass,
				psc);
		planets.add(particles);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(2);
		
		//forces
		for(Body planet: planets) {
			for(Body gravity: planets) {
				if(gravity == planet) continue;
				PVector f = gravity.gravity(planet);
				planet.applyForce(f);
			}
		}
		
		
		//display
		for(Body planet: planets) {
			planet.move(dt*speedUp);
			planet.display(p, plt);
		}
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
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
