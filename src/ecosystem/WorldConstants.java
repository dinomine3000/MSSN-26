package ecosystem;

public class WorldConstants {

    //WORLD
    public final static double[] WINDOW = {-10,10,-10,10};

    //TERRAIN
    public final static int NROWS = 45;
    public final static int NCOLS = 60;
    public enum PatchType{
        EMPTY, OBSTACLE, FERTILE, FOOD
    }
    public final static double[] PATCH_TYPE_PROB = {0.3f, 0.2, 0.3, 0.2f};
    public final static int NSTATES = PatchType.values().length;
    public static int[][] TERRAIN_COLORS = {
            {250, 200, 60}, {160, 30, 70}, {200, 200, 60}, {40, 200, 20}

    };
    
    public final static float[] REGENERATION_TIME = {2.f, 15.f}; // seconds

    public final static float PREY_SIZE = .2f;
    public final static float PREY_MASS = 1f;
    public final static int INI_PREY_POPULATION = 50;
    public final static float INI_PREY_ENERGY = 10f;
    public final static float ENERGY_FROM_PLANT = 4f;
    public final static float PREY_ENERGY_TO_REPRODUCE = 25f;
    public static final int[] PREY_COLOR = {80, 100, 220};
//
//    public final static float PREDATOR_SIZE = .12f;
//    public final static float PREDATOR_MASS = 1.1f;
//    public final static int INI_PREDATOR_POPULATION = 10;
//    public final static float INI_PREDATOR_ENERGY = 60f;
//    public final static float ENERGY_FROM_PREY = 25f;
//    public final static float PREDATOR_ENERGY_TO_REPRODUCE = 80f;
//
//    public final static float APEX_SIZE = .20f;
//    public final static float APEX_MASS = 1.2f;
//    public final static int INI_APEX_POPULATION = 4;
//    public final static float INI_APEX_ENERGY = 60f;
//    public final static float APEX_ENERGY_FROM_PREDATOR = 10f;
//    public final static float APEX_ENERGY_FROM_PREY = 3f;
//    public final static float APEX_ENERGY_TO_REPRODUCE = 200f;
}
