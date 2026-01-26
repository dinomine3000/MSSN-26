package boids;

import java.util.ArrayList;
import java.util.List;

import boids.behaviours.AvoidObstacle;
import boids.behaviours.Behaviour;
import boids.behaviours.ConditionalBrake;
import boids.behaviours.Wander;
import hello.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class Boid extends Body{
	
	private SubPlot plt;
	private PShape shape;
	public DNA dna;
	public Eye<? extends Body> eye;
	protected List<Behaviour> behaviours;
	public float phiWander;
	private double[] window;
	private float sumWeights;
	private boolean inView = false;
	private boolean inNearView = false;
	private boolean debug = false;

	public Boid(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
		super(pos, new PVector(), mass, radius, color);
		this.plt = plt;
		dna = new DNA();
		window = plt.getWindow();
		setShape(p, plt);
		behaviours = new ArrayList<Behaviour>();
	}
	
	public void setEye(Eye<? extends Body> eye) {
		this.eye = eye;
	}

    public void setShape(PApplet p, SubPlot plt, float radius, int color) {
        this.radius = radius;
        this.color = color;
        setShape(p, plt);
    }
    
	public void setShape(PApplet p, SubPlot plt) {
        float[] rr = plt.getDimInPixel(radius, radius);
        shape = p.createShape();
        shape.beginShape();
        if(debug) {
        	shape.fill(255, 0, 0);	
        }
        else if(inView){
        	if(inNearView) {
            	shape.fill(255, 255, 255);
        	} else {
            	shape.fill(0, 255, 255);	
        	}
        } else {
        	shape.fill(color);
        }
        shape.noStroke();
        shape.vertex(-rr[0], rr[0]/2);
        shape.vertex(rr[0], 0);
        shape.vertex(-rr[0], -rr[0]/2);
        shape.vertex(-rr[0]/2, 0);
        shape.endShape(PConstants.CLOSE);
	}
	
	private void sumWeights() {
		sumWeights = 0;
		
		for(Behaviour b : behaviours)
			sumWeights += b.getWeight();
	}
	
	public void addBehaviour(Behaviour beh) {
		behaviours.add(beh);
		sumWeights();
	}
	
	public void removeBehaviour(Behaviour beh) {
		if(behaviours.contains(beh)) {
			behaviours.remove(beh);
		}
		sumWeights();
	}
	
	public void applyBehaviour(int i, float dt) {
		if(eye != null)
			eye.look();
		
		Behaviour beh = behaviours.get(i);
		PVector vd = beh.getDesiredVelocity(this);
		move(dt, vd);
	}
	
	public void applyBehaviours(float dt) {
        if (eye != null) eye.look();
        PVector vd = new PVector();
        int sum = 0;
        for (Behaviour behavior : behaviours) {
        	if(!behavior.isActive(this)) continue;
            PVector vdd = behavior.getDesiredVelocity(this);
            sum += behavior.getWeight();
            vdd.mult(behavior.getWeight());
            vd.add(vdd);
        }
        move(dt, vd.mult(1/sum));
	}
	
	private void move(float dt, PVector vd) {
//        vd.normalize().mult(dna.maxSpeed);
		if(vd.mag() > dna.maxSpeed) vd.normalize().mult(dna.maxSpeed);
        PVector fs = PVector.sub(vd, vel);
        applyForce(fs.limit(dna.maxForce));
        super.move(dt);

        if(pos.x < window[0])
            pos.x += window[1] - window[0];
        if(pos.y < window[2])
            pos.y += window[3] - window[2];
        if(pos.x >= window[1])
            pos.x -= window[1] - window[0];
        if(pos.y >= window[3])
            pos.y -= window[3] - window[2];
	}
	
	public void display(PApplet p, SubPlot plt) {
        p.pushMatrix();
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        this.setShape(p, plt);
        p.translate(pp[0], pp[1]);
        p.rotate(-vel.heading());
        p.shape(shape);
        p.popMatrix();
        
        this.inNearView = false;
        this.inView = false;    
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void mutateBehaviours() {
		for(Behaviour b: behaviours) {
			if(b instanceof ConditionalBrake) {
				b.weight += DNA.random(-0.2f, 0.2f);
				b.weight = Math.max(0,  b.weight);
			}
		}
		sumWeights();
	}
	
	public List<Behaviour> getBehaviours(){
		return behaviours;
	}
	
	public DNA getDNA() {
		return this.dna;
	}

	public void setInView() {
		this.inView = true;
	}
	
	public void setInNearView() {
		this.inNearView = true;
	}

}
