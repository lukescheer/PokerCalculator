/**
 * Created by Luke on 8/23/2017.
 */

//import com.sun.jna.platform.win32.Wdm;
import javafx.scene.layout.Background;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class calculator {
    ExampleTemplateMatching etm = new ExampleTemplateMatching();
    //static String[] deck = new String[52];
    static String deck = "2h3h4h5h6h7h8h9hthjhqhkhah2d3d4d5d6d7d8d9dtdjdqdkdad2s3s4s5s6s7s8s9stsjsqsksas2c3c4c5c6c7c8c9ctcjcqckcac";
    static String value1;
    static String suit1;
    static String value2;
    static String suit2;
    static String cardsinplay;
    static int numcardsleft;
    static String[] valuelist = {"a","2","3","4","5","6","7","8","9","t","j","q","k","a"};
    static String[] suitslist ={"h","d","s","c"};
    static int[] cardvalues = {100, 200, 300, 400, 500, 600, 700};
    static int[] flushcardvalues = {100, 200, 300, 400, 500, 600, 700};
    static int[] straightouts = {0,0};
    static int[] outs = {0,0,0,0,0,0,0,0,0};
    static String totalouts = "00000000000000000000000000000000000000000000000000000";
    static String board;
    static String holecards;
    static double pot;
    static double bet;
    static double potodds;

    private JTextField holecardsJTF;
    private JTextField boardJTF;

    private JTextField potJTF;
    private JTextField betJTF;

    private JButton getFlop;
    private JButton getTurn;
    private JButton getBet;
    private JButton calcJB;
    private JButton clearJB;
    private JButton moveJB;

    private JLabel Jpotodds = new JLabel("0.00%");

    private JLabel sft = new JLabel("0.00%");
    private JLabel sfr = new JLabel("0.00%");
    private JLabel fourt = new JLabel("0.00%");
    private JLabel fourr = new JLabel("0.00%");
    private JLabel fht = new JLabel("0.00%");
    private JLabel fhr = new JLabel("0.00%");
    private JLabel flusht = new JLabel("0.00%");
    private JLabel flushr = new JLabel("0.00%");
    private JLabel straightt = new JLabel("0.00%");
    private JLabel straightr = new JLabel("0.00%");
    private JLabel threet = new JLabel("0.00%");
    private JLabel threer = new JLabel("0.00%");
    private JLabel twot = new JLabel("0.00%");
    private JLabel twor = new JLabel("0.00%");
    private JLabel oneht = new JLabel("0.00%");
    private JLabel onehr = new JLabel("0.00%");
    private JLabel onelt = new JLabel("0.00%");
    private JLabel onelr = new JLabel("0.00%");

    private JLabel tfourt = new JLabel("0.00%");
    private JLabel tfourr = new JLabel("0.00%");
    private JLabel tfht = new JLabel("0.00%");
    private JLabel tfhr = new JLabel("0.00%");
    private JLabel tflusht = new JLabel("0.00%");
    private JLabel tflushr = new JLabel("0.00%");
    private JLabel tstraightt = new JLabel("0.00%");
    private JLabel tstraightr = new JLabel("0.00%");
    private JLabel tthreet = new JLabel("0.00%");
    private JLabel tthreer = new JLabel("0.00%");
    private JLabel ttwot = new JLabel("0.00%");
    private JLabel ttwor = new JLabel("0.00%");
    private JLabel toneht = new JLabel("0.00%");
    private JLabel tonehr = new JLabel("0.00%");
    private JLabel tonelt = new JLabel("0.00%");
    private JLabel tonelr = new JLabel("0.00%");



    static double turntotal = 0;
    static double rivertotal = 0;
    static double[][] probabilities = new double[9][2];
    static double[][] totals = new double[9][2];


    public calculator(){
        this.setUpGUInew();
        getTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                          holecards = holecardsJTF.getText();
                                          //    board = boardJTF.getText();
                                          //    pot =  Double.parseDouble(potJTF.getText());
                                          //    bet =  Double.parseDouble(betJTF.getText());   **/

                                          ExampleTemplateMatching autoget = new ExampleTemplateMatching();
                                          String[] cards = autoget.getTurn((TRUE));
                                          board = cards[0];
                                          holecards = cards[1];
                                          boardJTF.setText(board);
                                          holecardsJTF.setText(holecards);

                                          runCalculation();

                                      }
        });

        getFlop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                holecards = holecardsJTF.getText();
                //    board = boardJTF.getText();
                //    pot =  Double.parseDouble(potJTF.getText());
                //    bet =  Double.parseDouble(betJTF.getText());   **/

                ExampleTemplateMatching autoget= new ExampleTemplateMatching();
                String[] cards = autoget.getTurn(FALSE);
                board = cards[0];
                holecards = cards[1];

                if(holecards.substring(0,1).equals(holecards.substring(2,3))){
                    board = "000000";
                    holecards = "0000";
                    boardJTF.setText("");
                    holecardsJTF.setText("");
                }

                else if (board.substring(0,1).equals(board.substring(2,3)) || board.substring(0,1).equals(board.substring(4,5)) || board.substring(4,5).equals(board.substring(2,3))){
                    board = "000000";
                    holecards = "0000";
                    boardJTF.setText("");
                    holecardsJTF.setText("");
                }
                else{
                    boardJTF.setText(board);
                    holecardsJTF.setText(holecards);
                }


                runCalculation();

            }
        });

        getBet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ExampleTemplateMatching autoget= new ExampleTemplateMatching();
                autoget.takeScreenshot("potsize.png", 1531, 710, 1579, 725);
                String[] bets = autoget.getPot();
                String textpot = bets[0];
                String textbet = bets[1];
                textpot = textpot.replaceAll("\\G0", "");
                textbet = textbet.replaceAll("\\G0", "");

                potJTF.setText(textpot);
                betJTF.setText(textbet);

                pot = Integer.parseInt(textpot);
                bet = Integer.parseInt(textbet);

                potodds = 1/(pot / bet +1)*100;
                Jpotodds.setText(String.format("%.2f", potodds) + "%");

            }
        });
                calcJB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        holecards = holecardsJTF.getText();
                        board = boardJTF.getText();
                        pot =  Double.parseDouble(potJTF.getText());
                        bet =  Double.parseDouble(betJTF.getText());

                        runCalculation();
                    }
                });
                clearJB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        holecardsJTF.setText("");
                        boardJTF.setText("");
                    }
                });
        moveJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                etm.click();
            }
        });


    }

    private void runCalculation(){

        setupdeck();
        probabilities[0][board.length() / 2 - 3] = calcStraighFlush();
        probabilities[1][board.length() / 2 - 3] = calcFour();
        probabilities[2][board.length() / 2 - 3] = calcFullHouse();
        probabilities[3][board.length() / 2 - 3] = calcFlush();
        probabilities[4][board.length() / 2 - 3] = calcStraight();
        probabilities[5][board.length() / 2 - 3] = calcThree();
        probabilities[6][board.length() / 2 - 3] = calcTwo();
        probabilities[7][board.length() / 2 - 3] = calcOne();
        probabilities[8][board.length() / 2 - 3] = calcOne();
        double bflush = calcBackdoorFlush();
        double bstraight = calcBackStraight();

        if(board.length()/2 == 3){
            for(int i = 0; i < 9; i++){
                if(probabilities[i][0] != 0) {
                    double outs = probabilities[i][0];
                    probabilities[i][1] = (1 - (((47 - outs) / 47) * ((46 - outs) / 46))) * numcardsleft;
                }
            }
        }

        double total = 0;
        for(int j = 0; j < 2; j++) {
            for(int i = 0; i< 9; i++){
                if(j == 1 & i == 0){total = 0;}
                totals[i][j] = probabilities[i][j] + total;
                total += probabilities[i][j];
            }
        }


        System.out.println(probabilities[3][1]);
        System.out.println(bflush);
        if(probabilities[3][1] == 0 & bflush != 0) {
            System.out.println("Its going!!!!");
            probabilities[3][1] = bflush * numcardsleft;
            totals[3][1] += probabilities[3][1];
            for(int i = 4; i <9; i++){
                totals[i][1] += probabilities[3][1];
            }
        }
        if(probabilities[4][1] == 0 & bstraight != 0 & numcardsleft == 47){
            probabilities[4][1] = bstraight * numcardsleft;
            totals[4][1] += probabilities[4][1];
            for(int i = 5; i <9; i++){
                totals[i][1] += probabilities[4][1];
            }
        }

        String format = "%6.2f";
        DecimalFormat formatter = new java.text.DecimalFormat("000.00%");

        JLabel[] turnlabels = {sft,fourt,fht,flusht,straightt,threet,twot,oneht,onelt};
        JLabel[] riverlabels = {sfr,fourr,fhr,flushr,straightr,threer,twor,onehr,onelr};

        JLabel[] sflabels = {sft, sfr};
        for(int i=0; i<2; i++){
            if(probabilities[0][i] != 0) {
                String formatted = formatter.format(probabilities[0][i]/numcardsleft).replaceAll("\\G0", " ");
                sflabels[i].setText(formatted + " " + formatted);
            }
            else{
                sflabels[i].setText("");
            }
            sflabels[i].setFont(new Font("Lucida Console", Font.PLAIN, 14));
            sflabels[i].setOpaque(true);
            sflabels[i].setBackground(Color.WHITE);

        }

        for(int i = 1; i < 9; i++){
            if(probabilities[i][1] == 0) {
                turnlabels[i].setText("");
                riverlabels[i].setText("");
            }
            else if(probabilities[i][0] == 0){
                turnlabels[i].setText("");
                riverlabels[i].setText(formatter.format(probabilities[i][1]/numcardsleft).replaceAll("\\G0", " ") + " " + formatter.format(totals[i][1]/numcardsleft).replaceAll("\\G0", " "));
            }
            else{
                turnlabels[i].setText(formatter.format(probabilities[i][0]/numcardsleft).replaceAll("\\G0", " ") + " " + formatter.format(totals[i][0]/numcardsleft).replaceAll("\\G0", " "));
                riverlabels[i].setText(formatter.format(probabilities[i][1]/numcardsleft).replaceAll("\\G0", " ") + " " + formatter.format(totals[i][1]/numcardsleft).replaceAll("\\G0", " "));
            }
            turnlabels[i].setFont(new Font("Lucida Console", Font.PLAIN, 14));
            riverlabels[i].setFont(new Font("Lucida Console", Font.PLAIN, 14));
            if(i % 2 == 0){
                turnlabels[i].setOpaque(true);
                riverlabels[i].setOpaque(true);
                turnlabels[i].setBackground(Color.WHITE);
                riverlabels[i].setBackground(Color.WHITE);

            }
        }



/**        for(int i = 1; i < 9; i++){
                if(probabilities[i][1] == 0){
                    turnlabels[i].setText(String.format (format, probabilities[i][0]/numcardsleft*100) + "% " + formatter.format(totals[i][0]/numcardsleft).replaceAll("\\G0", " "));
                    riverlabels[i].setText(String.format (format, probabilities[i][1]/numcardsleft*100) + "% " + formatter.format(totals[i][1]/numcardsleft).replaceAll("\\G0", " "));
                }
                else{
                    turnlabels[i].setText(formatter.format(probabilities[i][0]/numcardsleft).replaceAll("\\G0", " ") + " " + formatter.format(totals[i][0]/numcardsleft).replaceAll("\\G0", " "));
                    riverlabels[i].setText(formatter.format(probabilities[i][1]/numcardsleft).replaceAll("\\G0", " ") + " " + formatter.format(totals[i][1]/numcardsleft).replaceAll("\\G0", " "));
                }
                turnlabels[i].setFont(new Font("Lucida Console", Font.PLAIN, 14));
                riverlabels[i].setFont(new Font("Lucida Console", Font.PLAIN, 14));
                if(i % 2 == 0){
                    turnlabels[i].setOpaque(true);
                    riverlabels[i].setOpaque(true);
                    turnlabels[i].setBackground(Color.WHITE);
                    riverlabels[i].setBackground(Color.WHITE);

                }
        }   **/


    /**    sft.setText(formatter.format(probabilities[0][0]/numcardsleft).replaceAll("\\G0", " "));
        sfr.setText(String.format (format, probabilities[0][1]/numcardsleft*100) + "%");
        fourt.setText(formatter.format(probabilities[1][0]/numcardsleft*100).replaceAll("\\G0", " ") + formatter.format(totals[1][0]/numcardsleft).replaceAll("\\G0", " "));
        fourr.setText(String.format (format, probabilities[1][1]/numcardsleft*100) + "%   " + String.format (format, totals[1][1]/numcardsleft*100) + "%");
        fht.setText(String.format (format, probabilities[2][0]/numcardsleft*100) + "%   " + String.format (format, totals[2][0]/numcardsleft*100) + "%");
        fhr.setText(String.format (format, probabilities[2][1]/numcardsleft*100) + "%   " + String.format (format, totals[2][1]/numcardsleft*100) + "%");
        flusht.setText(String.format (format, probabilities[3][0]/numcardsleft*100) + "%   " + String.format (format, totals[3][0]/numcardsleft*100) + "%");
        flushr.setText(String.format (format, probabilities[3][1]/numcardsleft*100) + "%   " + String.format (format, totals[3][1]/numcardsleft*100) + "%")
        straightt.setText(String.format (format, probabilities[4][0]/numcardsleft*100) + "%   " + String.format (format, totals[4][0]/numcardsleft*100) + "%");
        straightr.setText(String.format (format, probabilities[4][1]/numcardsleft*100) + "%   " + String.format (format, totals[4][1]/numcardsleft*100) + "%");
        threet.setText(String.format (format, probabilities[5][0]/numcardsleft*100) + "%   " + String.format (format, totals[5][0]/numcardsleft*100) + "%");
        threer.setText(String.format (format, probabilities[5][1]/numcardsleft*100) + "%   " + String.format (format, totals[5][1]/numcardsleft*100) + "%");
        twot.setText(String.format (format, probabilities[6][0]/numcardsleft*100) + "%   " + String.format (format, totals[6][0]/numcardsleft*100) + "%");
        twor.setText(String.format (format, probabilities[6][1]/numcardsleft*100) + "%   " + String.format (format, totals[6][1]/numcardsleft*100) + "%");
        oneht.setText(String.format (format, probabilities[7][0]/numcardsleft*100) + "%   " + String.format (format, totals[7][0]/numcardsleft*100) + "%");
        onehr.setText(String.format (format, probabilities[7][1]/numcardsleft*100) + "%   " + String.format (format, totals[7][1]/numcardsleft*100) + "%");
        onelt.setText(String.format (format, probabilities[8][0]/numcardsleft*100) + "%   " + String.format (format, totals[8][0]/numcardsleft*100) + "%");
        onelr.setText(String.format (format, probabilities[8][1]/numcardsleft*100) + "%   " + String.format (format, totals[8][1]/numcardsleft*100) + "%");



        tfourt.setText(String.format ("%.2f", totals[1][0]/numcardsleft*100) + "%");
        tfourr.setText(String.format ("%.2f", totals[1][1]/numcardsleft*100) + "%");
        tfht.setText(String.format ("%.2f", totals[2][0]/numcardsleft*100) + "%");
        tfhr.setText(String.format ("%.2f", totals[2][1]/numcardsleft*100) + "%");
        tflusht.setText(String.format ("%.2f", totals[3][0]/numcardsleft*100) + "%");
        tflushr.setText(String.format ("%.2f", totals[3][1]/numcardsleft*100) + "%");
        tstraightt.setText(String.format ("%.2f", totals[4][0]/numcardsleft*100) + "%");
        tstraightr.setText(String.format ("%.2f", totals[4][1]/numcardsleft*100) + "%");
        tthreet.setText(String.format ("%.2f", totals[5][0]/numcardsleft*100) + "%");
        tthreer.setText(String.format ("%.2f", totals[5][1]/numcardsleft*100) + "%");
        ttwot.setText(String.format ("%.2f", totals[6][0]/numcardsleft*100) + "%");
        ttwor.setText(String.format ("%.2f", totals[6][1]/numcardsleft*100) + "%");
        toneht.setText(String.format ("%.2f", totals[7][0]/numcardsleft*100) + "%");
        tonehr.setText(String.format ("%.2f", totals[7][1]/numcardsleft*100) + "%");
        tonelt.setText(String.format ("%.2f", totals[8][0]/numcardsleft*100) + "%");
        tonelr.setText(String.format ("%.2f", totals[8][1]/numcardsleft*100) + "%");
**/
        potodds = 1/(pot / bet +1)*100;
        Jpotodds.setText(String.format("%.2f", potodds) + "%"   );
    }

    private void setUpGUInew(){
        this.holecardsJTF = new JTextField();
        this.boardJTF = new JTextField();

        this.potJTF = new JTextField();
        this.betJTF = new JTextField();

        this.getFlop = new JButton("Get Flop");
        getFlop.setMnemonic(KeyEvent.VK_1);
        this.getTurn = new JButton("Get Turn");
        getTurn.setMnemonic(KeyEvent.VK_2);
        this.getBet = new JButton("Get Bet");
        getBet.setMnemonic(KeyEvent.VK_3);


        this.calcJB = new JButton("Calculate");
        this.clearJB = new JButton("Clear Form");

        this.moveJB = new JButton("Move Table");
        moveJB.setMnemonic(KeyEvent.VK_0);

        JFrame mainJFrame = new JFrame("Odds Calculator");
        JPanel mainJPanel = new JPanel();

        mainJPanel.setLayout(new GridLayout(21+2+1-8,3));
        //GridBagConstraints c = new GridBagConstraints();

        mainJPanel.add(this.getFlop);
        mainJPanel.add(this.getTurn);
        mainJPanel.add(this.getBet);


        mainJPanel.add(new JLabel("bet:"));
        mainJPanel.add(new JLabel("pot:"));
        mainJPanel.add(new JLabel("pot odds:"));

        mainJPanel.add(this.betJTF);
        mainJPanel.add(this.potJTF);
        mainJPanel.add(Jpotodds);

        mainJPanel.add(new JLabel("Hole Cards:"));
        mainJPanel.add(this.holecardsJTF);
        mainJPanel.add(this.calcJB);

        mainJPanel.add(new JLabel("Board:"));
        mainJPanel.add(this.boardJTF);
        mainJPanel.add(this.clearJB);

        mainJPanel.add(this.moveJB);
        mainJPanel.add(new JLabel("Probability of making", SwingConstants.RIGHT));
        mainJPanel.add(new JLabel(" hand by"));
        mainJPanel.add(new JLabel("",SwingConstants.RIGHT));
        mainJPanel.add(new JLabel("        Turn             Total"));
        mainJPanel.add(new JLabel("        River            Total"));
        JLabel sftext = (new JLabel("Straight Flush:",SwingConstants.RIGHT)); sftext.setOpaque(true); sftext.setBackground(Color.WHITE); mainJPanel.add(sftext);
        mainJPanel.add(sft);
        mainJPanel.add(sfr);
        mainJPanel.add(new JLabel("Four of a kind:", SwingConstants.RIGHT));
        mainJPanel.add(fourt);
        mainJPanel.add(fourr);
        //mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        //mainJPanel.add(tfourt);
        //mainJPanel.add(tfourr);
        JLabel fhtext = (new JLabel("Full House:",SwingConstants.RIGHT)); fhtext.setOpaque(true); fhtext.setBackground(Color.WHITE); mainJPanel.add(fhtext);
        mainJPanel.add(fht);
        mainJPanel.add(fhr);
        //mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        //mainJPanel.add(tfht);
        //mainJPanel.add(tfhr);
        mainJPanel.add(new JLabel("Flush:",SwingConstants.RIGHT));
        mainJPanel.add(flusht);
        mainJPanel.add(flushr);
        //mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        //mainJPanel.add(tflusht);
        //mainJPanel.add(tflushr);
        JLabel straighttext = (new JLabel("Straight:",SwingConstants.RIGHT)); straighttext.setOpaque(true); straighttext.setBackground(Color.WHITE); mainJPanel.add(straighttext);
        mainJPanel.add(straightt);
        mainJPanel.add(straightr);
        //mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        //mainJPanel.add(tstraightt);
        //mainJPanel.add(tstraightr);
        mainJPanel.add(new JLabel("Three of a Kind:",SwingConstants.RIGHT));
        mainJPanel.add(threet);
        mainJPanel.add(threer);
        //mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        //mainJPanel.add(tthreet);
        //mainJPanel.add(tthreer);
        JLabel twotext = (new JLabel("Two Pair:",SwingConstants.RIGHT)); twotext.setOpaque(true); twotext.setBackground(Color.WHITE); mainJPanel.add(twotext);
        mainJPanel.add(twot);
        mainJPanel.add(twor);
        //mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        //mainJPanel.add(ttwot);
        //mainJPanel.add(ttwor);
        mainJPanel.add(new JLabel("High Pair:",SwingConstants.RIGHT));
        mainJPanel.add(oneht);
        mainJPanel.add(onehr);
        //mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        //mainJPanel.add(toneht);
        //mainJPanel.add(tonehr);
        JLabel oneltext = (new JLabel("Low Pair:",SwingConstants.RIGHT)); oneltext.setOpaque(true); oneltext.setBackground(Color.WHITE); mainJPanel.add(oneltext);
        mainJPanel.add(onelt);
        mainJPanel.add(onelr);
        //mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        //mainJPanel.add(tonelt);
        //mainJPanel.add(tonelr);




        //mainJFrame.setMaximumSize(new Dimension(200,1000));
        mainJFrame.setMinimumSize(new Dimension(450, 600));
        mainJFrame.setContentPane(mainJPanel);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJFrame.setLocationRelativeTo(null);
        mainJFrame.pack();

        mainJFrame.setVisible(true);

    }

    private void setUpGUI(){
        this.holecardsJTF = new JTextField();
        this.boardJTF = new JTextField();

        this.potJTF = new JTextField();
        this.betJTF = new JTextField();

        this.getFlop = new JButton("Get Flop");
        getFlop.setMnemonic(KeyEvent.VK_1);
        this.getTurn = new JButton("Get Turn");
        getTurn.setMnemonic(KeyEvent.VK_2);
        this.getBet = new JButton("Get Bet");

        this.calcJB = new JButton("Calculate");
        this.clearJB = new JButton("Clear Form");

        JFrame mainJFrame = new JFrame("Odds Calculator");
        JPanel mainJPanel = new JPanel();

        mainJPanel.setLayout(new GridLayout(21+2+1,3));
        //GridBagConstraints c = new GridBagConstraints();

        mainJPanel.add(this.getFlop);
        mainJPanel.add(this.getTurn);
        mainJPanel.add(this.getBet);


        mainJPanel.add(new JLabel("bet:"));
        mainJPanel.add(new JLabel("pot:"));
        mainJPanel.add(new JLabel("pot odds:"));

        mainJPanel.add(this.betJTF);
        mainJPanel.add(this.potJTF);
        mainJPanel.add(Jpotodds);



        mainJPanel.add(new JLabel("Hole Cards:"));
        mainJPanel.add(this.holecardsJTF);
        mainJPanel.add(this.calcJB);
        mainJPanel.add(new JLabel("Board:"));
        mainJPanel.add(this.boardJTF);
        mainJPanel.add(this.clearJB);
        mainJPanel.add(new JLabel(""));
        mainJPanel.add(new JLabel("Probability of making hand by"));
        mainJPanel.add(new JLabel(""));
        mainJPanel.add(new JLabel("Straight Flush",SwingConstants.HORIZONTAL));
        mainJPanel.add(new JLabel("  Turn    Total"));
        mainJPanel.add(new JLabel("  River   Total"));
        mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        mainJPanel.add(sft);
        mainJPanel.add(sfr);
        mainJPanel.add(new JLabel("Four of a kind",SwingConstants.HORIZONTAL));
        mainJPanel.add(fourt);
        mainJPanel.add(fourr);
        mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        mainJPanel.add(tfourt);
        mainJPanel.add(tfourr);
        mainJPanel.add(new JLabel("Full House",SwingConstants.HORIZONTAL));
        mainJPanel.add(fht);
        mainJPanel.add(fhr);
        mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        mainJPanel.add(tfht);
        mainJPanel.add(tfhr);
        mainJPanel.add(new JLabel("Flush",SwingConstants.HORIZONTAL));
        mainJPanel.add(flusht);
        mainJPanel.add(flushr);
        mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        mainJPanel.add(tflusht);
        mainJPanel.add(tflushr);
        mainJPanel.add(new JLabel("Straight",SwingConstants.HORIZONTAL));
        mainJPanel.add(straightt);
        mainJPanel.add(straightr);
        mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        mainJPanel.add(tstraightt);
        mainJPanel.add(tstraightr);
        mainJPanel.add(new JLabel("Three of a Kind",SwingConstants.HORIZONTAL));
        mainJPanel.add(threet);
        mainJPanel.add(threer);
        mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        mainJPanel.add(tthreet);
        mainJPanel.add(tthreer);
        mainJPanel.add(new JLabel("Two Pair",SwingConstants.HORIZONTAL));
        mainJPanel.add(twot);
        mainJPanel.add(twor);
        mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        mainJPanel.add(ttwot);
        mainJPanel.add(ttwor);
        mainJPanel.add(new JLabel("High Pair",SwingConstants.HORIZONTAL));
        mainJPanel.add(oneht);
        mainJPanel.add(onehr);
        mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        mainJPanel.add(toneht);
        mainJPanel.add(tonehr);
        mainJPanel.add(new JLabel("Low Pair",SwingConstants.HORIZONTAL));
        mainJPanel.add(onelt);
        mainJPanel.add(onelr);
        mainJPanel.add(new JLabel("Total:   ",SwingConstants.RIGHT));
        mainJPanel.add(tonelt);
        mainJPanel.add(tonelr);




        mainJFrame.setMaximumSize(new Dimension(200,1000));
        mainJFrame.setContentPane(mainJPanel);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJFrame.setLocationRelativeTo(null);
        mainJFrame.pack();

        mainJFrame.setVisible(true);

    }



    public static void removefromdeck(String remove){
        deck = deck.replaceAll(remove, "00");
    }

    public static void removesuit(String remove){
        for(int i = 1; i < deck.length(); i++){
            if(deck.substring(i,i+1).equals(remove)){
                deck = deck.substring(0,i-1) + "00" + deck.substring(i+1);
            }
        }
        //System.out.println(deck);
    }
    public static void removenum(String remove){
        for(int i = 0; i < deck.length(); i++){
            if(deck.substring(i,i+1).equals(remove)){
                deck = deck.substring(0,i) + "00" + deck.substring(i+2);
            }
        }
        //System.out.println(deck);
    }

    public static int countdeck(String lookup){
        return(deck.length() - deck.replace(lookup, "").length());
    }

    public static int[] createintary(String cardsinplay, int[] cardvalues){
        for(int i = 0; i < (cardsinplay.length() / 2); i++){
            String value = cardsinplay.substring(i*2,i*2+1);
            switch (value){
                case "a":
                    cardvalues[i] = 1;
                    cardvalues[6] = 14;
                    break;
                case "t":
                    cardvalues[i] = 10;
                    break;
                case "j":
                    cardvalues[i] = 11;
                    break;
                case "q":
                    cardvalues[i] = 12;
                    break;
                case "k":
                    cardvalues[i] = 13;
                    break;
                default:
                    cardvalues[i] = Integer.parseInt(value);
                    break;
            }
        }

        int min;
        int indexofmin = 0;
        int hold;
        for(int j = 0; j < 7; j++) {
            min = 1000;
            for (int i = j; i < 7; i++) {
                if(i != j & cardvalues[i] == cardvalues[j]){
                    cardvalues[i] = i * 100 + 50;
                }
                if(cardvalues[i] == min){
                    cardvalues[i] = i * 100 + 50;
                }
                else if (cardvalues[i] < min) {
                    min = cardvalues[i];
                    indexofmin = i;
                }
            }
            hold = cardvalues[indexofmin];
            cardvalues[indexofmin] = cardvalues[j];
            cardvalues[j] = hold;
        }
        for(int i =0; i < 7; i++){
            //System.out.print(cardvalues[i] + " ");
        }
        return cardvalues;
    }



    public static String checkcount(int count, String suit){
        if(count ==5){
            String flushcards = "";
            for(int i = 1; i < cardsinplay.length(); i = i + 2){
                if(cardsinplay.substring(i,i+1).equals(suit.substring(0,1))){
                    flushcards = flushcards + cardsinplay.substring(i-1,i+1);
                }
            }
            flushcardvalues = createintary(flushcards, flushcardvalues);
            return("You have a flush of" + suit);
        }
        if(count ==4){
            //removesuit(suit.substring(0,1));
            outs[3] = 9;
            String flushcards = "";
            for(int i = 1; i < cardsinplay.length(); i = i + 2){
                if(cardsinplay.substring(i,i+1).equals(suit.substring(0,1))){
                    flushcards = flushcards + cardsinplay.substring(i-1,i+1);
                }
            }
            flushcardvalues = createintary(flushcards, flushcardvalues);
            return(suit);
        }
        if(count==3 & board.length()==6){
            return("Backdoor: " + suit);
        }
        else{
            return("no");
        }
    }

    public static String checkflush(){
        int scount = 0;
        int ccount = 0;
        int hcount = 0;
        int dcount = 0;

        for(int i = 0; i < (cardsinplay.length() / 2); i++) {
            String value = cardsinplay.substring(i * 2 + 1, i * 2 + 2);
            switch (value) {
                case "s":
                    scount++;
                    break;
                case "c":
                    ccount++;
                    break;
                case "h":
                    hcount++;
                    break;
                case "d":
                    dcount++;
                    break;
                default:
                    break;
            }
        }
        String sstring = checkcount(scount,"spades");
        String cstring = checkcount(ccount,"clubs");
        String hstring = checkcount(hcount,"hearts");
        String dstring = checkcount(dcount,"diamonds");

        if(!sstring.equals("no")){
            return sstring;
        }
        if(!cstring.equals("no")){
            return cstring;
        }
        if(!hstring.equals("no")){
            return hstring;
        }
        if(!dstring.equals("no")){
            return dstring;
        }

        return"no";
    }


    public static int checkstraight(int []cardvalues){
        int start = 0;
        int count = 0;
        boolean fourof5 = false;

        for(int i = 0; i < cardvalues.length; i++){
            System.out.println(cardvalues[i]);
        }


        for(int i = 0; i < 3; i++) {
            if (cardvalues[i + 4] - cardvalues[i] == 4) {
                //System.out.println("You have a straight.");
                count = 5;
                start = cardvalues[i];
                //System.out.println(start);
                break;
            }
        }
        if(count == 0) {
            for (int i = 0; i < 4; i++) {
                if (cardvalues[i + 3] - cardvalues[i] == 3) {
                    count = 4;
                    start = cardvalues[i];
                    if(start == 1){
                        straightouts[0] = 5;
                        break;
                    }
                    else if(start == 11){
                        straightouts[0] = 10;
                        break;
                    }
                    else{
                        straightouts[0] = start - 1;
                        straightouts[1] = start + 4;
                        break;
                    }
                }
            }
        }
        if(count == 0){
            for(int i = 0; i < 4; i++){
                if(cardvalues[i + 3] - cardvalues[i] == 4){
                    count = 4;
                    start = cardvalues[i];
                    for(int j = i; j <= i + 3; j++){
                        if(j > cardvalues.length){break;}
                        if(cardvalues[j] + 1 != cardvalues[j+1]){
                            straightouts[0] = cardvalues[j] + 1;
                            if(j==i){
                                if(j+4 >= cardvalues.length){break;}
                                if(cardvalues[j+4] == cardvalues[j] + 6){
                                    straightouts[1] = cardvalues[j] + 5;
                                }
                            }
                            break;
                        }
                    }
                }
                if(count!=0){
                    break;
                }
            }
        }



        //System.out.println("straight outs: " + straightouts[0] + " " + straightouts[1]);





        return 0;
    }


    public static void setupdeck(){
        straightouts[0] = 0;
        straightouts[1] = 0;
        probabilities = new double[9][2];
        deck = "2h3h4h5h6h7h8h9hthjhqhkhah2d3d4d5d6d7d8d9dtdjdqdkdad2s3s4s5s6s7s8s9stsjsqsksas2c3c4c5c6c7c8c9ctcjcqckcac";
        board = board.replaceAll("10","t");
        board = board.toLowerCase();
        holecards = holecards.toLowerCase();
        holecards = holecards.replaceAll("10", "t");

        String hole1 = holecards.substring(0,2);
        String hole2 = holecards.substring(2,4);
        String board1 = board.substring(0,2);
        String board2 = board.substring(2,4);
        String board3 = board.substring(4,6);
        String board4 = "nn";
        if(board.length()>6){
            board4 = board.substring(6,8);
        }
        numcardsleft = 50 - (board.length() / 2);
        removefromdeck(board1);
        removefromdeck(board2);
        removefromdeck(board3);
        removefromdeck(board4);
        removefromdeck(hole1);
        removefromdeck(hole2);
        cardsinplay = holecards + board;

        value1 = hole1.substring(0,1);
        suit1 = hole1.substring(1,2);
        value2 = hole2.substring(0,1);
        suit2 = hole2.substring(1,2);

    }

    public static String toString(int num){
        switch (num){
            case 1: return "a";
            case 10: return "t";
            case 11: return "j";
            case 12: return "q";
            case 13: return "k";
            case 14: return "a";
            case 0: return "n";
            default: return Integer.toString(num);
        }
    }


    public static int calcStraighFlush(){
        int sfouts = 0;
        String flush = checkflush();
        if(!flush.equals("no")){
            checkstraight(flushcardvalues);
        }
        String flushsuit = flush.substring(0,1);
        String sfout1 = toString(straightouts[0]) + flushsuit;
        String sfout2 = toString(straightouts[1]) + flushsuit;
        if(!sfout1.substring(0,1).equals("n")){
            sfouts++;
            removefromdeck(sfout1);
        }
        if(!sfout2.substring(0,1).equals("n")){
            sfouts++;
            removefromdeck(sfout2);
        }
        return sfouts;
    }
    public static int calcFour(){

        int fourouts = 0;
        String card = " ";
        int count1 = 1;
        int count2 = 1;
        if(holecards.substring(0,1).equals(holecards.substring(2,3))){
            count1 = 2;
        }
        for(int i = 0; i < board.length() / 2; i++){
            if(board.substring(i*2, i*2+1).equals(holecards.substring(0,1))){
                count1++;
            }
            else if(board.substring(i*2, i*2+1).equals(holecards.substring(2,3))){
                count2++;
            }
        }
        String card2 = " ";
        if(count1 == 3){
            card = holecards.substring(0,1);
        }
        if(count2 == 3){
            if(card.equals(" ")) {
                card = holecards.substring(2, 3);
            }
            else{
                card2 = holecards.substring(2, 3);
            }
        }
        if(count1 == 3 | count2 == 3){
            fourouts = countdeck(card);
            removenum(card);
        }
        if(count1 == 3 & count2 == 3){
            fourouts *= 2;
            removenum(card2);
        }
        return fourouts;
    }
    public static int calcFullHouse(){
        int fhouts = 0;
        int count1 = cardsinplay.length() - cardsinplay.replaceAll(value1,"").length();
        int count2 = cardsinplay.length() - cardsinplay.replaceAll(value2,"").length();
        if(count1 == 2 & count2 == 2 & !value1.equals(value2)){
            fhouts = countdeck(value1) + countdeck(value2);
            removenum(value1);
            removenum(value2);
        }
        if(count1 == 3 & count2 == 1){
            fhouts = countdeck(value2);
            removenum(value2);
        }
        if(count1 == 1 & count2 == 3){
            fhouts = countdeck(value1);
            removenum(value1);
        }
        cardvalues = createintary(cardsinplay, cardvalues);
        int counts[] = new int[cardvalues.length];
        for(int i =0; i< cardvalues.length; i++) {
            counts[i] = cardsinplay.length() - cardsinplay.replaceAll(Integer.toString(cardvalues[i]),"").length();
        }
        for(int i = 0; i < counts.length; i++){
            if(counts[i] == 3 & count1 == 1 & count2 == 1){
                fhouts = countdeck(value1) + countdeck(value2);
                removenum(value1);
                removenum(value2);
                break;
            }

        }
        return fhouts;
    }

    public static int calcFlush(){
        int fouts = 0;
        String flush = checkflush();
        System.out.println(flush); ///
        String flushsuit = flush.substring(0,1);
        if(!flush.equals("no")){
            fouts = countdeck(flushsuit);
            removesuit(flushsuit);
        }
        return fouts;
    }
    public static double calcBackdoorFlush(){
        double fouts = 0;
        System.out.println(fouts);
        String flush = checkflush();
        if(!flush.equals("no") && flush.substring(0, 4).equals("Back")) {
            fouts = (1.0 / 24.0);
        }
        return fouts;
    }
    public static int calcStraight(){
        int souts = 0;
        straightouts[0] = 0;
        straightouts[1] = 0;
        cardvalues = createintary(cardsinplay, cardvalues);
        checkstraight(cardvalues);
        String fout1 = toString(straightouts[0]);
        String fout2 = toString(straightouts[1]);
        souts += countdeck(fout1);
        souts += countdeck(fout2);

        if(fout1 != "n"){removenum(fout1);}
        if(fout2 != "n"){removenum(fout2);}
        return souts;
    }
    public static double calcBackStraight(){
        return checkbackstraight(cardvalues);
    }
    public static double checkbackstraight(int []cardvalues){
        for(int i = 0; i < 5; i++){
            if (cardvalues[i + 2] - cardvalues[i] == 2) { //123 234 345
                if(cardvalues[i] == 1 | cardvalues[i] == 12){  // 123 12 13 14 exact 2
                    return (1.0 / 67.7);  //in
                }
                if(cardvalues[i] == 2 | cardvalues[i] == 11){  // 234 11 12 13 exact 1
                    return (1.0 / 33.8);  //in-out
                }
                return (1.0 / 22.5);  //345 any out
            }
            if(cardvalues[i + 2] - cardvalues[i] == 3){

                if(cardvalues[i] == 1 | cardvalues[i] == 11){ // 134 11 13 14 exact 2
                    return (1.0 / 67.7); //in
                }
                return (1.0 / 33.8); // 245 exact 1 in-out
            }

            if(cardvalues[i + 2] - cardvalues[i] == 4){ // 145  exact 2
                return (1.0 / 67.7); //in
            }
        }
        return 0;
    }
    public static int calcThree(){
        System.out.println(deck);
        if(probabilities[1][1] != 0 | probabilities[2][1] != 0){
            System.out.println("EXIT!");
            return 0;
        }
        int thouts = 0;
        int count1 = cardsinplay.length() - cardsinplay.replaceAll(value1,"").length();
        int count2 = cardsinplay.length() - cardsinplay.replaceAll(value2,"").length();
        System.out.println("count1" + count1);
        System.out.println("count2" + count2);
        if(count1 == 2){
            thouts = countdeck(value1);
            removenum(value1);
        }
        if(count2 == 2 & !value1.equals(value2)){
            thouts = countdeck(value2);
            removenum(value2);
        }
        System.out.println(thouts);
        return thouts;
    }

    public static int calcTwo(){
        if(probabilities[1][1] != 0 | probabilities[2][1] != 0){
            System.out.println("EXIT!");
            return 0;
        }
        int touts = 0;
        int count1 = cardsinplay.length() - cardsinplay.replaceAll(value1,"").length();
        int count2 = cardsinplay.length() - cardsinplay.replaceAll(value2,"").length();
        if(count1 == 1 & count2 == 2){
            touts = countdeck(value1);
        }
        if(count1 == 2 & count2 == 1){
            touts = countdeck(value2);
        }
        return touts;
    }

    public static int calcOne(){
        int count1 = cardsinplay.length() - cardsinplay.replaceAll(value1,"").length();
        int count2 = cardsinplay.length() - cardsinplay.replaceAll(value2,"").length();
        if(count1 != 1 | count2 != 1){
            System.out.println("EXIT!");
            return 0;
        }
        return(countdeck(value1));
    }



    public static void main(String[] args){
       calculator calc = new calculator();
       calc.test();
        }

    public void test(){
        betJTF.setText("1");
        potJTF.setText("1");
        holecardsJTF.setText("3d4c");
        boardJTF.setText("qsqhqd");
    }
}

