package io.github.Arkrimus;

import io.github.Arkrimus.util.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MinionCalculator extends JFrame implements ActionListener{
    //Minion types, load in the time per level of each minion.
    enum MinionType {
        WHEAT(15,15,13,13,11,11,10,10,9,9,8),           // Wheat minion assumes you are only trying to get wheat not seeds.
        CARROT(20,20,18,18,16,16,14,14,12,12,10),       // Carrot/potato minion assume that you get an average of 2.5 crops per harvest.
        POTATO(20,20,18,18,14,14,12,12,10,10),
        PUMPKIN(40,40,37,37,34,34,30,30,25,25,20),
        MELON(30,30,28,28, 26,26,23,23,20,20,16),       // Melon minion assumes that you get 5.5 melons per harvest.
        MUSHROOM(30,30,28,28,26,26,23,23,20,20,16),
        COCOA_BEAN(27,27,25,25,23,23,21,21,18,18,15),   // Cocoa beans assume you get 3 every harvest.
        CACTUS(27,27,25,25,23,23,21,21,18,18,15),       // Cactus/Sugar Cane minion assumes the cactus is fully grown.
        SUGAR_CANE(27,27,25,25,23,23,21,21,18,18,15),
        COW(26,26,24,24,22,22, 20,20,17,17,13),         // Cow minion assumes you are only trying to get leather.
        PIG(26,26,24,24,22,22,20,20,17,17,13),
        CHICKEN(26,26,24,24,22,22,20,20,17,17,13),      // Chicken minion assumes you are only trying to get feathers.
        SHEEP(24,24,22,22,20,20,18,18,16,16,12),        // Sheep minion assumes you are only trying to get mutton.
        RABBIT(26,26,24,24,22,22,20,20,17,17,13),       // Rabbit minion assumes you are only trying to get rabbit meat.
        NETHER_WART(50,50,47,47, 44,44,41,41, 38,38,32),// Nether wart minion assumes you get an average of 3 every harvest.
        COBBLESTONE(14,14,12,12,10,10,9,9,8,8,7),
        COAL(15,15,13,13,12,12,10,10,9,9,7),
        IRON(17,17,15,15,14,14,12,12,10,10,8),
        GOLD(22,22,20,20,18,18,16,16,14,14,11),
        DIAMOND(29,29,27,27,25,25,22,22,19,19,15),
        LAPIS_LAZULI(29,29,27,27,25,25,23,23,21,21,18), // Lapis minion assumes you get an average of 6.5 every ore.
        EMERALD(28,28,26,26,24,24,21,21,18,18,14),
        REDSTONE(29,29,27,27,25,25,23,23,21,21,18),     // Redstone minion assumes you get an average of 4.5 every ore.
        QUARTZ(25,25,23,23,21,21,19,19,16,16,13),       // Quartz minion assumes you get an average of 4 quartz every ore.
        OBSIDIAN(45,45,42,42,39,39,35,35,30,30,24),
        GLOWSTONE(25,25,23,23,21,21,19,19,16,16,13),
        GRAVEL(24,26,26,24,24,22,22,19,19,16,16,13),    // Gravel assumes you get only gravel with every harvest.
        SAND(26,26,24,24,22,22,19,19,16,16,13),
        ZOMBIE(26,26,24,24,22,22,20,20,17,17,13),
        SKELETON(26,26,24,24,22,22,20,20,17,17,13),
        SPIDER(26,26,24,24,22,22,20,20,17,17,13),       // Spiders don't account for spider eyes, and cave spider minions are not included in this program.
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

    //Items that we need to access later for calculations
    private JComboBox minions;
    private ArrayList<JSpinner> minionLevels = new ArrayList<>();
    private JTextField desiredAmt;
    private JButton calcButton = new JButton("Calculate"), resetButton = new JButton("Reset");


    private MinionCalculator(){
        super("Hypixel Minion Calculator");

        //Add action listeners to the buttons
        calcButton.addActionListener(this);
        resetButton.addActionListener(this);

        //Don't allow resizing because I don't know how to make it look good on bigger sizes lol
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the first combo box with all the minion types in it.
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

        //Use spring layout for the level spinners. SpringUtilities is an Oracle provided utility class.
        temp = new JPanel();
        temp.setLayout(new SpringLayout());
        for (int i = 1; i <= 11; i++) {
            minionLevels.add(new JSpinner(new SpinnerNumberModel(0,0,25,1)));
            temp.add(new JLabel("Lvl " + i + " Amount: "));
            temp.add(minionLevels.get(i-1));
        }
        SpringUtilities.makeCompactGrid(temp, 11,2,0,15,3,3);
        add(temp);

        //Last field for desired number of items.
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
        MinionType type;
        if (minions.getSelectedIndex() >= 37) type = MinionType.TREE;
        else type = MinionType.valueOf(((String) minions.getSelectedItem()).replace(" ", "_").toUpperCase());
        int totalSeconds, totalMins, totalHrs, totalDays;
        double desiredAmount;

        double itemsPerSecond = 0;
        for (int i = 0; i < 11; i++){
            if ((int) minionLevels.get(i).getValue() < 1) continue;
            switch (type){
                case LAPIS_LAZULI:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*6.5) / (double) (type.getTimeAtLevel(i)));
                    break;
                }
                case MELON:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*5.5) / (double) (type.getTimeAtLevel(i)));
                    break;
                }
                case REDSTONE:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*4.5) / (double) (type.getTimeAtLevel(i)));
                    break;
                }
                case QUARTZ:
                case GLOWSTONE:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*4) / (double) (type.getTimeAtLevel(i)*2));
                    break;
                }
                case NETHER_WART:
                case COCOA_BEAN:
                case SUGAR_CANE:
                case CACTUS:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*3) / (double) (type.getTimeAtLevel(i)));
                    break;
                }
                case POTATO:
                case CARROT:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()*2.5) / (double) (type.getTimeAtLevel(i)*2));
                    break;
                }
                default:{
                    itemsPerSecond += (((int) minionLevels.get(i).getValue()) / (double) (type.getTimeAtLevel(i)*2));
                    break;
                }
            }
        }

        try{
            desiredAmount = Integer.parseInt(desiredAmt.getText().trim());
        } catch(NumberFormatException nfe){
            return;
        }

        int time = (int) (desiredAmount/itemsPerSecond);//The total time in seconds.

        //Conversion process into an understandable time format.

        totalSeconds = time%60;                         //Time % 60 gets us any leftover seconds
        time -= time%60;                                //Subtract the left over seconds and you are left with a number divisible by 60
        totalMins = (time/60)%60;                       //Divide the time by 60 and mod it by 60 to get the leftover minutes.
        time -= totalMins*60;                           //Convert the leftover minutes into seconds and subtract it from the original time
        totalHrs = ((time/60)/60)%24;                   //Divide time by 60 twice and mod by 24 to get the leftover hours
        time -= totalHrs*60*60;                         //Convert the leftover hours into seconds subtract that amount from time.
        totalDays = (((time/60)/60)/24);                //Divide by 60, 60, and 24 to convert the remaining time into days.

        //Show a message dialog with the time info.
        JOptionPane.showMessageDialog(null, "It will take about " + totalDays + " days, " + totalHrs + " hours, " + totalMins + " minutes, and " + totalSeconds + " seconds to achieve your goal.");
    }

    //Reset the tool to original values.
    private void reset(){
        for (JSpinner s : minionLevels){
            s.setValue(0);
        }
        desiredAmt.setText("");
    }
}
