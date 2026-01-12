package ecosystem;

import hello.IProcessingApp;
import hello.SubPlot;
import processing.core.PApplet;

public class TestEcosystemApp implements IProcessingApp {
	private SubPlot plt;
	
	private Terrain terrain;
	private Population population;

	@Override
	public void setup(PApplet p) {
		plt = SubPlot.getPlotAt(p, 0, 0, 1, 1, WorldConstants.WINDOW);
		terrain = new Terrain(p, plt);
		terrain.setStateColors(getColors(p));
		terrain.initRandom(WorldConstants.PATCH_TYPE_PROB);
		for(int i = 0; i<2; i++) terrain.majorityRule();
		population = new Population(p, plt, terrain);
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
		terrain.regenerate();
		population.update(dt, terrain);
		
		terrain.display(p);
		population.display(p, plt);
		
		System.out.println("População atual: " + population.getNumAnimals());
	}

	@Override
	public void mousePressed(PApplet p) {
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

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}
