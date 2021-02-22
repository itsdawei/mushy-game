package Function;

/**
 * @author Andrew
 */
public class TypeHandler {
    private final static double X = 0.4; // ineffective
    private final static double O = 1; // no effect
    private final static double S = 1.3; // effective

    private static final double[][] superEffectiveChart = new double[][]{ // this has to match with the order of types in the enum
            {X, X, S, O, O, O, O, S, O, O}, // fire
            {S, X, X, O, S, O, O, S, O, S}, // water
            {X, S, X, O, S, O, X, X, O, O}, // grass
            {O, O, O, X, X, S, O, O, S, S}, // electric
            {O, X, O, O, O, O, O, S, S, O}, // rock
            {O, O, O, S, O, S, O, X, X, S}, // magnet
            {S, S, S, O, X, O, O, O, X, X}, // wind
            {X, S, S, O, S, O, O, X, X, X}, // ice
            {S, S, S, O, O, O, O, O, O, O}, // dragon
            {O, O, O, S, O, O, O, O, S, S}, // mech
    };

    public static double checkEffectiveness(Type type1, Type type2){
        return superEffectiveChart[type1.ordinal()][type2.ordinal()];
    }

    //must have a way to know what group a type is
    public static int getGroup(Type type){
        Type[] types = Type.values();
        if(type.equals(types[0])||type.equals(types[8])){
            //heat group
            return 0;
        }else if(type.equals(types[1])|| type.equals(types[7])){
            //H2O group
            return 1;
        }else if(type.equals(types[3])||type.equals(types[5])||type.equals(types[9])){
            //electromagnetic group
            return 2;
        }else{
            //nature group
            return 3;
        }
    }
}
