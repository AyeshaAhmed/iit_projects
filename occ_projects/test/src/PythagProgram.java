//Java 156 Lab7 Excercise6 
//Ayesha Ahmed
//12-03-13

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PythagProgram extends JFrame
{
   private JLabel ExplainL0, ExplainL1, ExplainL2, FirstNumL, SecondNumL, ThirdNumL, ResultL;

   private JTextField FirstNumTF, SecondNumTF, ThirdNumTF;

   private JTextArea ResultTA;

   private JButton calculateB, exitB;

   private CalculateButtonHandler cbHandler;
   private ExitButtonHandler ebHandler;
   
   private GridLayout gridLayoutMgr0, gridLayoutMgr1, gridLayoutMgr2;
   private JPanel gridPanel0, gridPanel1, gridPanel2;

   private static final int WIDTH = 950;
   private static final int HEIGHT = 500;

   public PythagProgram()
   {
             //Create the four labels
      ExplainL0 = new JLabel("  Enter any 3 integers to check if the sum of",
                                  SwingConstants.LEFT);
      ExplainL1 = new JLabel("  the first and second number to the quantity squared",
                                  SwingConstants.LEFT);
      ExplainL2 = new JLabel("  equals the third number to the quantity squared.",
                                  SwingConstants.LEFT);//North
      FirstNumL = new JLabel("Enter the first number: ",
                                  SwingConstants.RIGHT);
      SecondNumL = new JLabel("Enter the second number: ",
                                  SwingConstants.RIGHT);
      ThirdNumL = new JLabel("Enter the third number: ",
                                  SwingConstants.RIGHT);//Center
      ResultL = new JLabel("~ ~ ~ ~ ~ Result: ~ ~ ~ ~ ~", SwingConstants.CENTER);
      
      ResultTA = new JTextArea(10, 30);//South
      ResultTA.setEditable(false);

             //Create the four text fields
      FirstNumTF = new JTextField(10);
      SecondNumTF = new JTextField(10);
      ThirdNumTF = new JTextField(10);

             //Create Calculate Button
      calculateB = new JButton("Calculate");
      cbHandler = new CalculateButtonHandler();
      calculateB.addActionListener(cbHandler);

             //Create Exit Button
      exitB = new JButton("Exit");
      ebHandler = new ExitButtonHandler();
      exitB.addActionListener(ebHandler);

             //Set the title of the window
      setTitle("Pythagorean Triple Ordered Pairs Checker");

             //Get the container
      Container pane = getContentPane();

             //Set the layout
      pane.setLayout(new BorderLayout());
      
      gridLayoutMgr0 = new GridLayout(3, 1);//north
      gridLayoutMgr1 = new GridLayout(3, 2); //center
      gridLayoutMgr2 = new GridLayout(2, 2); //center

             //Place the components in the pane
      gridPanel0 = new JPanel();
      gridPanel0.setLayout(gridLayoutMgr0);
      gridPanel0.add(ExplainL0);
      gridPanel0.add(ExplainL1);
      gridPanel0.add(ExplainL2);
      pane.add(gridPanel0, BorderLayout.NORTH);
      
      gridPanel1 = new JPanel();
      gridPanel1.setLayout(gridLayoutMgr1);
      gridPanel1.add(FirstNumL);
      gridPanel1.add(FirstNumTF);
      gridPanel1.add(SecondNumL);
      gridPanel1.add(SecondNumTF);
      gridPanel1.add(ThirdNumL);
      gridPanel1.add(ThirdNumTF);
      pane.add(gridPanel1, BorderLayout.CENTER);
      
      gridPanel2 = new JPanel();
      gridPanel2.setLayout(gridLayoutMgr2);
      gridPanel2.add(ResultL);
      gridPanel2.add(ResultTA);
      gridPanel2.add(calculateB);
      gridPanel2.add(exitB);
      pane.add(gridPanel2, BorderLayout.SOUTH);

             //Set the size of the window and display it
      setSize(WIDTH, HEIGHT);
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   private class CalculateButtonHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
        boolean value;
        int FirstNum = Integer.parseInt(FirstNumTF.getText()), SecondNum = Integer.parseInt(SecondNumTF.getText()), ThirdNum = Integer.parseInt(ThirdNumTF.getText());
        double FirstD, SecondD, ThirdD, FirstPow, SecondPow, ThirdPow, SumPow;      
        String display;
               
        value = isPythag(FirstNum, SecondNum, ThirdNum);
        FirstD = beDouble(FirstNum);
        SecondD = beDouble(SecondNum);
        ThirdD = beDouble(ThirdNum);
        
        FirstPow = Math.pow(FirstD, 2.0);
        SecondPow = Math.pow(SecondD, 2.0);
        ThirdPow = Math.pow(ThirdD, 2.0);
        
        SumPow = FirstPow + SecondPow;
        
                    if (value == true){
                        display = "Yes,the sum of the first and second number to the quantity squared" + 
                                            "\nequals the third number to the quantity squared." +
                                            "\nLet's see how it figures out: " + 
                                            "\n(" + FirstNum + "*" + FirstNum + ") + (" + SecondNum + "*" + SecondNum + ") ?= (" + ThirdNum + "*" + ThirdNum + ")" +
                                            "\n" + FirstPow + " + " + SecondPow + " ?= " + ThirdPow +
                                            "\n" + SumPow + "=" + ThirdPow + 
                                            "\n Correct!";
                    }else{
                        display = "No,the sum of the first and second number to the quantity squared" + 
                                            "\nDOES NOT equal the third number to the quantity squared." + 
                                            "\nLet's see how it figures out: " + 
                                            "\n(" + FirstNum + "*" + FirstNum + ") + (" + SecondNum + "*" + SecondNum + ") ?= (" + ThirdNum + "*" + ThirdNum + ")" +
                                            "\n" + FirstPow + " + " + SecondPow + " ?= " + ThirdPow +
                                            "\n" + SumPow + "?=" + ThirdPow + 
                                            "\n Incorrect.";                        
                        }
        ResultTA.setText(display);
    }
   
   }
   
    public static boolean isPythag (int x, int y, int z){
        boolean value;
        double First, Second, Third, Check1, Check2;
        First = x;
        Second = y;
        Third = z;
        Check1 = (Math.pow(First, 2.0))+ (Math.pow(Second, 2.0));
        Check2 = (Math.pow(Third, 2.0));
        value = (Check1 == Check2);
                if (value == true){
                    return true;
                }else                
                    return false;
    }
    public static double beDouble (int x){
        double ConDouble;
        ConDouble = x;
        return ConDouble;
    }

   private class ExitButtonHandler implements ActionListener
   {
       public void actionPerformed(ActionEvent e)
       {
           System.exit(0);
       }
   }

   public static void main(String[] args)
   {
       PythagProgram pythagObject = new PythagProgram();
   }
}