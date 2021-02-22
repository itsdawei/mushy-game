package Graphical;

import Function.Monster;
import Function.MonsterInfo;
import Function.Party;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * @author David
 * graphics for the selection screen
 */
public class SelectionGraphics extends JPanel {
    // all panels
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel eastPanel;
    private JPanel westPanel;
    private JPanel centerPanel;

    private JButton nameText;

    // left and right button
    // clickEvent: display next mushy or last mushy
    private JButton lBtn, rBtn;

    // display the three mushy in the party currently.
    // clickEvent: delete mushy from selection
    private JButton[] partyBtns = new JButton[3];
    private JLabel partyText;

    // ready
    private JButton readyBtn = new JButton();

    // mushy display; selects mushy if clicked
    private JButton mushDisplayBtn;

    public SelectionGraphics(){
        super(new BorderLayout());
        initComponents();
    }

    /**
     * Initialize Components
     */
    private void initComponents(){
        // set size for main panel
        this.setLayout(new BorderLayout(20, 20));
        this.setPreferredSize(new Dimension(400, 400));

        // name text
        nameText = new JButton();
        this.add( BorderLayout.PAGE_END, nameText);

        // lBtn and rBtn
        lBtn = new JButton("prev");
        rBtn = new JButton("next");

        // mushy display
        mushDisplayBtn = new JButton();
        mushDisplayBtn.setActionCommand("pick");

        // party display
        partyText = new JLabel();
        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        northPanel.add(partyText);
        for (int i = 0; i < partyBtns.length; i++) { // initialize party btn
            partyBtns[i] = new JButton();
            partyBtns[i].setActionCommand("delete " + i);
            northPanel.add(partyBtns[i]);
        }

        // ready button
        readyBtn = new JButton("ready!");
        readyBtn.setActionCommand("ready");
        readyBtn.setPreferredSize(new Dimension(100, 50));


        this.add(BorderLayout.PAGE_START, northPanel);
        this.add(BorderLayout.PAGE_END, readyBtn);
        this.add(BorderLayout.LINE_START, lBtn);
        this.add(BorderLayout.LINE_END, rBtn);
        this.add(BorderLayout.CENTER, mushDisplayBtn);
    }

    public void initListeners(ActionListener a){
        lBtn.addActionListener(a);
        rBtn.addActionListener(a);

        mushDisplayBtn.addActionListener(a);

        for (JButton partyBtn : partyBtns) {
            partyBtn.addActionListener(a);
        }

        readyBtn.addActionListener(a);
    }

    public void setMushDisplay(MonsterInfo monsterInfo){
//        mushDisplayBtn.setText(monsterInfo.name());

        BufferedImage monImg = monsterInfo.getBufferedImage();

        Image dimg1 = monImg.getScaledInstance(this.mushDisplayBtn.getWidth(), this.mushDisplayBtn.getHeight(),
                Image.SCALE_SMOOTH);

        this.mushDisplayBtn.setIcon(new ImageIcon(dimg1));
    }

    public void updateParty(Party party){
        Monster[] Monster = party.getParty();
        for (int i = 0; i < Party.MAX_SIZE(); i++) {
            if(Monster[i] != null){ // there is mushy at i
                //mushy.setIcon(monster.getIcon());
                partyBtns[i].setText(Monster[i].getName());
            } else { // there is no mushy at i
                partyBtns[i].setText("");
            }
            northPanel.add(partyBtns[i]);
        }
    }

    public void setPartyText(String s){
        partyText.setText(s);
    }
}
