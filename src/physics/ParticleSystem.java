package physics;

import java.util.ArrayList;
import java.util.List;

import hello.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;

public class ParticleSystem extends Body {
	
	private List<Particle> particles;
	private PSControl psc;

	protected ParticleSystem(PVector pos, PVector vel, float mass, PSControl psc) {
		super(pos, vel, mass, 0, 0);
		this.psc = psc;
		this.particles = new ArrayList<Particle>();
	}	
	
	@Override
	public void move(float dt)
	{
		super.move(dt);
		addParticles(dt);
		for(int i=particles.size()-1;i>=0;i--)
		{
			Particle p = particles.get(i);
			p.move(dt);
			if (p.isDead())
				particles.remove(i);
		}
	}
	
	private void addParticles(float dt) {
		float particlesPerFrame = psc.getFlow() * dt;
		int n = (int) particlesPerFrame;
		float f = particlesPerFrame - n;
		for (int i = 0; i<n; i++) {
			addOneParticle();
		}
		if (Math.random() < f) {
			addOneParticle();
		}
	}
	
	
	private void addOneParticle()
	{
		PVector vel = PVector.add(psc.getRndVel(), this.getVel());
		Particle particle = new Particle(pos, vel, psc.getRndRadius(), psc.getColor(), psc.getRndLifetime());
		particles.add(particle);
	}
	
	@Override
	public void display(PApplet p, SubPlot plt)
	{
		//System.out.println(particles.size());
		for (Particle particle : particles)
		{
			particle.display(p, plt);
		}
	}

}