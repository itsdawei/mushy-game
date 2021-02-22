import Function.*;

import java.beans.PropertyChangeSupport;

/**
 * @author David.
 */
public class Model {
    private final int MAX_PARTY_SIZE = 3;

    private Player user1;
    private Player user2;
    private Player currUser;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Panel currPanel;
    private int gameMode;
    private Battle battle;
    private Panel currSubPanel;

    private int currSelectionIndex;

    public PropertyChangeSupport getPcs() {
        return pcs;
    }

    public Model(){
        currPanel = Panel.menu;
        gameMode = 0;
        currSelectionIndex = 0;
        Party.setMaxSize(MAX_PARTY_SIZE);
    }

    /**
     * Change screen when called
     * @param currScreen
     */
    public void setCurrPanel(Panel currScreen) {
        if(currScreen == Panel.battle){
            pcs.firePropertyChange("battle", user1.getCurrMonster(), user2.getCurrMonster());
        }
        pcs.firePropertyChange("currScreen", this.currPanel, currScreen);
        this.currPanel = currScreen;
    }

    /**
     * sets the gamemode to 1 player or 2 player; initializes the battle.
     * @param n
     */
    public void setGameMode(int n) {
        this.gameMode = n;
        initializeBattle();
        setCurrPanel(Panel.selection);
    }

    private void initializeBattle(){
        user1 = new User();
        if(gameMode == 1){
            user2 = new RandomBot();
        } else if (gameMode == 2){
            user2 = new User();
        } else {
            throw new RuntimeException("gameMode undefined");
        }

        currUser = user1;
        battle = new Battle(user1, user2);
    }

    /**
     * Changes the current subPanel in battle screen.
     * @param panel
     */
    private void setCurrSubPanel(Panel panel) {
        switch (panel) {
            case attack -> pcs.firePropertyChange("Sub Panel", user1.getCurrMonster().getAttacks(), panel);
            case party -> pcs.firePropertyChange("Sub Panel", user1.getParty(), panel);
            case move -> pcs.firePropertyChange("Sub Panel", user1.getCurrMonster(), panel);
            default -> throw new RuntimeException("sub panel not found");
        }
        this.currSubPanel = panel;
    }

    /**
     * change the current monster on display
     * @param n
     */
    public void advanceSelectionIndex(int n){
        int c = currSelectionIndex + n;
        if(c >= 0 && c < MonsterInfo.values().length){
            currSelectionIndex = c;
            pcs.firePropertyChange("monster display", c, currSelection());
        }
    }

    /**
     * adds current monster to party; fires (new party size, new party)
     */
    public void pickMonster(){
        if(currUser.getParty().size() < MAX_PARTY_SIZE){
            currUser.getParty().add(currSelection().getInstance());
            pcs.firePropertyChange("party", currUser.getParty().size(), currUser.getParty());
        }
    }

    /**
     * removes monster at index i of party; fires (new party size, new party)
     */
    public void deleteMonster(int i){
        currUser.getParty().remove(i);
        pcs.firePropertyChange("party", currUser.getParty().size(), currUser.getParty());
    }

    private MonsterInfo currSelection(){
        return MonsterInfo.values()[currSelectionIndex];
    }

    /**
     * makes both sides have selected their mushy, and change to battle screen
     */
    public void selectionComplete(){
        if(currUser.getParty().size() == 3) {
            if (currUser == user2) { // end selection
                switchCurrUser();
                setCurrPanel(Panel.battle);
            } else { // user2 selects
                // TODO handle 2 player mode
                switchCurrUser();
                if(user2 instanceof RandomBot){
                    ((RandomBot) user2).setParty();
                }
                selectionComplete();
            }
        }
    }

    /**
     * switches to the next user
     * If the next user is a bot, call method to get bot decision.
     */
    private void switchCurrUser(){
        if(currUser.equals(user1)){
            currUser = user2;
        } else if(currUser.equals(user2)){
            currUser = user1;
        }

        if (currUser instanceof RandomBot) { // request move from bot, repeats until move is valid
            botTurn((RandomBot) currUser);
        }

        if(isAllMovesPicked()){ // if both side has picked their move.
            nextRound();
        }
    }

