package ecosystem;

import hello.IProcessingApp;
import hello.SubPlot;
import processing.core.PApplet;
import util.TimeGraph;

public class TestEcosystemApp implements IProcessingApp {
	
	private SubPlot plt, pltG1, pltG2, pltCol1, pltCol2;
	
	private float timeDuration = 120;
    private final float refPopulation = 100f;

    private final double[] winCol= {0, 2, 0,2};
    
    private final double[] winGraph1 = {0, timeDuration, 0, 2 * refPopulation};
    private final double[] winGraph2 = {0,timeDuration, 0, 3*refPopulation};
    
    private TimeGraph t1, t2;
	
	private Terrain terrain;
	private Population population;
	
	private float timer, updateGraphTime;
	private float intervalUpdate = 1;
	
	private boolean tickSeasons = false;

	@Override
	public void setup(PApplet p) {
		
		plt = SubPlot.getPlotAt(p, 0, 0, 0.6f, 1, WorldConstants.WINDOW);
		pltG1 = SubPlot.getPlotAt(p, 0.65f, 0.1f, 0.95f, 0.3f, winGraph1);
		pltG2 = SubPlot.getPlotAt(p, 0.65f, 0.4f, 0.95f, 0.6f, winGraph2);
		pltCol1 = SubPlot.getPlotAt(p, 0.6f, 0, 0.65f, 1, winCol);
		pltCol2 = SubPlot.getPlotAt(p, 0.95f, 0, 1, 1, winCol);

		t1 = new TimeGraph(p, pltG1, p.color(255, 0, 0), refPopulation);
		t2 = new TimeGraph(p, pltG2, p.color(255, 0, 0), refPopulation);
		
		terrain = new Terrain(p, plt);
		terrain.setStateColors(getColors(p));
		terrain.initRandom(WorldConstants.PATCH_TYPE_PROB);
		for(int i = 0; i<2; i++) terrain.majorityRule();
		
		population = new Population(p, plt, terrain);
		
		timer = 0;
		updateGraphTime = timer + intervalUpdate;
	}

	private int[] getColors(PApplet p) {
		int[] colors = new int[WorldConstants.NSTATES];
		for(int i = 0; i < WorldConstants.NSTATES; i++) {
			colors[i] = p.color(
					WorldConstants.TERRAIN_COLORS[i][0],
					WorldConstants.TERRAIN_COLORS[i][1],
					WorldConstants.TERRAIN_COLORS[i][2]);
		}
		return colors;
	}

	@Override
	public void draw(PApplet p, float dt) {
		timer += dt;
	
		terrain.regenerate();
		if(tickSeasons)
			terrain.tickSeason();
		population.update(dt, terrain);
		
		terrain.display(p);
		population.display(p, plt);
		
		
		
		if(timer > updateGraphTime) {
			
			int preyId = WorldConstants.PREY_ID;
			int scavId = WorldConstants.SCAV_ID;

			int popPrey = population.getNumAnimals(preyId);
			int popScav = population.getNumAnimals(scavId);
			
			System.out.println(String.format("Time = %ds", (int)timer));
			System.out.println("number of prey = " + popPrey + "\nnumber of scavengers = " + popScav);
			System.out.println("");
			
			t1.plot(timer,  popPrey);
			t2.plot(timer,  popScav);
			
			updateGraphTime = timer + intervalUpdate;
		}
		
		p.fill(225);
		p.textSize(25);
	    p.textAlign(PApplet.LEFT, PApplet.TOP);
	    p.text("Current season is " + getSeasonFromOrdinal(terrain.season), 5, 5);
	    
		if(!tickSeasons) {
		    p.text("Seasons are frozen.", 5, 45);
		}
		
		float[] bb1 = pltCol1.getBoundingBox();
		float[] bb2 = pltCol2.getBoundingBox();
		p.pushStyle();
		p.fill(0);
		p.noStroke();
		p.rect(bb1[0], bb1[1], bb1[2], bb1[3]);
		p.rect(bb2[0], bb2[1], bb2[2], bb2[3]);
	}
	
	public String getSeasonFromOrdinal(int season) {
        if (season == WorldConstants.Season.FALL.ordinal()) {return "Fall";}
        if (season == WorldConstants.Season.WINTER.ordinal()) {return "Winter";}
        if (season == WorldConstants.Season.SPRING.ordinal()) {return "Spring";}
        if (season == WorldConstants.Season.SUMMER.ordinal()) {return "Summer";}

        return "Fall";
	}

	@Override
	public void mousePressed(PApplet p) {
		if(p.mouseButton == 39) {
			this.terrain.advanceSeasons();
			return;
		}
		
		winGraph1[0] = timer;
		winGraph1[1] = timer + timeDuration;
		winGraph1[3] = 2*population.getNumAnimals();
		pltG1 = SubPlot.getPlotAt(p, 0.65f, 0.1f, 0.95f, 0.3f, winGraph1);
		t1 = new TimeGraph(p, pltG1, p.color(255, 0, 0), population.getNumAnimals(WorldConstants.PREY_ID));

		winGraph2[0] = timer;
		winGraph2[1] = timer + timeDuration;
		winGraph1[3] = 2*population.getNumAnimals();
		pltG2 = SubPlot.getPlotAt(p, 0.65f, 0.4f, 0.95f, 0.6f, winGraph2);
		t2 = new TimeGraph(p, pltG2, p.color(255, 0, 0), population.getNumAnimals(WorldConstants.SCAV_ID));
		
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(PApplet p) {
		if(p.key == 'e') {
			this.tickSeasons = !this.tickSeasons;
		}
		
	}

}
