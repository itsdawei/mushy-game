package Function;

import java.awt.image.BufferedImage;

public class Monster {

    private int level;
    private final Type  type;
    private int HP;
    private final Attack[] attackSet;
    private final int speed;
    private final BufferedImage bufferedImage;
    private final Type[] comboSet;
    private final int maxHP;
    private final String name;

    private Monster(String name, int level, Type type, int HP, int speed, Attack[] attackSet, BufferedImage bufferedImage, Type[] comboSet){
        this.name = name;
        this.level = level;
        this.type = type;
        this.speed = speed;
        this.HP = HP;
        this.attackSet = attackSet;
        this.bufferedImage = bufferedImage;
        this.comboSet = comboSet;
        this.maxHP = HP;
    }

    public Monster(MonsterInfo info){
        this(info.name(), info.getLevel(),info.getType(),info.getHP(),info.getSpeed(),info.getAttackSet(),
                info.getBufferedImage(), info.getComboSet());
    }

    public boolean isDead() {
        return HP<=0;
    }

    public Type[] getComboSet() {
        return comboSet;
    }

    public int getLevel(){
        return level;
    }

    public Type getType(){
        return type;
    }

    public int getHP(){
        return HP;
    }

    public int getSpeed(){
        return speed;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int changeHP(int damage){
        HP -= damage;
        return getHP();
    }

    public Attack getAttackAt(int index){
        return attackSet[index];
    }

    public Attack[] getAttacks() {
        return attackSet;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public String getName() {
        return name;
    }

    public static Monster handleSpeed(Monster mon1, Monster mon2){
        if (mon1.speed > mon2.speed) {
            return mon1;
        } else if(mon1.speed < mon2.speed){
            return mon2;
        } else {
            if(Math.random() * 2 > 1){
                return mon1;
            } else {
                return mon2;
            }
        }
    }
}
