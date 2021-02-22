package Graphical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author David
 */
public class MenuGraphics extends JPanel{
    private JButton a1PlayerButton;
    private JButton a2PlayerButton;
    private JLabel menuTitleText;
    private JButton exitButton;

    public MenuGraphics() {
        super();
        initComponents();
    }

    private void initComponents(){
        // setup
        setLayout(new GridLayout(3, 1));

        // title
        menuTitleText = new JLabel();
        menuTitleText.setHorizontalAlignment(2);
        menuTitleText.setText("AP CSA RPG");

        // p1 btn
        a1PlayerButton = new JButton();
        a1PlayerButton.setText("1 Player");
        add(a1PlayerButton);

        // p2 btn
        a2PlayerButton = new JButton();
        a2PlayerButton.setText("2 Player");
        add(a2PlayerButton);

        // exit btn
        exitButton = new JButton();
        exitButton.setText("Exit");
        add(exitButton);

    }

    public void initListeners(ActionListener a) {
        a1PlayerButton.addActionListener(a);
        a2PlayerButton.addActionListener(a);
        exitButton.addActionListener(a);
    }
}
