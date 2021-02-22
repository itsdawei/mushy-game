package Function;

import java.util.ArrayList;

/**
 * @author Andrew
 * Edited by David
 * This class represents a battle with two player, p1 and p2.
 * It has methods to build a moveque and to execute a moveque.
 */
public class Battle {
    private final Player p1;
    private final Player p2;
    private ArrayList<Spirit> spirits;

    //how lucky the attack is...
    private final int variability = 12;

    public Battle(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
        spirits = new ArrayList<Spirit>();
    }

    public void combo(Player p){
        int damage = 80 + (int)(Math.random()*variability);
        if(p.equals(p1)){
            p2.getCurrMonster().changeHP(damage);
        } else {
            p1.getCurrMonster().changeHP(damage);
        }

    }

    public boolean isComboReady(Player p){
        int countMatches =0;
        for(int i = 0; i < p.getCurrMonster().getComboSet().length; i++){
            for(Spirit j: spirits){
                if(j.getType().equals(p.getCurrMonster().getComboSet()[i])){
                    countMatches++;
                    break;
                    //must break because I don't want two spirits with the same type saying there are two matches...
                }
            }
        }
        return countMatches == 3;
    }

    /**
     * @author David
     * @param p player
     * @param n index in party
     */
    public void switchMonster(Player p, int n){
       p.setCurrMonster(n);
    }

    /**
     * @author David
     * Executes the moves. Get the move from currUser and executes. If currUser is user 1, then switch user and
     * execute again.
     */
    public boolean executeMove(Move move, Player currUser){
        switch (move) {
            case Switch:
                switchMonster(currUser, currUser.getNextSwitch());
                break;
            case Summon:
                Type type = currUser.getCurrMonster().getType();
                spirits.add(new Spirit(Spirit.SpiritInfo.valueOf(type.name())));
                spirits.add(new Spirit(Spirit.SpiritInfo.valueOf(type.name())));
                break;
            case Ultimate:
                if (isComboReady(currUser)) {
                    combo(currUser);
                } else {
                    return false;
                }
                break;
            case Attack:
                attack(currUser);
                break;
        }
        return true;
    }


    /**
     * @author Andrew
     * Edited by David
     * Evaluates priority. If both side is same priority, then compare speed.
     * @return faster player
     */
    public Player getFirstPlayer(){
        Move m1 = p1.getNextMove(); // player 1 next move
        Move m2 = p2.getNextMove(); // player 2 next move

        if(Move.handlePriority(m1, m2) == m1){
            return p1;
        } else if (Move.handlePriority(m1, m2) == m2){
            return p2;
        } else { // same priority -> compares speed
            Monster mon1 = p1.getCurrMonster();
            Monster mon2 = p2.getCurrMonster();
            if(Monster.handleSpeed(mon1, mon2) == mon1){
                return p1;
            } else {
                return p2;
            }
        }
    }

    /**
     * @author Andrew
     * Edited by David
     * Handles an attack action
     * @param p
     */
    public void attack(Player p){
        spirits.add(Spirit.generateSpirit(p.getNextAttack().getType()));
        if(p.equals(p1)){ // p1 attacks p2
            Attack attack = p1.getNextAttack();
            //finding matching spirit
            int spiritMatch = 0; //TODO replace spirit calculator
            for(Spirit i : spirits){
                if(i.getType().equals(attack.getType())){
                    spiritMatch++;
                }
            }
            int damage = (int) (attack.getDmg() *
                    TypeHandler.checkEffectiveness(attack.getType(), p2.getCurrMonster().getType())*
                    Spirit.calculateBuff(spirits, attack)) + (int)(Math.random()*variability);
            p2.getCurrMonster().changeHP(damage);
        } else if (p.equals(p2)){ // p2 attacks p1
            Attack attack = p2.getNextAttack();
            int spiritMatch = 0;
            for(Spirit i : spirits){
                if(i.getType().equals(attack.getType())){
                    spiritMatch++;
                }
            }
            int damage = (int) (attack.getDmg() *
                    TypeHandler.checkEffectiveness(attack.getType(), p1.getCurrMonster().getType())*
                    Spirit.calculateBuff(spirits, attack))+ (int)(Math.random()*variability);
            p1.getCurrMonster().changeHP(damage);
        }
    }

    public void endRound(){
        System.out.println(p1.getCurrMonster().getHP()+ ", " +p2.getCurrMonster().getHP());
        p1.resetMove();
        p2.resetMove();
        Spirit.decayAll(spirits);
    }

    public Player getWinner(){
        if(p1.getParty().remainingCount() == 0){
            return p2;
        } else if(p2.getParty().remainingCount() == 0){
            return p1;
        } else {
            return null;
        }
    }

    public ArrayList<Spirit> getSpirits() {
        return spirits;
    }
}
