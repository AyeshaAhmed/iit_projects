//Java 156 - Lab7 Pizza Parlor
//Author: Ayesha Ahmed
//Date: 11-24-2013

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AyeshaPizzaParlor extends JApplet implements
                                               ItemListener
{
    private JLabel YourOrderL, toppingsL, sizeL, crustL;
    private JCheckBox tomatoCB, greenPepperCB, blackOlivesCB, mushroomsCB, extraCheeseCB, pepperoniCB, sausageCB;
    private JRadioButton smallSizeRB, mediumSizeRB, largeSizeRB, thinCrustRB, mediumCrustRB, panCrustRB;
    private ButtonGroup SizeSelectBGroup, CrustSelectBGroup, ToppingSelectBGroup;
    private JTextArea OrderTA;
    private JButton CalculateOrderB, ResetB, PlaceOrderB;
    private PlaceOrderButtonHandler pobHandler;
    private ResetButtonHandler rbHandler;
    private CalculateOrderButtonHandler cobHandler;
    //private JComboBox presetPizzaDD;
    /*private String[] pizzaNames
                     = {"Meat Lover's", "Veggie Dream",
                        "Cheesy Paradise", "Everything"};*/
    public String sizeS, crustS, prevTopS = " ", addTopS = prevTopS, TopDisplayS;
    public double multTop = 0, priceSize = 0;

    public void init()
    {
        Container c = getContentPane();
        c.setLayout(null);

        tomatoCB = new JCheckBox("Tomato");
        greenPepperCB = new JCheckBox("Green Pepper");
        blackOlivesCB = new JCheckBox("Black Olives");
        mushroomsCB = new JCheckBox("Mushrooms");
        extraCheeseCB = new JCheckBox("Extra Cheese");
        pepperoniCB = new JCheckBox("Pepperoni");
        sausageCB = new JCheckBox("Sausage");
        smallSizeRB = new JRadioButton("Small $6.50");
        mediumSizeRB = new JRadioButton("Medium $8.50");
        largeSizeRB = new JRadioButton("Large $10.00");
        thinCrustRB = new JRadioButton("Thin Crust");
        mediumCrustRB = new JRadioButton("Medium Crust");
        panCrustRB = new JRadioButton("Pan Crust");
        
        CalculateOrderB = new JButton ("Calculate Order");
        cobHandler = new CalculateOrderButtonHandler();
        CalculateOrderB.addActionListener(cobHandler);
        
        ResetB = new JButton ("Reset");
        rbHandler = new ResetButtonHandler();
        ResetB.addActionListener(rbHandler);
        
        PlaceOrderB = new JButton("Place Order");
        pobHandler = new PlaceOrderButtonHandler();
        PlaceOrderB.addActionListener(pobHandler);
        
        YourOrderL = new JLabel("Your Order: ");
        toppingsL = new JLabel("Select Toppings:");
        sizeL = new JLabel("Select Size:");
        crustL = new JLabel("Select Crust");
        
        OrderTA = new JTextArea(10, 50);
        OrderTA.setEditable(false);
        //presetPizzaDD = new JComboBox(presetPizza);
        //presetPizzaDD.setMaximumRowCount(4);

        tomatoCB.setSize(125, 30);
        greenPepperCB.setSize(125, 30);
        blackOlivesCB.setSize(125, 30);
        mushroomsCB.setSize(125, 30);
        extraCheeseCB.setSize(125, 30);
        pepperoniCB.setSize(125, 30);
        sausageCB.setSize(125, 30);
        smallSizeRB.setSize(125, 30);
        mediumSizeRB.setSize(125, 30);
        largeSizeRB.setSize(125, 30);
        thinCrustRB.setSize(125, 30);
        mediumCrustRB.setSize(125, 30);
        panCrustRB.setSize(125, 30);
        CalculateOrderB.setSize(200, 30);
        ResetB.setSize(80, 30);
        PlaceOrderB.setSize(200, 30);
        YourOrderL.setSize(200, 30);
        toppingsL.setSize(250, 30);
        sizeL.setSize(250, 30);
        crustL.setSize(250, 30);
        OrderTA.setSize(700, 200);
         //presetPizzaDD.setSize(1000, 30);
        
        tomatoCB.setLocation(100, 100);
        greenPepperCB.setLocation(100, 130);
        blackOlivesCB.setLocation(100, 160);
        mushroomsCB.setLocation(100, 190);
        extraCheeseCB.setLocation(100, 220);
        pepperoniCB.setLocation(100, 250);
        sausageCB.setLocation(100, 280);
        smallSizeRB.setLocation(350, 100);
        mediumSizeRB.setLocation(350, 130);
        largeSizeRB.setLocation(350, 160);
        thinCrustRB.setLocation(600, 100);
        mediumCrustRB.setLocation(600, 130);
        panCrustRB.setLocation(600, 160);
        CalculateOrderB.setLocation(80, 360);
        ResetB.setLocation(80, 410);
        PlaceOrderB.setLocation(300, 500);
        YourOrderL.setLocation(300, 250);
        toppingsL.setLocation(100, 70);
        sizeL.setLocation(350, 70);
        crustL.setLocation(600, 70);
        OrderTA.setLocation(300, 280);
        //presetPizzaDD.setLocation(100, 400);
        
        tomatoCB.addItemListener(this);
        greenPepperCB.addItemListener(this);
        blackOlivesCB.addItemListener(this);

        mushroomsCB.addItemListener(this);
        extraCheeseCB.addItemListener(this);
        pepperoniCB.addItemListener(this);
        sausageCB.addItemListener(this);
        smallSizeRB.addItemListener(this);
        mediumSizeRB.addItemListener(this);
        largeSizeRB.addItemListener(this);
        thinCrustRB.addItemListener(this);
        mediumCrustRB.addItemListener(this);
        panCrustRB.addItemListener(this);
        //presetPizzaDD.addItemListener(this);

        c.add(tomatoCB);
        c.add(greenPepperCB);
        c.add(blackOlivesCB);
        c.add(mushroomsCB);
        c.add(extraCheeseCB);
        c.add(pepperoniCB);
        c.add(sausageCB);
        c.add(smallSizeRB);
        c.add(mediumSizeRB);
        c.add(largeSizeRB);
        c.add(thinCrustRB);
        c.add(mediumCrustRB);
        c.add(panCrustRB);
        c.add(CalculateOrderB);
        c.add(ResetB);
        c.add(PlaceOrderB);
        c.add(YourOrderL);
        c.add(toppingsL);
        c.add(sizeL);
        c.add(crustL);
        c.add(OrderTA);
        //c.add(presetPizzaDD);
                
        SizeSelectBGroup = new ButtonGroup();
        SizeSelectBGroup.add(smallSizeRB);
        SizeSelectBGroup.add(mediumSizeRB);
        SizeSelectBGroup.add(largeSizeRB);
        
        CrustSelectBGroup = new ButtonGroup();
        CrustSelectBGroup.add(thinCrustRB);
        CrustSelectBGroup.add(mediumCrustRB);
        CrustSelectBGroup.add(panCrustRB);
        
        setSize(1100, 600);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.setApplet(Color.white);
        g.setColor(Color.red);
        g.drawRoundRect(75, 60, 170, 260, 40, 40);
        g.drawRoundRect(330, 60 , 170, 145, 40, 40);
        g.drawRoundRect(580, 60, 170, 145, 40, 40);
        g.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 30));
        g.drawString("Ayesha's Pizza Parlor", 250, 40);
    }

    public void itemStateChanged(ItemEvent e)
    {
        
        if (e.getStateChange() == ItemEvent.SELECTED){
        
            if (e.getSource() == tomatoCB)
            {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    multTop++;
                    addTopS = prevTopS + "Tomatoes, ";
                }
                
                }
                prevTopS = addTopS;
            if (e.getSource() == greenPepperCB)
            {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    multTop++;
                    addTopS = prevTopS + "Green Peppers, ";
                }
                
            }
                    prevTopS = addTopS;
            if (e.getSource() == blackOlivesCB)
            {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    multTop++;
                    addTopS = prevTopS + "Black Olives, ";
                }
                
            }
                    prevTopS = addTopS;
            if (e.getSource() == mushroomsCB)
            {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    multTop++;
                    addTopS = prevTopS + "Mushrooms, ";
                }
                
            }
                    prevTopS = addTopS;
            if (e.getSource() == extraCheeseCB)
            {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    multTop++;
                    addTopS = prevTopS + "Extra Cheese, ";
                }
                
            }
                    prevTopS = addTopS;
            if (e.getSource() == pepperoniCB)
            {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    multTop++;
                    addTopS = prevTopS + "Pepperoni, ";
                }
                
            }
                prevTopS = addTopS;
            if (e.getSource() == sausageCB)
            {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    multTop++;
                    addTopS = prevTopS + "Sausages, ";
                }
                
            }
                prevTopS = addTopS;
       }else if (e.getStateChange() == ItemEvent.DESELECTED)
                multTop = 0;
                addTopS = prevTopS;
        if (e.getSource() == smallSizeRB){
            priceSize = 6.50;
            sizeS = "Small";
        }else if (e.getSource() == mediumSizeRB){
            priceSize = 8.50;
            sizeS = "Medium";
        }else if (e.getSource() == largeSizeRB){
            priceSize = 10.00;
            sizeS = "Large";
        }
        
        if (e.getSource() == thinCrustRB){
            crustS = "Thin";
        }else if (e.getSource() == mediumCrustRB){
            crustS = "Medium";
        }else if (e.getSource() == panCrustRB){
            crustS = "Pan";
        }else crustS = "";

        /*if (e.getSource() == fontFaceDD)
            currentFontName =
                       fontNames[fontFaceDD.getSelectedIndex()];*/
    }
    
     private class CalculateOrderButtonHandler implements ActionListener{
    
      public void actionPerformed(ActionEvent e)
      {
         double priceTop, finalPrice;
         priceTop = 1.50 * multTop;
         finalPrice = priceTop + priceSize;
         String display = "Toppings:" + addTopS + "\nSize: "  + sizeS + "\nCrust type: " + crustS
                           + "\nTopping price: $" + priceTop + "0. \nSize price: $" + priceSize + "0. \nTotal: $" + finalPrice + "0.";
         OrderTA.setText(display);
                          
      }
    }

     private class PlaceOrderButtonHandler implements ActionListener{
    
      public void actionPerformed(ActionEvent e)
      {
         String str = "Your order has been placed!";
         JOptionPane.showMessageDialog(null, str, "Order Placed", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    
     private class ResetButtonHandler implements ActionListener{
    
      public void actionPerformed(ActionEvent e)
      {
         OrderTA.setText("");
         SizeSelectBGroup.clearSelection();
         CrustSelectBGroup.clearSelection();
         tomatoCB.setSelected(false);
         greenPepperCB.setSelected(false);
         blackOlivesCB.setSelected(false);
         mushroomsCB.setSelected(false);
         extraCheeseCB.setSelected(false);
         pepperoniCB.setSelected(false);
         sausageCB.setSelected(false);
         prevTopS = " ";
         addTopS = prevTopS;
         sizeS = "";
         priceSize = 0.0;
      }
    }
}