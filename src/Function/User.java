package Function;

/**
 * @author Andrew
 * This class should be able to prompt the user for input on decisions
 */
public class User extends Player{

    public User(String name){
        super(name);
    }

    public User(){
        super("User");
    }

    public void setSwitch(int i) {
        nextSwitch = i;
    }

    public void setAttack(int i){
        nextAttack = party.getCurrMon().getAttackAt(i);
    }

    @Override
    public void replaceDead(){
        Monster[] partyParty = party.getParty();
        for (int i = 0; i < partyParty.length; i++) {
            if (!partyParty[i].isDead()) {
                party.setCurrMon(i);
            }
        }
    }
}
