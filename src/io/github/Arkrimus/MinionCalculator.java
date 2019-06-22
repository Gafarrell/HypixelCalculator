package io.github.Arkrimus;

import io.github.Arkrimus.util.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MinionCalculator extends JFrame implements ActionListener{
    enum MinionType {
        WHEAT(15,15,13,13,11,11,10,10,9,9,8),
        CARROT(20,20,18,18,16,16,14,14,12,12,10),
        POTATO(20,20,18,18,14,14,12,12,10,10),
        PUMPKIN(40,40,37,37,34,34,30,30,25,25,20),
        MELON(30,30,28,28, 26,26,23,23,20,20,16),
        MUSHROOM(30,30,28,28,26,26,23,23,20,20,16),
        COCOA_BEAN(27,27,25,25,23,23,21,21,18,18,15),
        CACTUS(27,27,25,25,23,23,21,21,18,18,15),
        SUGAR_CANE(27,27,25,25,23,23,21,21,18,18,15),
        COW(26,26,24,24,22,22, 20,20,17,17,13),
        PIG(26,26,24,24,22,22,20,20,17,17,13),
        CHICKEN(26,26,24,24,22,22,20,20,17,17,13),
        SHEEP(24,24,22,22,20,20,18,18,16,16,12),
        RABBIT(26,26,24,24,22,22,20,20,17,17,13),
        NETHER_WART(50,50,47,47, 44,44,41,41, 38,38,32),
        COBBLESTONE(14,14,12,12,10,10,9,9,8,8,7),
        COAL(15,15,13,13,12,12,10,10,9,9,7),
        IRON(17,17,15,15,14,14,12,12,10,10,8),
        GOLD(22,22,20,20,18,18,16,16,14,14,11),
        DIAMOND(29,29,27,27,25,25,22,22,19,19,15),
        LAPIS_LAZULI(29,29,27,27,25,25,23,23,21,21,18),
        EMERALD(28,28,26,26,24,24,21,21,18,18,14),
        REDSTONE(29,29,27,27,25,25,23,23,21,21,18),
        QUARTZ(25,25,23,23,21,21,19,19,16,16,13),
        OBSIDIAN(45,45,42,42,39,39,35,35,30,30,24),
        GLOWSTONE(25,25,23,23,21,21,19,19,16,16,13),
        GRAVEL(24,26,26,24,24,22,22,19,19,16,16,13),
        SAND(26,26,24,24,22,22,19,19,16,16,13),
        ZOMBIE(26,26,24,24,22,22,20,20,17,17,13),
        SKELETON(26,26,24,24,22,22,20,20,17,17,13),
        SPIDER(26,26,24,24,22,22,20,20,17,17,13),
        CREEPER(27,27,25,25,23,23,21,21,18,18,14),
        ENDERMAN(32,32,30,30,28,28,25,25,22,22,18),
        SLIME(26,26,24,24,22,22,19,19,16,16,12),
        BLAZE(30,30,28,28,26,26,23,23,19,19,15),
        MAGMA_CUBE(32,32,30,30,28,28,25,25,22,22,18),
        CLAY(26,26,24,24,22,22,19,19,16,16,13),
        TREE(48,48,45,45,42,42,38,38,33,33,27);

        private int[] timePerAction;

        MinionType(int... timePerLevel){
            this.timePerAction = timePerLevel;

        }

        int getTimeAtLevel(int level){return timePerAction[level];}
    }

    private JComboBox minions;
    private ArrayList<JSpinner> minionLevels = new ArrayList<>();
    private JTextField desiredAmt;
    private JButton calcButton = new JButton("Calculate"), resetButton = new JButton("Reset");

    private MinionCalculator(){
        super("Hypixel Minion Calculator");
        calcButton.addActionListener(this);
        resetButton.addActionListener(this);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout());
        temp.add(new JLabel("Minion Type: "));
        getContentPane().setPreferredSize(new Dimension(300,425));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        String[] minionTypes = {"Wheat", "Carrot", "Potato", "Pumpkin", "Melon", "Mushroom", "Cocoa Bean", "Cactus",
                "Sugar Cane", "Cow", "Pig", "Chicken", "Sheep", "Rabbit", "Nether Wart", "Cobblestone", "Coal", "Iron", "Gold", "Diamond",
                "Lapis Lazuli", "Emerald", "Redstone", "Quartz", "Obsidian", "Glowstone", "Gravel", "Sand", "Zombie", "Skeleton", "Spider",
                "Creeper", "Enderman", "Slime", "Blaze", "Magma Cube", "Clay", "Oak", "Spruce", "Birch", "Dark Oak", "Acacia", "Jungle"};

        minions =  new JComboBox(minionTypes);
        minions.setSelectedIndex(0);
        minions.setEditable(false);
        temp.add(minions);
        add(temp);

        temp = new JPanel();
        temp.setLayout(new SpringLayout());
        for (int i = 1; i <= 11; i++) {
            minionLevels.add(new JSpinner(new SpinnerNumberModel(0,0,25,1)));
            temp.add(new JLabel("Lvl " + i + " Amount: "));
            temp.add(minionLevels.get(i-1));
        }
        SpringUtilities.makeCompactGrid(temp, 11,2,0,15,3,3);
        add(temp);

        temp = new JPanel();
        temp.setLayout(new FlowLayout());
        temp.add(new JLabel("Desired Amount of items: "));
        desiredAmt = new JTextField(10);
        desiredAmt.setHorizontalAlignment(JTextField.RIGHT);
        temp.add(desiredAmt);
        add(temp);

        temp = new JPanel();
        temp.setLayout(new FlowLayout());
        temp.add(calcButton);
        temp.add(resetButton);
        add(temp);

        setVisible(true);
        pack();
    }

    public static void main(String[] args){
        new MinionCalculator();
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        if (evt.getSource() == calcButton) calculate();
        if (evt.getSource() == resetButton) reset();
    }

    private void calculate(){
        MinionType type = MinionType.valueOf(((String) minions.getSelectedItem()).replace(" ", "_").toUpperCase());
        int totalSeconds, totalMins, totalHrs, totalDays;
        double desiredAmount;

        double itemsPerSecond = 0;
        for (int i = 0; i < 11; i++){
            if ((int) minionLevels.get(i).getValue() < 1) continue;
            switch (type){
                case GLOWSTONE:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*4) / (double) (type.getTimeAtLevel(i)*2));
                    break;
                }
                case POTATO:
                case CARROT:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*3) / (double) (type.getTimeAtLevel(i)*2));
                    break;
                }
                case MELON:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*4) / (double) (type.getTimeAtLevel(i)));
                }
                case SUGAR_CANE:
                case CACTUS:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*3) / (double) (type.getTimeAtLevel(i)));
                    break;
                }
                default:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()) / (double) (type.getTimeAtLevel(i)*2));
                    break;
                }
            }
        }
        System.out.println("You get " + itemsPerSecond + " items per second");
        try{
            desiredAmount = Integer.parseInt(desiredAmt.getText().trim());
        } catch(NumberFormatException nfe){
            nfe.printStackTrace();
            return;
        }
        int time = (int) (desiredAmount/itemsPerSecond);
        System.out.println("It'll take " + time + " seconds.");
        totalSeconds = time%60;
        time -= time%60;
        totalMins = (time/60)%60;
        time -= totalMins*60;
        totalHrs = ((time/60)/60)%24;
        time -= totalHrs*60*60;
        totalDays = (((time/60)/60)/24);
        JOptionPane.showMessageDialog(null, "It will take about " + totalDays + " days, " + totalHrs + " hours, " + totalMins + " minutes, and " + totalSeconds + " seconds to reach " + desiredAmount + " to achieve your goal.");
    }

    private void reset(){
        for (JSpinner s : minionLevels){
            s.setValue(0);
        }
        desiredAmt.setText("");
    }
}
