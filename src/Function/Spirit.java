package Function;

import java.util.ArrayList;

/**
 * @author David
 */
public class Spirit{
    private static final int STARTING_LIFE_COUNT = 3;

    private Type type;
    private int lifeCount;
    private int group;
    private String name;
    private SpiritInfo info;

    private Spirit(Type type, int life, int group) {
        this.type = type;
        this.lifeCount = life;
        this.group = group;
        this.name = type.name();
    }

    public Spirit(SpiritInfo info){
        this(info.getType(), STARTING_LIFE_COUNT, info.getGroup());
        this.info = info;
    }

    public int getLifeCount() {
        return lifeCount;
    }

    public Type getType() {
        return type;
    }

    public void decay(){
        lifeCount--;
    }

    public int getGroup(){
        return group;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return info.toString();
    }

    /**
     * decay all spirits in arrayList and remove dead spirits.
     * @param spiritArrayList
     */
    public static void decayAll(ArrayList<Spirit> spiritArrayList){
        ArrayList<Spirit> deadSpirits = new ArrayList<Spirit>();
        for (Spirit spirit: spiritArrayList) {
            spirit.decay();
            if(spirit.getLifeCount() <= 0){
                deadSpirits.add(spirit);
            }
        }
        spiritArrayList.removeAll(deadSpirits);
    }

    /**
     * Generate spirit with Attack
     * @param attack
     * @return
     */
    public static Spirit generateSpirit(Attack attack){
        return generateSpirit(attack.getType());
    }

    /**
     * Generate spirit with Type
     * @param type
     * @return
     */
    public static Spirit generateSpirit(Type type){
        for (SpiritInfo s: SpiritInfo.values()) {
            if(type == s.getType()){
                return new Spirit(s);
            }
        }
        return null;
    }

    /**
     * get the Player p's current attack and type and add 0.2 to the double every time
     * it buffs and subtract 0.2 every time it debuffs.
     */

    public static double calculateBuff(ArrayList<Spirit> spirits, Attack attack){
        double multiplier = 1;
        double buff = 0.2;
        int currGroup = TypeHandler.getGroup(attack.getType());

        for(int i = 0; i < spirits.size() - 1; i++){
            if(currGroup == spirits.get(i).getGroup()){
                multiplier += buff;
            } else if(TypeHandler.checkEffectiveness(attack.getType(), spirits.get(i).getType()) == 0.4){
                multiplier -= buff;
            }
        }

        return multiplier;
    }

    enum SpiritInfo{
        Fire(Type.Fire, 0),
        Water(Type.Water, 1),
        Grass(Type.Grass, 3),
        Electric(Type.Electric, 2),
        Rock(Type.Rock, 3),
        Magnetic(Type.Magnetic, 2),
        Wind(Type.Wind, 3),
        Ice(Type.Ice, 1),
        Dragon(Type.Dragon, 0),
        Mech(Type.Mech, 2);

        private Type type;
        private int group;

        SpiritInfo(Type type, int group){
            this.type = type;
            this.group = group;
        }

        public Type getType() {
            return type;
        }

        public int getGroup(){
            return group;
        }
    }
}
