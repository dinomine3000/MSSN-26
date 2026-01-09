package ca;
import processing.core.PApplet;
import hello.IProcessingApp;
import hello.SubPlot;

public class TestCA implements IProcessingApp {

    private int nrows = 30;
    private int ncols = 40;
    private int nStates = 4;
    private int radiusNeigh = 2;
    private boolean pause = false;
    private SubPlot plt;
    private double[] window = {0, 10, 0,10};
    private float[] viewport = {0.2f, 0.2f, 0.6f, 0.6f};
    CellularAutomata ca;

    @Override
    public void setup(PApplet p) {
    	plt = new SubPlot(window, viewport, p.width, p.height);
        ca = new MajorityCa(p, plt, nrows, ncols, nStates, radiusNeigh);
        //ca = new CellularAutomata(p, plt, nrows, ncols, nStates, radiusNeigh);
        ca.setRandomStates();
    }

    @Override
    public void draw(PApplet p, float dt)
    {
        ca.display(p);
    }

    @Override
    public void mousePressed(PApplet p) {
    	if(ca instanceof MajorityCa mca) mca.majorityRule();
    }

    public void keyPressed(PApplet p)
    {
        //se pressionar a tecla 'r' da reset ao jogo
        if (p.key=='r'  || p.key=='R')
            setup(p);

        //se pressionar a tecla do espaço pausa o jogo
        if (p.key== ' ') {
            pause = !pause;
            if (pause)
                System.out.println("O jogo está em pausa");
            else
                System.out.println("O jogo está a correr");
        }

        //se pressionar a tecla 'c' mata todas as cells
        if (p.key== 'c' || p.key=='C') {
            pause=true;
            ca.reset();
        }
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}