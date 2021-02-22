package Function;

/**
 * @author Andrew
 * Edited by David: extends Player
 * This class should simulate a user gameplay using all random.
 */
public class RandomBot extends Player{

    public RandomBot(){
        super("Random Strategy");
    }

    /**
     * @author Andrew
     * Edited by David
     * randomizes between switch, heal, dodge, and attack.
     * If chooses attack or switch, then randomizes again.
     */
    public Move getMove() {
        int randAction = (int)(Math.random()*4);
        if(randAction == 0){
            nextMove = Move.Ultimate;
        }else if(randAction == 1){
            nextMove = Move.Switch;
            nextSwitch = getSwitch();
        }else if(randAction == 2){
            nextMove = Move.Summon;
        }else {
            nextMove = Move.Attack;
            nextAttack = getAttack();
        }
        return nextMove;
    }

    /**
     * @author David
     * randomize an attack
     */
    public Attack getAttack() {
        int randAction = (int)(Math.random()*3);
        nextAttack = getCurrMonster().getAttackAt(randAction);
        return nextAttack;
    }

    /**
     * @author David
     * randomize a switch
     * @return
     */
    public int getSwitch() {
        int rand;
        do{
            rand = (int)(Math.random()*party.size());
        } while(getParty().getMonsterAt(rand).isDead());

        return rand;
    }

    public void setParty() {
        for(int i = 0; i < Party.MAX_SIZE(); i++){
            party.add(MonsterInfo.randomMonster());
        }
    }

    @Override
    public void replaceDead() {
        setCurrMonster(getSwitch());
    }

    public void setSwitch(int i){
        nextSwitch = i;
    }
}
