import Function.Move;

import java.beans.PropertyChangeListener;

/**
 * @author David
 */
public class Controller {

    private final Model model;

    public Controller(){
        model = new Model();
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        model.getPcs().addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        model.getPcs().removePropertyChangeListener(propertyName, listener);
    }

    public void setGamemode(int n){
        model.setGameMode(n);
    }

    public void prev(){
        model.advanceSelectionIndex(-1);
    }

    public void next(){
        model.advanceSelectionIndex(1);
    }

    public void pickMonster(){
        model.pickMonster();
    }

    public void deleteMonster(int i){
        model.deleteMonster(i);
    }

    public void selectionComplete(){
        model.selectionComplete();
    }

    public void setNextMove(Move move){
        model.setNextMove(move);
    }

    public void pickNextAttack(int i){
        model.pickNextAttack(i);
    }

    public void pickSwitch(int i){
        model.pickSwitch(i);
    }
}
