package ecosystem;

import hello.IProcessingApp;
import hello.SubPlot;
import processing.core.PApplet;

public class TestTerrainApp implements IProcessingApp {
	private SubPlot plt;
	private Terrain terrain;

	@Override
	public void setup(PApplet p) {
		plt = SubPlot.getPlotAt(p, 0.2f, 0.2f, 0.8f, 0.8f, WorldConstants.WINDOW);
		terrain = new Terrain(p, plt);
		terrain.setStateColors(getColors(p));
		terrain.setRandomStates();
		for(int i = 0; i < 5; i++) terrain.majorityRule();
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
		terrain.display(p);
		
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
