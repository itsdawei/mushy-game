package Function;

/**
 * @author David
 */
public enum Move {
    Attack(0),
    Summon(0),
    Switch(1),
    Ultimate(0);

    private final int priority;

    Move(int priority){
        this.priority = priority;
    }

    public static Move handlePriority(Move m1, Move m2) {
        if(m1 == null || m2 == null){
            return m1 == null ? m2: m1;
        }

        if (m1.priority > m2.priority) {
            return m1;
        } else if(m1.priority < m2.priority){
            return m2;
        } else {
            return null;
        }

    }
}
