package hello;

import processing.core.PApplet;

public class Programa implements IProcessingApp {
	
	private int centerX, centerY, xx, yy;
	private int r = 100, g=0, b=210;
	//private IProcessingApp app;
	
	/*@Override
	public void settings() {
		size(800, 600);
	}*/
	
	@Override
	public void setup(PApplet p) {
		
		//bg = p.loadImage(null);
		centerX = 100; centerY = 500;
		p.strokeWeight(3);
		p.fill(0,255,255, 50);
		p.stroke(0,255,255);
		//this.circle(posX, 300, 100);
	}
	
	@Override
	public void draw(PApplet p, float dt) {
		
		xx = p.mouseX;
		yy = p.mouseY;
		
		
		p.fill(r, g, b);
		
		
		p.background(255, 172, 28);
		p.circle(xx,  yy,  30);
		p.circle(centerX + (xx-centerX)/10,  centerY + (yy-centerY)/10,  100);
		p.circle(centerX + (xx-centerX)/7,  centerY + (yy-centerY)/7,  100);
		p.circle(centerX + (xx-centerX)/5,  centerY 	+ (yy-centerY)/5,  100);
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
	
	/*public static void main(String[] args) {
		PApplet.main(Programa.class);
	}*/
}