    /**
     * proceeds to next round
     */
    private void nextRound(){
        if(battle.getFirstPlayer().equals(user1)) {
            battle.executeMove(user1.getNextMove(), user1);
            if(!user2.getCurrMonster().isDead()){ // make sure the other mushy is alive
                battle.executeMove(user2.getNextMove(),user2);
            }
        } else {
            battle.executeMove(user2.getNextMove(), user2);
            if(!user1.getCurrMonster().isDead()) { // make sure the other mushy is alive
                battle.executeMove(user1.getNextMove(), user1);
            }
        }

        /*
        check is game is over
         */
        if(battle.getWinner() != null){
            pcs.firePropertyChange("user 1 winner", null, battle.getWinner());
            return;
        }

        if(user1.getCurrMonster().isDead()){
            handleDeaths(user1);
        }
        if(user2.getCurrMonster().isDead()){
            handleDeaths(user2);
        }

        report();

        // resets
        currUser = user1;
        battle.endRound();
        setCurrSubPanel(Panel.move);

        // update view class
        pcs.firePropertyChange("battle", user1.getCurrMonster(), user2.getCurrMonster());
        pcs.firePropertyChange("spirit", null, battle.getSpirits());
    }

    /**
     * report for testing
     */
    private void report() {
        System.out.println("p1 move: " + user1.getNextMove());
        System.out.println("p2 move: " + user2.getNextMove());
        if(user1.getNextMove() == Move.Attack){
            System.out.println("p1 attack: " + user1.getNextAttack());
        }
        if(user2.getNextMove() == Move.Attack){
            System.out.println("p2 attack: " + user2.getNextAttack());
        }
        if(user1.getNextMove() == Move.Switch){
            System.out.println("p1 switch: " + user1.getNextSwitch());
        }
        if(user2.getNextMove() == Move.Switch){
            System.out.println("p2 switch: " + user2.getNextSwitch());
        }
        System.out.println(battle.getSpirits().toString());
    }

    /**
     * automatically replace the dead mushy
     * TODO: allow user to replace the dead mushy
     */
    private void handleDeaths(Player user){
        if(user.getCurrMonster().isDead()){
            if(user instanceof User){
//                setCurrSubPanel(Panel.party);
                //TODO make the user select next mushy
                user.replaceDead();
            }
            if(user instanceof RandomBot){
                 user.replaceDead();
            }
//            battle.executeMove(user.getNextMove(), user);
        }
    }

    /**
     * handles the bot's turn.
     * TODO: implement for Bot interface
     * @param bot
     */
    private void botTurn(RandomBot bot){
        if(bot.getParty().size() != 0) {
            do {
                bot.getMove();
            } while (bot.getNextMove() == Move.Ultimate && !battle.isComboReady(bot)); // check if ult is valid
        } else {
            bot.setParty();
        }
    }

    /**
     * sets the next move for the user if the move is valid. Also handles switching the sub panels if the move
     * is Attack or switch.
     * @param move
     */
    public void setNextMove(Move move){
        if(move == Move.Ultimate && !battle.isComboReady(currUser)){
            System.out.println("ultimate not ready");
            return;
        }

        currUser.setNextMove(move);

        if(move == Move.Attack){
            setCurrSubPanel(Panel.attack);
        } else if(move == Move.Switch){
            setCurrSubPanel(Panel.party);
        } else {
            switchCurrUser();
        }
    }

    /**
     * currUser picks attack, and game switches to next user
     * @param i
     */
    public void pickNextAttack(int i){
        if(currUser instanceof User){
            ((User) currUser).setAttack(i);
        }
        switchCurrUser();
    }

    /**
     * currUser picks his switch, and game switches to next user.
     * @param i
     */
    public void pickSwitch(int i){
        if(currUser instanceof User){
            ((User) currUser).setSwitch(i);
        }
        switchCurrUser();
    }

    /**
     * returns true if all users have selected their moves, false otherwise.
     */
    private boolean isAllMovesPicked(){
        return user1.getNextMove() != null && user2.getNextMove() != null;
    }
}
