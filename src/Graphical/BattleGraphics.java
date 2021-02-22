package Graphical;

import Function.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author David
 */
public class BattleGraphics extends JPanel{
    private JLabel mon1Img, mon2Img, mon1Label, mon2Label;
    private CardLayout cl;
    private JPanel subPanelsCont, movePanel, attackPanel, switchPanel;
    private JButton[] movebtns, attackbtns, switchbtns;
    private JPanel hpDisplayPanel, spiritPanel, mon1Panel, mon2Panel;
    private JLabel hp1, hp2;

    public BattleGraphics(){
        super();
        initComponents();
    }

    private void initComponents(){
        this.setLayout(new BorderLayout(10, 10));
        this.setPreferredSize(new Dimension(400, 400));

        // sub-panel for moves; initialized
        initMovesPanel();

        // sub-panel for attacks; yet to be initialized
        attackPanel = new JPanel();
        attackPanel.setLayout(new FlowLayout());

        // sub-panel for switching; yet to be initialized
        switchPanel = new JPanel();
        switchPanel.setLayout(new FlowLayout());

        // construct cardLayout for subPanels
        cl = new CardLayout();
        subPanelsCont = new JPanel(cl);
        subPanelsCont.add(movePanel, "move");
        subPanelsCont.add(attackPanel, "attack");
        subPanelsCont.add(switchPanel, "switch");
        cl.show(subPanelsCont, "move");

        // construct HpDisplayPanel
        hpDisplayPanel = new JPanel();
        hpDisplayPanel.setLayout(new FlowLayout());
        hp1 = new JLabel();
        hp2 = new JLabel();
        hpDisplayPanel.add(hp1);
        hpDisplayPanel.add(hp2);
        hp1.setAlignmentX(FlowLayout.LEADING);
        hp2.setAlignmentX(FlowLayout.TRAILING);

        // spirit panel init
        spiritPanel = new JPanel();
        spiritPanel.setLayout(new BoxLayout(spiritPanel, BoxLayout.PAGE_AXIS));

        // monster 1
        mon1Img = new JLabel();
        mon1Img.setPreferredSize(new Dimension(160, 160));

        //mon1 panel
        mon1Panel = new JPanel();
        mon1Panel.setLayout(new BoxLayout(mon1Panel, BoxLayout.Y_AXIS));
        mon1Panel.add(mon1Img);

        // monster 2
        mon2Img = new JLabel();
        mon2Img.setPreferredSize(new Dimension(160, 160));

        //mon2 panel
        mon2Panel = new JPanel();
        mon2Panel.setLayout(new BoxLayout(mon2Panel, BoxLayout.Y_AXIS));
        mon2Panel.add(mon2Img);


        this.add(BorderLayout.LINE_START, mon1Img);
        this.add(BorderLayout.LINE_END, mon2Img);

        // moves
        this.add(BorderLayout.PAGE_END, subPanelsCont);
        // hp display
        this.add(BorderLayout.PAGE_START, hpDisplayPanel);
        // spirit panel
        this.add(BorderLayout.CENTER, spiritPanel);
    }

    private void initMovesPanel() {
        movePanel = new JPanel();
        movePanel.setLayout(new FlowLayout());
        movebtns = new JButton[Move.values().length];
        for (int i = 0; i < movebtns.length; i++) {
            String text = Move.values()[i].name();
            movebtns[i] = new JButton(text);
            movePanel.add(movebtns[i]);
        }
    }

    public void initMoveListeners(ActionListener a){
        for(JButton btn: movebtns) {
            btn.addActionListener(a);
        }
    }

    /**
     * initialize and pull up the attack panel, which contains a btn for each attack the current monster has.
     * @param attacks the attacks that need to be displayed
     * @param a
     */
    public void setAttacksPanel(Attack[] attacks, ActionListener a){
        attackbtns = new JButton[attacks.length];
        attackPanel.removeAll();
        for (int i = 0; i < attacks.length; i++) {
            String text = attacks[i].toString();
            attackbtns[i] = new JButton(text);
            attackPanel.add(attackbtns[i]);

            attackbtns[i].addActionListener(a);
            attackbtns[i].setActionCommand(i + "");
        }
        cl.show(subPanelsCont, "attack");
    }

    /**
     * initialize and pull up the switch panel, which contains a btn for each monster in the user's party
     * @param party
     * @param a
     */
    public void setSwitchPanel(Party party, ActionListener a){
        switchbtns = new JButton[party.size()];
        for (int i = 0; i < switchbtns.length; i++) {
            if(!party.getMonsterAt(i).isDead()){
                String text = party.getMonsterAt(i).getName();
                switchbtns[i] = new JButton(text);
                switchPanel.add(switchbtns[i]);

                switchbtns[i].addActionListener(a);
                switchbtns[i].setActionCommand(i + "");
            }
        }
        cl.show(subPanelsCont, "switch");
    }

    public void setSpiritPanel(ArrayList<Spirit> spirits) {
        spiritPanel.removeAll();
        for (Spirit spirit: spirits) {
            spiritPanel.add(new JLabel(spirit.getName()));
        }
        repaint();
    }

    public void updateMonsters(Monster mon1, Monster mon2){
        BufferedImage mon1Img = mon1.getBufferedImage();
        BufferedImage mon2Img = mon2.getBufferedImage();

        Image dimg1 = mon1Img.getScaledInstance(this.mon1Img.getWidth(), this.mon1Img.getHeight(), Image.SCALE_SMOOTH);
        Image dimg2 = mon2Img.getScaledInstance(this.mon2Img.getWidth(), this.mon2Img.getHeight(), Image.SCALE_SMOOTH);

        this.mon1Img.setIcon(new ImageIcon(dimg1));
        this.mon2Img.setIcon(new ImageIcon(dimg2));

        hp1.setText(mon1.getHP() + "/" + mon1.getMaxHP());
        hp2.setText(mon2.getHP() + "/" + mon2.getMaxHP());

//        mon1Label.setText(mon1.getName());
//        mon2Label.setText(mon2.getName());
    }

    public void setMovePanel(){
        cl.show(subPanelsCont, "move");
    }

}