package Function;

/**
 * @author David
 * Attacks entry by Andrew
 */
public enum Attack {

    AfterEatingHuoGuo(30,Type.Fire),
    ElectricBurns(22, Type.Fire),
    ReversePolarity(22,Type.Magnetic),
    Spit(22, Type.Water),
    HugeSpit(30, Type.Water),
    MeltedIce(22, Type.Water),
    WaterIsMyFood(22, Type.Water),
    GrassWhip(30, Type.Grass),
    RockThrow(22, Type.Rock),
    WaterEngine(22, Type.Electric),
    ZapYoMaMa(30,Type.Electric),
    Imposter(22, Type.Ice),
    FreezingColdWuhan(30, Type.Ice),
    WindHurts(30, Type.Wind),
    KnightSlayer(22, Type.Dragon),
    ComputaVirus(30, Type.Mech),
    FlameThrower(22, Type.Fire);

    public static final Attack[] ATTACKS = Attack.values();
    public static final int SIZE = ATTACKS.length;

    private final int dmg;
    private final Type type;
    private final int priority;

    Attack(int dmg, Type type){
        this(dmg, type, 0);
    }

    Attack(int dmg, Type type, int priority){
        this.dmg = dmg;
        this.type = type;
        this.priority = priority;
    }

    public int getDmg(){
        return dmg;
    }

    public Type getType(){
        return type;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * Converts the name of the num to a more readable String
     * ShakeTheGround -> "Shake The Ground"
     * @return more readable String
     */
    @Override
    public String toString() {
        String a = name() + "";
        String b = a.substring(0, 1);
        for (int i = 1; i < a.length(); i++) {
            if(Character.isUpperCase(a.charAt(i))){
                b += " ";
            }
            b += a.substring(i, i+1);
        }
        return b;
    }
}
