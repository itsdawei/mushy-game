package Function;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author David
 */
public enum MonsterInfo {

    FireMonkey(1, Type.Fire, 150 ,115, new Attack[]{Attack.AfterEatingHuoGuo, Attack.RockThrow,
            Attack.Spit}, "src\\sprites\\Fire Monkey.jpg",
            new Type[]{Type.Fire, Type.Water, Type.Wind}),

    SlippySlappy(1, Type.Water, 120 ,82, new Attack[]{Attack.HugeSpit, Attack.WaterEngine,
            Attack.Imposter}, "src\\sprites\\Slippy Slappy.jpg",
            new Type[]{Type.Water, Type.Ice, Type.Rock}),

    GrassySassy(1, Type.Grass, 120 ,97, new Attack[]{Attack.GrassWhip, Attack.WaterIsMyFood,
            Attack.RockThrow}, "src\\sprites\\Grassy Sassy.jpg",
            new Type[]{Type.Grass, Type.Water, Type.Rock}),

    Staticky(1, Type.Electric, 120 ,96, new Attack[]{Attack.ZapYoMaMa, Attack.ElectricBurns,
            Attack.RockThrow}, "src\\sprites\\Staticky.jpg",
            new Type[]{Type.Electric, Type.Fire, Type.Mech}),

    LameRock(1, Type.Rock, 200 ,100, new Attack[]{Attack.RockThrow, Attack.RockThrow, Attack.RockThrow},
            "src\\sprites\\Lame Rock.jpg",
            new Type[]{Type.Rock, Type.Rock, Type.Rock}),

    Magnet(1, Type.Magnetic, 130 ,30, new Attack[]{Attack.ReversePolarity, Attack.RockThrow,
            Attack.GrassWhip}, "src\\sprites\\Magnet.jpg",
            new Type[]{Type.Magnetic, Type.Rock, Type.Mech}),

    AimlessBird(1, Type.Wind, 150 ,95, new Attack[]{Attack.WindHurts, Attack.KnightSlayer,
            Attack.WaterIsMyFood}, "src\\sprites\\Aimless Bird.jpg",
            new Type[]{Type.Wind, Type.Dragon, Type.Water}),

    IcyBoy(1, Type.Ice, 80 ,30, new Attack[]{Attack.FreezingColdWuhan, Attack.MeltedIce,
            Attack.RockThrow}, "src\\sprites\\Icy Boy.jpg",
            new Type[]{Type.Ice, Type.Water, Type.Rock}),

    DracoDog(1, Type.Dragon, 150, 120, new Attack[]{Attack.ComputaVirus, Attack.FlameThrower,
            Attack.Imposter}, "src\\sprites\\Draco Dog.jpg",
            new Type[]{Type.Dragon, Type.Fire, Type.Rock}),

    Mechanytar(1, Type.Mech, 120 ,94, new Attack[]{Attack.ComputaVirus, Attack.FlameThrower,
            Attack.Imposter}, "src\\sprites\\Mechanytar.jpg",
            new Type[]{Type.Mech, Type.Electric, Type.Electric});

    private int level;
    private final Type  type;
    private int HP;
    private final Attack[] attackSet;
    private final int speed;
    private final String imagePath;
    private final Type[] comboSet;
    private final int maxHP;

    MonsterInfo(int level, Type type, int HP, int speed, Attack[] attackSet, String imagePath, Type[] comboSet){
        this.level = level;
        this.type = type;
        this.speed = speed;
        this.HP = HP;
        this.attackSet = attackSet;
        this.imagePath = imagePath;
        this.comboSet = comboSet;
        this.maxHP = HP;
    }

    public static Monster randomMonster(){
        return values()[(int) (Math.random() * values().length)].getInstance();
    }

    public Monster getInstance(){
        return new Monster(this);
    }

    public int getMaxHP() {
        return maxHP;
    }

    public Attack[] getAttackSet() {
        return attackSet;
    }

    public BufferedImage getBufferedImage() {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (Exception e){
            e.getStackTrace();
            return null;
        }
    }

    public int getHP() {
        return HP;
    }

    public int getLevel() {
        return level;
    }

    public int getSpeed() {
        return speed;
    }

    public Type getType() {
        return type;
    }

    public Type[] getComboSet() {
        return comboSet;
    }
}
