package ecosystem;

import hello.IProcessingApp;
import hello.SubPlot;
import processing.core.PApplet;
import util.TimeGraph;

public class TestEcosystemApp implements IProcessingApp {
	private SubPlot plt, pltG1, pltG2, pltG3;
	
	private float timeDuration = 60;
    private final float refPopulation = 720;
    private final float refMeanMaxSpeed = 1f;
    private final float refStdMaxSpeed = 0.2f;

    private final double[] winGraph1 = {0, timeDuration, 0, 2 * refPopulation};
    private final double[] winGraph2 = {0,timeDuration, 0, 2*refMeanMaxSpeed};
    private final double[] winGraph3 = {0,timeDuration, 0, 2*refStdMaxSpeed};
    
    private TimeGraph t1, t2, t3;
	
	private Terrain terrain;
	private Population population;
	
	private float timer, updateGraphTime;
	private float intervalUpdate = 1;

	@Override
	public void setup(PApplet p) {
		plt = SubPlot.getPlotAt(p, 0, 0, 0.6f, 1, WorldConstants.WINDOW);
		pltG1 = SubPlot.getPlotAt(p, 0.6f, 0f, 1f, 0.2f, winGraph1);
		pltG2 = SubPlot.getPlotAt(p, 0.6f, 0.3f, 1f, 0.5f, winGraph2);
		pltG3 = SubPlot.getPlotAt(p, 0.6f, 0.6f, 1f, 0.8f, winGraph3);

		t1 = new TimeGraph(p, pltG1, p.color(255, 0, 0), refPopulation);
		t2 = new TimeGraph(p, pltG2, p.color(255, 0, 0), refMeanMaxSpeed);
		t3 = new TimeGraph(p, pltG3, p.color(255, 0, 0), refStdMaxSpeed);
		
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
			colors[i] = p.color(WorldConstants.TERRAIN_COLORS[i][0],
					WorldConstants.TERRAIN_COLORS[i][1],
					WorldConstants.TERRAIN_COLORS[i][2]);
		}
		return colors;
	}

	@Override
	public void draw(PApplet p, float dt) {
		timer += dt;
	
		terrain.regenerate();
		population.update(dt, terrain);
		
		terrain.display(p);
		population.display(p, plt);
		
		if(timer > updateGraphTime) {
			System.out.println(String.format("Time = %ds", (int)timer));
			System.out.println("numAnimals = " + population.getNumAnimals());
			System.out.println("meanMaxSpeed = " + population.getMeanMaxSpeed());
			System.out.println("StdMaxSpeed = " + population.getStdMaxSpeed());
			float[] weights = population.getMeanWeights();
			System.out.println("meanWeightWander = " + weights[0] + 
					"\nmeanWeightAvoid = " + weights[1]);
			System.out.println("");
			t1.plot(timer,  population.getNumAnimals());
			t2.plot(timer,  population.getMeanMaxSpeed());
			t3.plot(timer,  population.getStdMaxSpeed());
			updateGraphTime = timer + intervalUpdate;
		}
		
	}

	@Override
	public void mousePressed(PApplet p) {
		winGraph1[0] = timer;
		winGraph1[1] = timer + timeDuration;
		winGraph1[3] = 2*population.getNumAnimals();
		pltG1 = SubPlot.getPlotAt(p, 0.6f, 0f, 1f, 0.2f, winGraph1);
		t1 = new TimeGraph(p, pltG1, p.color(255, 0, 0), population.getNumAnimals());

		winGraph2[0] = timer;
		winGraph2[1] = timer + timeDuration;
		t2 = new TimeGraph(p, pltG2, p.color(255, 0, 0), refMeanMaxSpeed);

		winGraph3[0] = timer;
		winGraph3[1] = timer + timeDuration;
		t3 = new TimeGraph(p, pltG3, p.color(255, 0, 0), refStdMaxSpeed);
		
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
		// TODO Auto-generated method stub
		
	}

}
