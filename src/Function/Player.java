package Function;


/**
 * @author Andrew
 * organized by David
 */
public abstract class Player {
    protected Move nextMove;
    protected Attack nextAttack;
    protected int nextSwitch;
    protected Party party;
    protected String name;

    public Player(String name){
        this.party = new Party();
        this.name = name;
    }

    public void resetMove(){nextMove = null;}

    public Move getNextMove() {
        return nextMove;
    }

    public Party getParty() {
        return party;
    }

    public Attack getNextAttack() {
        return nextAttack;
    }

    public int getNextSwitch() {
        return nextSwitch;
    }

    public String getName() {
        return name;
    }

    public void setNextMove(Move nextMove) {
        this.nextMove = nextMove;
    }

    public Monster getCurrMonster(){
        return this.getParty().getCurrMon();
    }

    public void setCurrMonster(int n){
        this.getParty().setCurrMon(n);
    }

    /**
     * @author Andrew
     * Edited by David to fit in User class
     */
    public void pickMonster(Monster monster) {
        party.add(monster);
    }

    public abstract void replaceDead();
}
