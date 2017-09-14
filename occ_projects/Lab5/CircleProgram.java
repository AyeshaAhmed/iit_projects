//Exercise 5 - Java 156 - CircleProgram in a JFrame
//Author: Ayesha Ahmed
//Date: 10/15/13

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CircleProgram extends JFrame
{
   private JLabel radiusL, orL, diameterL, areaL, circumferenceL, blankL;

   private JTextField radiusTF, diameterTF, areaTF, circumferenceTF;

   private JButton calculateB, exitB;

   private CalculateButtonHandler cbHandler;
   private ExitButtonHandler ebHandler;

   private static final int WIDTH = 400;
   private static final int HEIGHT = 600;

   public CircleProgram()
   {
             //Create the four labels
      radiusL = new JLabel("Enter the radius: ",
                                  SwingConstants.RIGHT);
      orL = new JLabel("~OR~", SwingConstants.RIGHT);
      diameterL = new JLabel("Enter the diameter: ",
                                  SwingConstants.RIGHT);
      areaL = new JLabel("Area: ", SwingConstants.RIGHT);
      circumferenceL = new JLabel("Circumference: ",
                                  SwingConstants.RIGHT);
      blankL = new JLabel("~ ~ ~ ~", SwingConstants.RIGHT);

             //Create the four text fields
      radiusTF = new JTextField(10);
      diameterTF = new JTextField(10);
      areaTF = new JTextField(10);
      circumferenceTF = new JTextField(10);

             //Create Calculate Button
      calculateB = new JButton("Calculate");
      cbHandler = new CalculateButtonHandler();
      calculateB.addActionListener(cbHandler);

             //Create Exit Button
      exitB = new JButton("Exit");
      ebHandler = new ExitButtonHandler();
      exitB.addActionListener(ebHandler);

             //Set the title of the window
      setTitle("Area and Circumference of a Circle");

             //Get the container
      Container pane = getContentPane();

             //Set the layout
      pane.setLayout(new GridLayout(6, 2));

             //Place the components in the pane
      pane.add(radiusL);
      pane.add(radiusTF);
      pane.add(orL);
      pane.add(blankL);
      pane.add(diameterL);
      pane.add(diameterTF);
      pane.add(areaL);
      pane.add(areaTF);
      pane.add(circumferenceL);
      pane.add(circumferenceTF);
      pane.add(calculateB);
      pane.add(exitB);

             //Set the size of the window and display it
      setSize(WIDTH, HEIGHT);
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   private class CalculateButtonHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         double radius, diameter, area, circumference;
         String r = radiusTF.getText(), d = diameterTF.getText();
                 
         if (d.length() == 0){
            radius = Double.parseDouble(radiusTF.getText());
            area = (Math.pow(radius, 2.0)) * Math.PI;
            diameter = radius * 2;
            circumference = diameter * Math.PI;
            
            areaTF.setText("" + area);
            circumferenceTF.setText("" + circumference);
            diameterTF.setText("" + diameter);
         }else if(r.length() == 0){
            diameter = Double.parseDouble(diameterTF.getText());
            area = (Math.pow((diameter / 2), 2.0)) * Math.PI;
            circumference = diameter * Math.PI;
            radius = diameter / 2;
            
            areaTF.setText("" + area);
            circumferenceTF.setText("" + circumference);
            radiusTF.setText("" + radius);
         }
      }
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
       CircleProgram circlObject = new CircleProgram();
   }
}
