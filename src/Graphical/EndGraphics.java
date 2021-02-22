package Graphical;

import javax.swing.*;
import java.awt.*;

/**
 * @author David
 */
public class EndGraphics extends JPanel {
    private JLabel winnerLabel;
    private JLabel retryLabel;

    public EndGraphics(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
    }

    private void initComponents() {
        winnerLabel = new JLabel();
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        retryLabel = new JLabel("thanks for playing");
        retryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(winnerLabel);
        add(retryLabel);
    }

    public void setWinnerLabel(String winnerLabel){
        this.winnerLabel.setText(winnerLabel + " wins");
    }
}
