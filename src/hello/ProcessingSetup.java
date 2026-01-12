package hello;

import ca.TestCA;
import ecosystem.TestEcosystemApp;
import ecosystem.TestTerrainApp;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet{

	private static IProcessingApp app;
	private int lastUpdateTime;
	
	@Override
	public void settings() {
		size(800,600);
	}
	
	@Override
	public void setup() {
		app.setup(this);
		lastUpdateTime = millis();
	}
	
	@Override
	public void draw() {
		int now = millis();
		float dt = (now - lastUpdateTime)/1000f;
		lastUpdateTime = now;
		app.draw(this, dt);
	}

	@Override
	public void mousePressed() {
		app.mousePressed(this);
	}
	
	
	public void mouseReleased() {
		app.mouseReleased(this);
	}
	
	public void mouseDragged() {
		app.mouseDragged(this);
	}
	
	
	@Override
	public void keyPressed() {
		app.keyPressed(this);
	}
	
	public static void main(String[] args) {
		app = new TestEcosystemApp();
		PApplet.main(ProcessingSetup.class);
	}
}
