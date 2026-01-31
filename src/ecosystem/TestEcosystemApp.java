package ecosystem;

import hello.IProcessingApp;
import hello.SubPlot;
import processing.core.PApplet;
import util.TimeGraph;

public class TestEcosystemApp implements IProcessingApp {
	
	private SubPlot plt, pltG1, pltG2, pltG3, pltG4, pltG5, pltG6,
					pltCol1, pltCol2, pltCol3,
					pltLin1, pltLin2, pltLin3, pltLin4;
	
	private SubPlot[] pltAuxs = {pltCol1, pltCol2, pltCol3, pltLin1, pltLin2, pltLin3, pltLin4};
	
	private float timeDuration = 120f;
    private final float refPopulation = 100f;
    private final float refHiber = 3f;
    private final float refThreshold = 1f;
    private final float refVision = 5f;

    
    private final double[] winGraph1 = {0, timeDuration, 0, 3 * refPopulation};
    private final double[] winGraph2 = {0,timeDuration, 0, 2 * refPopulation};
    private final double[] winGraph3 = {0,timeDuration, 0, 2 * refThreshold};
    private final double[] winGraph4 = {0,timeDuration, 0, 2 * refHiber};
    private final double[] winGraph5 = {0,timeDuration, 0, 2 * refHiber};
    private final double[] winGraph6 = {0,timeDuration, 0, 2 * refThreshold};
    
    private final double[] winSect= {0, 2, 0, 2};
    
    private TimeGraph t1, t2, t3, t4, t5, t6;
	
	private Terrain terrain;
	private Population population;
	
	private float timer, updateGraphTime;
	private float intervalUpdate = 1;
	
	private boolean tickSeasons = true;

	@Override
	public void setup(PApplet p) {
		//0.2625
		plt = SubPlot.getPlotAt(p, 0, 0, 0.4f, 1, WorldConstants.WINDOW);
		//para criar um subplot, defines 2 pontos, 1 e 2. as coordenadas sao percentages do ecra.
		//x1 = 0.2, y1 = 0.6 significa que o ponto está a 20% do caminho da esquerda para a direita, e 60% do caminho de cima para baixo
		//ent defines 2 pontos, (x1, y1) e (x2, y2) para definir os cantos opostos do subplot.
		pltG1 = SubPlot.getPlotAt(p, 0.425f, 0.1f, 0.6875f, 0.3f, winGraph1);
		pltG2 = SubPlot.getPlotAt(p, 0.425f, 0.4f, 0.6875f, 0.6f, winGraph2);
		pltG3 = SubPlot.getPlotAt(p, 0.425f, 0.7f, 0.6875f, 0.9f, winGraph3);
		pltG4 = SubPlot.getPlotAt(p, 0.7125f, 0.1f, 0.975f, 0.3f, winGraph4);
		pltG5 = SubPlot.getPlotAt(p, 0.7125f, 0.4f, 0.975f, 0.6f, winGraph5);
		pltG6 = SubPlot.getPlotAt(p, 0.7125f, 0.7f, 0.975f, 0.9f, winGraph6);
		
		pltAuxs[0] = SubPlot.getPlotAt(p, 0.4f, 0, 0.425f, 1, winSect);
		pltAuxs[1] = SubPlot.getPlotAt(p, 0.6875f, 0, 0.7125f, 1, winSect);
		pltAuxs[2] = SubPlot.getPlotAt(p, 0.975f, 0, 1, 1, winSect);
		
		pltAuxs[3] = SubPlot.getPlotAt(p, 0.4f, 0, 1, 0.1f, winSect);
		pltAuxs[4] = SubPlot.getPlotAt(p, 0.4f, 0.3f, 1, 0.4f, winSect);
		pltAuxs[5] = SubPlot.getPlotAt(p, 0.4f, 0.6f, 1, 0.7f, winSect);
		pltAuxs[6] = SubPlot.getPlotAt(p, 0.4f, 0.9f, 1, 1f, winSect);

		t1 = new TimeGraph(p, pltG1, p.color(255, 0, 0), refPopulation);
		t2 = new TimeGraph(p, pltG2, p.color(255, 0, 0), refPopulation);
		t3 = new TimeGraph(p, pltG3, p.color(255, 0, 0), refThreshold);
		t4 = new TimeGraph(p, pltG4, p.color(255, 0, 0), refHiber);
		t5 = new TimeGraph(p, pltG5, p.color(255, 0, 0), refHiber);
		t6 = new TimeGraph(p, pltG6, p.color(255, 0, 0), refThreshold);
		
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
//			float preySmell = population.getMeanSmell(preyId);
//			float scavSmell = population.getMeanSmell(scavId);
//			float preySd = population.getSdSmell(preyId, preySmell);
//			float scavSd = population.getSdSmell(scavId, scavSmell);
			float[] paramMean = population.getMeanWeights();
			float[] paramSd = population.getSdWeights(paramMean);
//			float visionMean = population.getMeanVisionSafeDistance();
			float limiarMean = population.getMean(1);
			float limiarSd = population.getDesvioPadrao(1);
//			float visionLimiar = population.getLimiar(2);
//			float visionDesvP = population.getDesvioPadrao(2);
			
			System.out.println(String.format("Time = %ds", (int)timer));
			System.out.println("number of prey = " + popPrey + "\nnumber of scavengers = " + popScav);
			if(WorldConstants.DO_HIBERNATION) {
				System.out.println("hibernation weight mean = " + paramMean[1] + "\nhibernation weight sd= " + paramSd[1]);
				System.out.println("threshold mean = " + limiarMean + "\nthreshold sd = " + limiarSd);
			}
			
			t1.plot(timer,  popPrey);
			t2.plot(timer,  popScav);
			if(WorldConstants.DO_HIBERNATION) {
				t3.plot(timer,  limiarMean);
				t4.plot(timer,  paramMean[1]);
				t5.plot(timer,  paramSd[1]);
				t6.plot(timer,  limiarSd);
			}
			updateGraphTime = timer + intervalUpdate;
		}
		
