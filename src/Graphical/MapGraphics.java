package Graphical;

import javax.swing.*;

import java.awt.event.MouseListener;

/**
 * TODO not complete
 * @author David
 */
public class MapGraphics extends JPanel {
    private JLabel player;
    private JLabel enemy;

    public MapGraphics(){
        initComponent();
    }

    private void initComponent(){
        // setup
        setLayout(null);
        setSize(400, 400);

        // enemy TODO


    }

    public void initListeners(MouseListener m){
        addMouseListener(m);
    }

    public boolean checkEncounter(){
        return false;
    }

    public void initPlayer(Sprite sprite){
        player = new JLabel();
        ImageIcon playerIcon = new ImageIcon(sprite.getImage());
        player.setIcon(playerIcon);
        player.setEnabled(true);
        add(player);
    }

    public void updatePlayerPos(int x, int y){
        player.setLocation(x, y);
    }
}
