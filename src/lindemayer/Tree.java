package lindemayer;

import processing.core.PApplet;
import processing.core.PVector;
import hello.SubPlot;

public class Tree {
	
    private LSystem lsys;
    private Turtle turtle;
    
    private PVector pos;
    
    private float len;
    private float growthRate;
    
    private int numberOfSeasonsToGrow;
    private float scalingFactor;
    private float timeBetweenSeasons;
    private float now;
    private float nextSeasonTime;
    private PApplet p;

    public Tree(String axiom, Rule[] rules, PVector pos, float refLen, float angle, int niter,
                float scalingFactor, float timeBetweenSeasons, PApplet p) {
        lsys = new LSystem(axiom, rules);

        len = 0;
        growthRate = refLen/timeBetweenSeasons;
        turtle = new Turtle(refLen, angle);

        this.pos = pos;
        numberOfSeasonsToGrow = niter;
        this.scalingFactor = scalingFactor;
        this.timeBetweenSeasons = timeBetweenSeasons;
        now = p.millis()/1000f;
        nextSeasonTime = now + timeBetweenSeasons;
        this.p = p;
    }

    public void grow(float dt) {
        now += dt;
        if (now < nextSeasonTime) {
            len += growthRate * dt;
            turtle.setLength(len);
        } else if (lsys.getGeneration() < numberOfSeasonsToGrow) {
            lsys.nextGeneration();
            len *= scalingFactor;
            growthRate *= scalingFactor;
            turtle.setLength(len);
            nextSeasonTime = now + timeBetweenSeasons;
        }
    }

    public void display(PApplet p, SubPlot plt) {
        p.pushMatrix();
        turtle.setPose(pos, (float) Math.PI/2, p, plt);
        turtle.render(lsys, p, plt);
        p.popMatrix();
    }
}
