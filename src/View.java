import Function.*;
import Graphical.BattleGraphics;
import Graphical.EndGraphics;
import Graphical.MenuGraphics;
import Graphical.SelectionGraphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * @author David
 * handles all the display
 */
public class View {

    private final Controller ctrl;

    private JFrame mFrame;
    private JPanel container;
    private MenuGraphics menuView;
    private BattleGraphics battleView;
    private SelectionGraphics selectionView;
    private CardLayout cl;
    private EndGraphics endView;


    public View(){
        ctrl = new Controller();

        initView();
        initListener();
        initPropertyChangeListeners();
    }

    /**
     * add property change listeners to control
     */
    private void initPropertyChangeListeners() {
        /*
          Listens to when Model updates currPanel, and switches the display accordingly
         */
        ctrl.addPropertyChangeListener("currScreen", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                cl.show(container, evt.getNewValue().toString());
                System.out.println("Changing screen to " + evt.getNewValue().toString());
                mFrame.pack();
            }
        });

        /*
          Listens to when Model updates subPanel, and switches the display accordingly
         */
        ctrl.addPropertyChangeListener("Sub Panel", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getNewValue() == Panel.attack){
                    battleView.setAttacksPanel((Attack[]) evt.getOldValue(), new ActionListener(){ // battle attack btn listener
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ctrl.pickNextAttack(Integer.parseInt(e.getActionCommand()));
                        }
                    });
                } else if(evt.getNewValue() == Panel.party){
                    battleView.setSwitchPanel((Party) evt.getOldValue(), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ctrl.pickSwitch(Integer.parseInt(e.getActionCommand()));
                        }
                    });
                } else if (evt.getNewValue() == Panel.move) {
                    battleView.setMovePanel();
                } else {
                    throw new RuntimeException("sub panel not found");
                }
                mFrame.pack();
            }
        });

        /*
          Listen to when Model updates currMonIndex, and switches the display accordingly
         */
        ctrl.addPropertyChangeListener("monster display", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                selectionView.setMushDisplay((MonsterInfo) evt.getNewValue());
            }
        });

        /*
          Listens to the change of user's party; selection screen should then display it on the party display panel.
         */
        ctrl.addPropertyChangeListener("party", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                selectionView.updateParty((Party) evt.getNewValue());
            }
        });

        ctrl.addPropertyChangeListener("battle", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                battleView.updateMonsters((Monster) evt.getOldValue(), (Monster) evt.getNewValue());
            }
        });

        ctrl.addPropertyChangeListener("spirit", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                battleView.setSpiritPanel((ArrayList<Spirit>) evt.getNewValue());
            }
        });

        /*
        listens to when a player wins, newVal: Player winner, oldVal: null.
         */
        ctrl.addPropertyChangeListener("user 1 winner", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                endView.setWinnerLabel(((Player)evt.getNewValue()).getName());
                cl.show(container, Panel.end.name());
            }
        });
    }

    /**
     * Initializes the JFrame and all the JPanels under it.
     */
    private void initView(){
        mFrame = new JFrame("RPG");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        mFrame.setSize(screenWidth / 2, screenHeight / 2);
        mFrame.setLocationByPlatform(true);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setVisible(true);

        // init all windows
        this.menuView = new MenuGraphics();
        this.battleView = new BattleGraphics();
        this.selectionView = new SelectionGraphics();
        this.endView = new EndGraphics();

        // card layout for all the windows
        cl = new CardLayout();
        container = new JPanel(cl);
        container.add(menuView, Panel.menu.name());
        container.add(battleView, Panel.battle.name());
        container.add(selectionView, Panel.selection.name());
        container.add(endView, Panel.end.name());

        // set starting screen to menu
        mFrame.add(container);
        cl.show(container, "menu");
        mFrame.pack();
        
        selectionView.setMushDisplay(MonsterInfo.values()[0]);
    }

    /**
     * Initializes all the listeners for each JPanel.
     */
    private void initListener(){
        // menu listener
        menuView.initListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()) {
                    case "1 Player" -> ctrl.setGamemode(1);
                    case "2 Player" -> ctrl.setGamemode(2);
                    case "Exit" -> mFrame.dispose();
                    default -> throw new RuntimeException("unknown destination");
                }
            }
        });

        // battle move btn listener
        battleView.initMoveListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrl.setNextMove(Move.valueOf(e.getActionCommand()));
            }
        });

        // selection listener
        selectionView.initListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()){ // TODO better to use if statements
                    case "prev" -> ctrl.prev();
                    case "next" -> ctrl.next();
                    case "pick" -> ctrl.pickMonster();
                    case "delete 0" -> ctrl.deleteMonster(0);
                    case "delete 1" -> ctrl.deleteMonster(1);
                    case "delete 2" -> ctrl.deleteMonster(2);
                    case "ready" -> ctrl.selectionComplete();
                    default -> throw new RuntimeException("unregistered selection action");
                }
            }
        });

    }
}
