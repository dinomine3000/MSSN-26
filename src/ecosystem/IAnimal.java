package ecosystem;

import physics.Body;
import processing.core.PApplet;

import java.util.List;

public interface IAnimal {
    Animal reproduce(boolean mutate);
    void eat(Terrain terrain);
    void energy_consumption(float dt, Terrain terrain);
    boolean die();
}