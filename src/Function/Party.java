package Function;

/**
 * Originally MonsterList
 * @author Andrew
 * Edited by David
 */
public class Party {
    private Monster[] party;
    private int currMon;
    private static int MAX_SIZE;
    private int size;

    public Party(){
        party = new Monster[MAX_SIZE];
        currMon = 0;
        size = 0;
    }

    /**
     * @author Andrew
     * presenting the monsters
     * @param i index
     */
    public Monster getMonsterAt(int i){
        return party[i];
    }

    /**
     * @author Andrew
     * Adds monster to party directly
     * @param m Monster
     */
    public void add(Monster m){
        for (int i = 0; i < MAX_SIZE; i++) {
            if(party[i] == null){
                party[i] = m;
                size++;
                return;
            }
        }
    }

    /**
     * @author David
     * set the index of the current monster
     * @param n int [0,2]
     */
    public void setCurrMon(int n){
        assert n >= 0 && n < party.length : "Invalid party index";

        currMon = n;
    }

    /**
     * @author David
     * @return current monster
     */
    public Monster getCurrMon() {
        return party[currMon];
    }

    /**
     * @author David
     * @return party
     */
    public Monster[] getParty() {
        return party;
    }

    /**
     * @author David
     */
    public int size(){
        return size;
    }

    /**
     * @author David
     * @return c remaining alive monsters in the party
     */
    public int remainingCount(){
        int c = 0;
        for (Monster m: party) {
            if(!m.isDead()){
                c++;
            }
        }
        return c;
    }

    public void remove(int i){
        if(party[i] != null){
            party[i] = null;
            size--;
        }
    }

    /**
     * @author David
     * @param maxSize
     */
    public static void setMaxSize(int maxSize) {
        MAX_SIZE = maxSize;
    }

    public static int MAX_SIZE() {
        return MAX_SIZE;
    }
}
