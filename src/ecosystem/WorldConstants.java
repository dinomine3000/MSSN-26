package ecosystem;

public class WorldConstants {

    //WORLD
    public final static double[] WINDOW = {-10,10,-10,10};

    //TERRAIN
    public final static int NROWS = 45;
    public final static int NCOLS = 60;
    public enum PatchType{
        EMPTY, OBSTACLE, FERTILE, FOOD, SPOILED
    }
    public final static double[] PATCH_TYPE_PROB = {0.25f, 0f, 0.2f, 0.25f, 0.3f};
    public final static int NSTATES = PatchType.values().length;
    public static int[][] TERRAIN_COLORS = {
            {250, 200, 60}, {160, 30, 70}, {200, 200, 60}, {40, 200, 20}, {217, 105, 245}
    };

    public final static float[] REGENERATION_TIME = {2.f, 5.f}; // seconds
    public final static float[] SPOILAGE_TIME = {5.f, 10.f}; // seconds

    public final static float PREY_SIZE = 0.3f;
    public final static float PREY_MASS = 1.f;
    public final static int INI_PREY_POPULATION = 20;
    public final static float INI_PREY_ENERGY = 10.f;
    public final static float ENERGY_FROM_PLANT = 5.f;
    public final static float PREY_ENERGY_TO_REPRODUCE = 25.f;
    public static final int[] PREY_COLOR = {80, 100, 220};
    
    public final static float SCAV_SIZE = 0.2f;
    public final static float SCAV_MASS = 0.5f;
    public final static int INI_SCAV_POPULATION = 20;
    public final static float INI_SCAV_ENERGY = 10.f;
    public final static float SCAV_ENERGY_FROM_PLANT = 5.f;
    public final static float SCAV_ENERGY_TO_REPRODUCE = 20.f;
    public static final int[] SCAV_COLOR = {200, 20, 20};

    public static final int PREY_ID = 1;
    public static final int SCAV_ID = 2;
    
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