		drawText(p, "Current season is " + getSeasonFromOrdinal(terrain.season), 5, 5);
		
		if(!tickSeasons) {
			drawText(p, "Seasons are frozen.", 5, 45);
		}
		
		p.pushStyle();
		p.fill(0);
		p.noStroke();
		
		for (SubPlot pltAux : pltAuxs) {
			float[] bb = pltAux.getBoundingBox();
			p.rect(bb[0], bb[1], bb[2], bb[3]);
		}
		drawText(p, "Number of Prey:", 518, 35);
		drawText(p, "Number of Scavangers:", 518, 215);
		if(!WorldConstants.DO_HIBERNATION) return;
		drawText(p, "Hibernation Threshold Mean:", 518, 395);
		drawText(p, "Hibernation Weight Mean:", 878, 35);
		drawText(p, "Hibernation Weight Sd:", 878, 215);
		drawText(p, "Hibernation Threshold Sd:", 878, 395);	
	}

	
	private void drawText(PApplet p, String text, int x, int y) {
		p.textSize(25);
		p.fill(0);
	    p.textAlign(PApplet.LEFT, PApplet.TOP);
	    for(int i = -1; i < 2; i++){
	        p.text(text, x+i,y);
	        p.text(text, x,y+i);
	    }
		p.fill(250);
	    p.text(text, x, y);
//	    p.text("Current season is " + getSeasonFromOrdinal(terrain.season), 5, 5);
	    
	}
	
	public String getSeasonFromOrdinal(int season) {
        if (season == WorldConstants.Season.FALL.ordinal()) {return "Fall";}
        if (season == WorldConstants.Season.WINTER.ordinal()) {return "Winter";}
//        if (season == WorldConstants.Season.SPRING.ordinal()) {return "Spring";}
//        if (season == WorldConstants.Season.SUMMER.ordinal()) {return "Summer";}

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
		winGraph1[3] = 2*population.getNumAnimals(WorldConstants.PREY_ID);
		pltG1 = SubPlot.getPlotAt(p, 0.425f, 0.1f, 0.6875f, 0.3f, winGraph1);
		t1 = new TimeGraph(p, pltG1, p.color(255, 0, 0), population.getNumAnimals(WorldConstants.PREY_ID));

		winGraph2[0] = timer;
		winGraph2[1] = timer + timeDuration;
		winGraph2[3] = 2*population.getNumAnimals(WorldConstants.SCAV_ID);
		pltG2 = SubPlot.getPlotAt(p, 0.425f, 0.4f, 0.6875f, 0.6f, winGraph2);
		t2 = new TimeGraph(p, pltG2, p.color(255, 0, 0), population.getNumAnimals(WorldConstants.SCAV_ID));
		
		if(!WorldConstants.DO_HIBERNATION) return;
		winGraph3[0] = timer;
		winGraph3[1] = timer + timeDuration;
		winGraph3[3] = 2*population.getMean(1);
		pltG3 = SubPlot.getPlotAt(p, 0.425f, 0.7f, 0.6875f, 0.9f, winGraph3);
		t3 = new TimeGraph(p, pltG3, p.color(255, 0, 0), population.getMean(1));
		
		winGraph4[0] = timer;
		winGraph4[1] = timer + timeDuration;
		winGraph4[3] = 2*population.getMeanWeights()[1];
		pltG4 = SubPlot.getPlotAt(p, 0.7125f, 0.1f, 0.975f, 0.3f, winGraph4);
		t4 = new TimeGraph(p, pltG4, p.color(255, 0, 0), population.getMeanWeights()[1]);
		
		winGraph5[0] = timer;
		winGraph5[1] = timer + timeDuration;
		winGraph5[3] = 2*population.getSdWeights()[1];
		pltG5 = SubPlot.getPlotAt(p, 0.7125f, 0.4f, 0.975f, 0.6f, winGraph5);
		t5 = new TimeGraph(p, pltG5, p.color(255, 0, 0), population.getSdWeights()[1]);
		
		winGraph6[0] = timer;
		winGraph6[1] = timer + timeDuration;
		winGraph6[3] = 2*population.getDesvioPadrao(1);
		pltG6 = SubPlot.getPlotAt(p, 0.7125f, 0.7f, 0.975f, 0.9f, winGraph6);
		t6 = new TimeGraph(p, pltG6, p.color(255, 0, 0), population.getDesvioPadrao(1));
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
		if(p.key == 'r') {
			setup(p);
		}
		
	}

}
