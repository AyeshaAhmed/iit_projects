//1040ez Tax program Honor's Project Java 156 
//Author: Ayesha Ahmed
//Date: 10/17/13

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import java.io.*;

public class TaxProject extends JFrame{
   private JLabel DisclaimerL0, DisclaimerL1, DisclaimerL2, DisclaimerL3, DisclaimerL4, DisclaimerL5, DisclaimerL6, DisclaimerL7, DisclaimerL8, 
   					BlankL1, BlankL2, FilingL, YourFirstNameL, YourLastNameL, YourSSNL, SpouseFirstNameL, SpouseLastNameL, SpouseSSNL,
                    AddressNumL, AddressStreetL, AddressAptNumL, AddressCityL, AddressStateL, AddressZipL, ForeignCountryL, ForeignStateL, ForeignZipL, 
   						WagesL, InterestL, DividendL, DependenceL, TotalDeductionL, WithheldL, EICL, CombatL, TotalCreditL,
                               AdjustGrossL, TaxableIncomeL, TaxL, RefundL, OweL, blankL;

   private JTextField YourFirstNameTF, YourLastNameTF, YourSSNTF, SpouseFirstNameTF, SpouseLastNameTF, SpouseSSNTF, 
   						AddressNumTF, AddressStreetTF, AddressAptNumTF, AddressCityTF, AddressStateTF, AddressZipTF, ForeignCountryTF, ForeignStateTF, ForeignZipTF,
   							WagesTF, InterestTF, DividendTF, DependenceTF, blankTF1, blankTF2, blankTF3, blankTF4, blankTF5, TotalDeductionTF, WithheldTF, EICTF, CombatTF, TotalCreditTF,
                               AdjustGrossTF, TaxableIncomeTF, TaxTF, RefundTF, OweTF;

   private JRadioButton File_SingleRB, File_JointRB, You_joint_DependRB, Spouse_joint_DependRB, Both_joint_DependRB, None_joint_DependRB, 
                            Yes_single_DependRB, No_single_DependRB;
   private ButtonGroup SelectFilingBGroup, JointSelectDependBGroup, SingleSelectDependBGroup;
   private JButton calculateB, exitB;
   private CalculateButtonHandler cbHandler;
   private ExitButtonHandler ebHandler;
   
   private BorderLayout MAINborderLayoutMgr, NORTHborderLayoutMgr, CENTERborderLayoutMgr, SOUTHborderLayoutMgr, MIDDLEborderLayoutMgr;
   private GridLayout gridLayoutMgr0, gridLayoutMgr1, gridLayoutMgr2, gridLayoutMgr3, gridLayoutMgr4, 
   						gridLayoutMgr5, gridLayoutMgr6, gridLayoutMgr7;
   private JPanel gridPanel0, gridPanel1, gridPanel2, gridPanel3, gridPanel4, 
   					gridPanel5, gridPanel6, gridPanel7,
   					borderPanel0, borderPanel1, borderPanel2, borderPanel3;
   
   public double YourSSN, SpouseSSN, line1, line2, line3, line4, line5, line6, line7, line8a, line8b, line9, line10, line11, line12;
   public double A, B, C, D, E, F, G, l, m, n;
   public boolean isSingle, isJoint, isSingleDepend, isJointDepend_One, isJointDepend_Both;
   public String FilingStatusS, DependStatusS;
   public PrintWriter outFile;
   
   private static final int WIDTH = 1100;
   private static final int HEIGHT = 825;

   private static final ItemListener ItemListener = null;
   
   public TaxProject()
   {
      DisclaimerL0 = new JLabel(":::::::::::::::::::::::::::::::::::::::::: D I S C L A I M E R ::::::::::::::::::::::::::::::::::::::::::", SwingConstants.LEFT);
      DisclaimerL1 = new JLabel("          USE THE 1040EZ TAX FORM ESTIMATOR PROGRAM ONLY IF: ", SwingConstants.LEFT);
      DisclaimerL2 = new JLabel(" - Your filing status is single or married filing jointly.", SwingConstants.LEFT);
      DisclaimerL3 = new JLabel(" - You (and your spouse if filing jointly) were under age 65 and not blind at the end of 2013.", SwingConstants.LEFT);
      DisclaimerL4 = new JLabel(" - You do not claim any dependents.", SwingConstants.LEFT); 
      DisclaimerL5 = new JLabel(" - Your taxable income (line 6) is less than $100,000.", SwingConstants.LEFT);
      DisclaimerL6 = new JLabel(" - Your taxable interest was not over 1,500.", SwingConstants.LEFT); 
      DisclaimerL7 = new JLabel(" - You do not claim any ajustments to income.", SwingConstants.LEFT); 
      DisclaimerL8 = new JLabel(" - The only tax credit you can claim is the earned income credit (EIC).", SwingConstants.LEFT);
      FilingL = new JLabel("What is your filing status?", SwingConstants.LEFT);
      YourFirstNameL = new JLabel("Your first name and initial:", SwingConstants.LEFT);
      YourLastNameL = new JLabel("Your last name:", SwingConstants.LEFT);
      YourSSNL = new JLabel("Your social security number:", SwingConstants.LEFT);
      SpouseFirstNameL = new JLabel("Spouse's first name and initial:", SwingConstants.LEFT);
      SpouseLastNameL = new JLabel("Spouse's last name:", SwingConstants.LEFT);
      SpouseSSNL = new JLabel("Spouse's social security number:", SwingConstants.LEFT);
      AddressNumL = new JLabel("Home address (number):", SwingConstants.LEFT);
      AddressStreetL = new JLabel("(street):", SwingConstants.LEFT);
      AddressAptNumL = new JLabel("(Apartment No.):", SwingConstants.LEFT);
      AddressCityL = new JLabel("City:", SwingConstants.LEFT);
      AddressStateL = new JLabel("State:", SwingConstants.LEFT);
      AddressZipL = new JLabel("Zip code:", SwingConstants.LEFT);
      ForeignCountryL = new JLabel("(For foreign address fill following) Foreign country name:", SwingConstants.LEFT);
      ForeignStateL = new JLabel("Foreign province/state/county:", SwingConstants.LEFT);
      ForeignZipL = new JLabel("Foreign postal code:", SwingConstants.LEFT);
      WagesL = new JLabel("Enter your wages, salaries and tips: \nThis should be in box 1 of your W2 form.", SwingConstants.RIGHT);
      InterestL = new JLabel("Enter your taxable interest: ",
                                 SwingConstants.RIGHT);
      DividendL = new JLabel("Enter your unemployment compensation and Alaska Permanent Fund dividends: ", SwingConstants.RIGHT);
      DependenceL = new JLabel("Can someone claim you as a dependant? Check the applicable box below: ",
                                  SwingConstants.CENTER);
      WithheldL = new JLabel("Enter your federal income tax withheld from Form(s) W2 and 1099: ", SwingConstants.RIGHT);
      EICL = new JLabel("Enter your earned income credit (EIC): ", SwingConstants.RIGHT);
      CombatL = new JLabel("Enter your nontaxable combat pay election: ", SwingConstants.RIGHT);
      
      BlankL1 = new JLabel("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
      BlankL2 = new JLabel("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
      blankL = new JLabel("~ ~ ~ ~ ~ ~ ~ CALCULATIONS and TOTALS ~ ~ ~ ~ ~ ~ ~", SwingConstants.CENTER);
      
      AdjustGrossL = new JLabel("Adjusted gross income: ", SwingConstants.RIGHT);
      TotalDeductionL = new JLabel("Total standard deduction and exemption: ", SwingConstants.RIGHT);
      TaxableIncomeL = new JLabel("Taxable income: ", SwingConstants.RIGHT);
      TotalCreditL = new JLabel("Total payments and credits: ", SwingConstants.RIGHT);
      TaxL = new JLabel("Tax amount: ", SwingConstants.RIGHT);
      RefundL = new JLabel("Refund: ", SwingConstants.RIGHT);
      OweL = new JLabel("The amont you owe in taxes: ", SwingConstants.RIGHT);
      
      YourFirstNameTF = new JTextField(10);
      YourLastNameTF = new JTextField(10);
      YourSSNTF = new JTextField(9);
      SpouseFirstNameTF = new JTextField(10);
      SpouseLastNameTF = new JTextField(10);
      SpouseSSNTF = new JTextField(9);
      AddressNumTF = new JTextField(10);
      AddressStreetTF = new JTextField(10);
      AddressAptNumTF = new JTextField(10);
      AddressCityTF = new JTextField(10);
      AddressStateTF = new JTextField(10);
      AddressZipTF = new JTextField(10);
      ForeignCountryTF = new JTextField(10);
      ForeignStateTF = new JTextField(10);
      ForeignZipTF = new JTextField(10);
      WagesTF = new JTextField(8); 
      InterestTF = new JTextField(8);
      DividendTF = new JTextField(8);
      DependenceTF = new JTextField(8);
      DependenceTF.setEditable(false);
      TotalDeductionTF = new JTextField(8);
      TotalDeductionTF.setEditable(false);
      blankTF1 = new JTextField(8);
      blankTF1.setEditable(false);
      blankTF2 = new JTextField(8);
      blankTF2.setEditable(false);
      blankTF3 = new JTextField(8);
      blankTF3.setEditable(false);
      blankTF4 = new JTextField(8);
      blankTF4.setEditable(false);
      blankTF5 = new JTextField(8);
      blankTF5.setEditable(false);
      WithheldTF = new JTextField(8);
      EICTF = new JTextField(8);
      CombatTF = new JTextField(8);
      TotalCreditTF = new JTextField(8);
      TotalCreditTF.setEditable(false);
      AdjustGrossTF = new JTextField(8);
      AdjustGrossTF.setEditable(false);
      TaxableIncomeTF = new JTextField(8);
      TaxableIncomeTF.setEditable(false);
      TaxTF = new JTextField(8);
      TaxTF.setEditable(false);
      RefundTF = new JTextField(8);
      RefundTF.setEditable(false);
      OweTF = new JTextField(8);
      OweTF.setEditable(false);
      
      File_SingleRB = new JRadioButton("Filing Single");
      File_JointRB = new JRadioButton("Married Filing Jointly");
      You_joint_DependRB = new JRadioButton("You");
      Spouse_joint_DependRB = new JRadioButton("Spouse");
      Both_joint_DependRB = new JRadioButton("Both");
      None_joint_DependRB = new JRadioButton("Neither");
      Yes_single_DependRB = new JRadioButton("Yes");
      No_single_DependRB = new JRadioButton("No");
      
      File_SingleRB.addItemListener(new SelectItemListener());
      File_JointRB.addItemListener(new SelectItemListener());
      You_joint_DependRB.addItemListener(new SelectItemListener());
      Spouse_joint_DependRB.addItemListener(new SelectItemListener());
      Both_joint_DependRB.addItemListener(new SelectItemListener());
      None_joint_DependRB.addItemListener(new SelectItemListener());
      Yes_single_DependRB.addItemListener(new SelectItemListener());
      No_single_DependRB.addItemListener(new SelectItemListener());
      
             //Create Calculate Button
      calculateB = new JButton("Calculate Tax");
      cbHandler = new CalculateButtonHandler();
      calculateB.addActionListener(cbHandler);

             //Create Exit Button
      exitB = new JButton("Exit");
      ebHandler = new ExitButtonHandler();
      exitB.addActionListener(ebHandler);
      

             //Set the title of the window
      setTitle("1040EZ Tax Estimator Program.");

             //Get the container
      Container pane = getContentPane();
      
      MAINborderLayoutMgr = new BorderLayout();
      //******************************
      NORTHborderLayoutMgr = new BorderLayout();
      gridLayoutMgr0 = new GridLayout(6, 2);//north
      gridLayoutMgr1 = new GridLayout(11, 3); //south
      //******************************
      CENTERborderLayoutMgr = new BorderLayout();
      gridLayoutMgr2 = new GridLayout(3, 2);//North
      
      MIDDLEborderLayoutMgr = new BorderLayout();//Center
      gridLayoutMgr3 = new GridLayout(1, 2);//north      
      gridLayoutMgr4 = new GridLayout(1, 4);//south - RadioButtons ***change***
      
      gridLayoutMgr5 = new GridLayout(3, 2);//South
      //******************************
      SOUTHborderLayoutMgr = new BorderLayout();
      gridLayoutMgr6 = new GridLayout(1, 1);//north
      gridLayoutMgr7 = new GridLayout(8, 2);//south

             //Set the layout
      pane.setLayout(MAINborderLayoutMgr);
      
             //Place the components in the pane
      borderPanel0 = new JPanel();
      borderPanel0.setLayout(NORTHborderLayoutMgr);
      gridPanel0 = new JPanel();
      gridPanel0.setLayout(gridLayoutMgr0);
      gridPanel0.add(DisclaimerL0);
      gridPanel0.add(blankTF1);
      gridPanel0.add(DisclaimerL1);
      gridPanel0.add(DisclaimerL2);
      gridPanel0.add(DisclaimerL3);
      gridPanel0.add(DisclaimerL4);
      gridPanel0.add(DisclaimerL5);
      gridPanel0.add(DisclaimerL6);
      gridPanel0.add(DisclaimerL7);
      gridPanel0.add(DisclaimerL8);
      gridPanel0.add(BlankL1);
      gridPanel0.add(BlankL2);
      borderPanel0.add(gridPanel0, BorderLayout.NORTH);
      
      gridPanel1 = new JPanel();
      gridPanel1.setLayout(gridLayoutMgr1);
      gridPanel1.add(FilingL);
      gridPanel1.add(File_SingleRB);
      gridPanel1.add(File_JointRB);
      gridPanel1.add(YourFirstNameL);
      gridPanel1.add(YourLastNameL);
      gridPanel1.add(YourSSNL);
      gridPanel1.add(YourFirstNameTF);      
      gridPanel1.add(YourLastNameTF);      
      gridPanel1.add(YourSSNTF);
      gridPanel1.add(SpouseFirstNameL);
      gridPanel1.add(SpouseLastNameL);
      gridPanel1.add(SpouseSSNL);
      gridPanel1.add(SpouseFirstNameTF);
      gridPanel1.add(SpouseLastNameTF);      
      gridPanel1.add(SpouseSSNTF);
      gridPanel1.add(AddressNumL);      
      gridPanel1.add(AddressStreetL);      
      gridPanel1.add(AddressAptNumL);
      gridPanel1.add(AddressNumTF);
      gridPanel1.add(AddressStreetTF);
      gridPanel1.add(AddressAptNumTF);
      gridPanel1.add(AddressCityL);
      gridPanel1.add(AddressStateL);
      gridPanel1.add(AddressZipL);
      gridPanel1.add(AddressCityTF);
      gridPanel1.add(AddressStateTF);
      gridPanel1.add(AddressZipTF);
      gridPanel1.add(ForeignCountryL);
      gridPanel1.add(ForeignStateL);
      gridPanel1.add(ForeignZipL);
      gridPanel1.add(ForeignCountryTF);
      gridPanel1.add(ForeignStateTF);
      gridPanel1.add(ForeignZipTF);
      borderPanel0.add(gridPanel1, BorderLayout.SOUTH);
      pane.add(borderPanel0, BorderLayout.NORTH);
      
      
      borderPanel1 = new JPanel();
      borderPanel1.setLayout(CENTERborderLayoutMgr);
      gridPanel2 = new JPanel();
      gridPanel2.setLayout(gridLayoutMgr2);
      gridPanel2.add(WagesL);
      gridPanel2.add(WagesTF);
      gridPanel2.add(InterestL);
      gridPanel2.add(InterestTF);
      gridPanel2.add(DividendL);
      gridPanel2.add(DividendTF);
      borderPanel1.add(gridPanel2, BorderLayout.NORTH);
      
      borderPanel2 = new JPanel();
      borderPanel2.setLayout(MIDDLEborderLayoutMgr);
      gridPanel3 = new JPanel();
      gridPanel3.setLayout(gridLayoutMgr3);
      gridPanel3.add(DependenceL);
      gridPanel3.add(DependenceTF);
      borderPanel2.add(gridPanel3, BorderLayout.NORTH);
      
      gridPanel4 = new JPanel();
      gridPanel4.setLayout(gridLayoutMgr4);
      gridPanel4.add(blankTF2);
      gridPanel4.add(blankTF3);
      gridPanel4.add(blankTF4);
      gridPanel4.add(blankTF5);
      borderPanel2.add(gridPanel4, BorderLayout.SOUTH);
      borderPanel1.add(borderPanel2, BorderLayout.CENTER);
      
      gridPanel5 = new JPanel();
      gridPanel5.setLayout(gridLayoutMgr5);
      gridPanel5.add(WithheldL);
      gridPanel5.add(WithheldTF);
      gridPanel5.add(EICL);
      gridPanel5.add(EICTF);
      gridPanel5.add(CombatL);
      gridPanel5.add(CombatTF);
      borderPanel1.add(gridPanel5, BorderLayout.SOUTH);
      pane.add(borderPanel1, BorderLayout.CENTER);
      
      borderPanel3 = new JPanel();
      borderPanel3.setLayout(SOUTHborderLayoutMgr);
      gridPanel6 = new JPanel();
      gridPanel6.setLayout(gridLayoutMgr6);
      gridPanel6.add(blankL);
      borderPanel3.add(gridPanel6, BorderLayout.NORTH);
      
      gridPanel7 = new JPanel();
      gridPanel7.setLayout(gridLayoutMgr7);
      gridPanel7.add(AdjustGrossL);
      gridPanel7.add(AdjustGrossTF);
      gridPanel7.add(TotalDeductionL);
      gridPanel7.add(TotalDeductionTF);
      gridPanel7.add(TotalCreditL);
      gridPanel7.add(TotalCreditTF);      
      gridPanel7.add(TaxableIncomeL);
      gridPanel7.add(TaxableIncomeTF);
      gridPanel7.add(TaxL);
      gridPanel7.add(TaxTF);
      gridPanel7.add(RefundL);
      gridPanel7.add(RefundTF);
      gridPanel7.add(OweL);
      gridPanel7.add(OweTF);      
      gridPanel7.add(calculateB);
      gridPanel7.add(exitB);
      borderPanel3.add(gridPanel7, BorderLayout.SOUTH);
      pane.add(borderPanel3, BorderLayout.SOUTH);
      

      SelectFilingBGroup = new ButtonGroup();
      SelectFilingBGroup.add(File_SingleRB);
      SelectFilingBGroup.add(File_JointRB);
      
      JointSelectDependBGroup = new ButtonGroup();
      JointSelectDependBGroup.add(You_joint_DependRB);
      JointSelectDependBGroup.add(Spouse_joint_DependRB);
      JointSelectDependBGroup.add(Both_joint_DependRB);
      JointSelectDependBGroup.add(None_joint_DependRB);
      
      SingleSelectDependBGroup = new ButtonGroup();
      SingleSelectDependBGroup.add(Yes_single_DependRB);
      SingleSelectDependBGroup.add(No_single_DependRB);
            
      
             //Set the size of the window and display it
      setSize(WIDTH, HEIGHT);
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   class SelectItemListener implements ItemListener {
   public void itemStateChanged(ItemEvent e)
   {
       if (e.getSource() == File_SingleRB){
    	   isSingle = true;
           isJoint = false;
           FilingStatusS = "Single";
           gridPanel4.removeAll();
           gridPanel4 = new JPanel();
           gridPanel4.setLayout(gridLayoutMgr4);
           gridPanel4.add(blankTF2);
           gridPanel4.add(blankTF3);
           gridPanel4.add(Yes_single_DependRB);
           gridPanel4.add(No_single_DependRB);
           borderPanel2.add(gridPanel4, BorderLayout.CENTER);
           gridPanel4.revalidate();
           gridPanel4.setVisible(true);
       }
       if (e.getSource() == File_JointRB){
           isJoint = true;
           isSingle = false;
           FilingStatusS = "Joint";
           gridPanel4.removeAll();
           gridPanel4 = new JPanel();
           gridPanel4.setLayout(gridLayoutMgr4);
           gridPanel4.add(You_joint_DependRB);
           gridPanel4.add(Spouse_joint_DependRB);
           gridPanel4.add(Both_joint_DependRB);
           gridPanel4.add(None_joint_DependRB);
           borderPanel2.add(gridPanel4, BorderLayout.CENTER);
           gridPanel4.revalidate();
           gridPanel4.setVisible(true);
       }       
       
       if (isSingle == true){
           if (e.getSource() == Yes_single_DependRB){
               isSingleDepend = true;
               DependStatusS = "Dependent"; 
           }else if (e.getSource() == No_single_DependRB){
               isSingleDepend = false;
               DependStatusS = "Not Dependent"; 
           }
       }else if(isJoint == true){
           if (e.getSource() == You_joint_DependRB){
               isJointDepend_One = true;
               isJointDepend_Both = false;
               DependStatusS = "One Dependent";
           }else if (e.getSource() == Spouse_joint_DependRB){
               isJointDepend_One = true;
               isJointDepend_Both = false;
               DependStatusS = "One Dependent";
           }else if (e.getSource() == Both_joint_DependRB){
               isJointDepend_Both = true;
               isJointDepend_One = false;
               DependStatusS = "Both Dependent";
           }else if (e.getSource() == None_joint_DependRB){
               isJointDepend_Both = false;
               isJointDepend_One = false;
               DependStatusS = "Neither Dependent";
           }
       }
       }
   }


   private class CalculateButtonHandler implements ActionListener 
   {
      public void actionPerformed(ActionEvent e) 
      {
         String YourFirstNameS = YourFirstNameTF.getText(), YourLastNameS = YourLastNameTF.getText(), YourSSNS = YourSSNTF.getText(), 
                 SpouseFirstNameS = SpouseFirstNameTF.getText(), SpouseLastNameS = SpouseLastNameTF.getText(), SpouseSSNS = SpouseSSNTF.getText(),
                		 HouseNumS = AddressNumTF.getText(), HouseStreetS = AddressStreetTF.getText(), HouseAptS = AddressAptNumTF.getText(), 
                		 HouseCityS = AddressCityTF.getText(), HouseStateS = AddressStateTF.getText(), HouseZipS = AddressZipTF.getText(), 
                		 ForeignCountryS = ForeignCountryTF.getText(), ForeignStateS = ForeignStateTF.getText(), ForeignZipS = ForeignZipTF.getText(),
                 l1 = WagesTF.getText(), l2 = InterestTF.getText(), l3 = DividendTF.getText(), l7 = WithheldTF.getText(),
                        l8a = EICTF.getText(), l8b = CombatTF.getText();
         /*char checkYourSSN = YourSSNS.charAt(0), checkSpouseSSN = SpouseSSNS.charAt(0);
         double YourSSNnum, SpouseSSNnum;
         
         if (Character.isDigit(checkYourSSN)){
             YourSSNnum = Double.parseDouble(YourSSNS);
             }else{
            	 String outputStr = "Your Social Security Number is invalid. \nPlease try again.";                
                 JOptionPane.showMessageDialog(null, outputStr, "Error", JOptionPane.ERROR_MESSAGE);
             }
         if (Character.isDigit(checkSpouseSSN)){
             SpouseSSNnum = Double.parseDouble(SpouseSSNS);
             }else{
            	 String outputStr = "Spose's Social Security Number is invalid. \nPlease try again.";                
                 JOptionPane.showMessageDialog(null, outputStr, "Error", JOptionPane.ERROR_MESSAGE);
             }*/
         if(YourSSNS.length() > 9){
             String outputStr = "Your Social Security Number \nhas more than 9 digits. \nPlease try again.";                
             JOptionPane.showMessageDialog(null, outputStr, "Error", JOptionPane.ERROR_MESSAGE);
             }
         if(SpouseSSNS.length() > 9){
             String outputStr = "Spouse's Social Security Number \nhas more than 9 digits. \nPlease try again.";                
             JOptionPane.showMessageDialog(null, outputStr, "Error", JOptionPane.ERROR_MESSAGE);                
             }
         
         if(l1.length() == 0){
             line1 = 0;
         }else line1 = Double.parseDouble(WagesTF.getText());
         
         if(l2.length() == 0){
             line2 = 0;
         }else line2 = Double.parseDouble(InterestTF.getText());
           if(line2 > 1500){
                String outputStr = "Your taxable interest is greater than $1500. \nYou cannot use the 1040ez form";                
                JOptionPane.showMessageDialog(null, outputStr, "Error", JOptionPane.ERROR_MESSAGE);                
                System.exit(0);
            }
         if(l3.length() == 0){
             line3 = 0;
         }else line3 = Double.parseDouble(DividendTF.getText());
         
         line4 = line1 + line2 + line3;
         AdjustGrossTF.setText("$" + line4 + "0.");
         
         if (isSingle == true && isJoint == false){
             //line5 = 
                 if(isSingleDepend == true){
                     A = line1 + 350.00;
                     B = 1000.00;
                     C = larger(A, B);
                     D = 6100;
                     E = smaller(C, D);
                     F = 0.0;
                     G = E + F;
                     line5 = G;
                 }else if(isSingleDepend == false){
                     line5 = 10000.00;
                     }else {
                    	String outputStr = "Please select your dependency status.";                
                 		JOptionPane.showMessageDialog(null, outputStr, "Error", JOptionPane.ERROR_MESSAGE);
                     }
         }else if(isJoint == true && isSingle == false){
             //line5 = 
             if (isJointDepend_One == true){
                 A = line1 + 350.00;
                 B = 1000.00;
                 C = larger(A, B);
                 D = 12200.00;
                 E = smaller(C, D);
                 F = 3900.00;
                 G = E + F;
                 line5 = G;
                 }else if (isJointDepend_Both == true){
                     A = line1 + 350.00;
                     B = 1000.00;
                     C = larger(A, B);
                     D = 12200.00;
                     E = smaller(C, D);
                     F = 0.0;
                     G = E + F;
                     line5 = G;
                 }else if (isJointDepend_Both == false){
                     line5 = 20000;
                 }else {
                 	String outputStr = "Please select your dependency status.";                
              		JOptionPane.showMessageDialog(null, outputStr, "Error", JOptionPane.ERROR_MESSAGE);
                  }
         }
         line5 = line5;
         TotalDeductionTF.setText("$" + line5 + "0.");
         
         if(line5 > line4){
             line6 = 0;
            }else line6 = line4 - line5;
            
            //limits -- 0 =< line6 < 10,000
            
            if(line6 == 0){
                n = 0;
            }else if(line6 >= 100000){
                String outputStr = "Your taxable income is greater than or equal to $100,000. \nYou cannot use the 1040ez form";                
                JOptionPane.showMessageDialog(null, outputStr, "Error", JOptionPane.ERROR_MESSAGE);                
                System.exit(0);
            }else if(line6 >= 0 && line6 <= 100000){
            	line6 = line6;
            }
            TaxableIncomeTF.setText("$" + line6 + "0.");
         //ifSingle line10 =
            if (isSingle == true && isJoint == false){
         if (line6 >= 0 && line6 <= 8700){
             l = line6 - 0;
             m = l * 0.1;
             n = m + 0;
         }else if (line6 > 8700 && line6 <= 35350){
             l = line6 - 8700;
             m = l * 0.15;
             n = m + 870.00;
         }else if (line6 > 35350 && line6 <= 85650){
             l = line6 - 35350;
             m = l * 0.25;
             n = m + 4867.50;
         }else if (line6 > 85650 && line6 < 100000){
             l = line6 - 85650;
             m = l * 0.28;
             n = m + 17442.50;
         }//ifJoint line10 = 
            }else if (isSingle == false && isJoint == true){
            	if (line6 >= 0 && line6 <= 17400){
                    l = line6 - 0;
                    m = l * 0.1;
                    n = m + 0;
                }else if (line6 > 17400 && line6 <= 70700){
                    l = line6 - 17400;
                    m = l * 0.15;
                    n = m + 1740.00;
                }else if (line6 > 70700 && line6 <= 100000){
                    l = line6 - 70700;
                    m = l * 0.25;
                    n = m + 9735.00;
                }
                   }else {
                    	String outputStr = "Please select your filing and dependency statuses.";                
                  		JOptionPane.showMessageDialog(null, outputStr, "Error", JOptionPane.ERROR_MESSAGE);
                      }

         line10 = n;
         TaxTF.setText("$" + line10 + "0.");
         if(l7.length() == 0){
             line7 = 0;
            }else line7 = Double.parseDouble(WithheldTF.getText()); 
         
         if(l8a.length() == 0){
             line8a = 0;
            }else line8a = Double.parseDouble(EICTF.getText());
            
         if(l8b.length() == 0){
             line8b = 0;
            }else line8b = Double.parseDouble(CombatTF.getText());
         
         line9 = line7 + line8a;
         TotalCreditTF.setText("$" + line9 + "0.");
                  
         if(line9 > line10){
             line12 = 0;
             line11 = line9 - line10;
            }else if(line10 > line9){
             line11 = 0;
             line12 = line10 - line9;
            }else if (line9 == line10){
                line11 = 0;
                line12 = 0;
            }
         RefundTF.setText("$" + line11 + "0.");
         OweTF.setText("$" + line12 + "0.");
         
         try {
			outFile = new PrintWriter("" + YourFirstNameS + "_" + YourLastNameS + "'s_Tax_Form.txt");
		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		 outFile.println("" + YourFirstNameS + "_" + YourLastNameS + "'s_Tax_Form.");
		 outFile.println("This is generated by the 1040EZ Tax Form Estimation Program.");
         outFile.println("Please note that this is only an estimate.");
         outFile.println();
         outFile.printf("%nYour filing status: \t %s.", FilingStatusS);
         outFile.printf("%nYour first name and initial: %s \tYour last name: %s \tYour social security number: %s", YourFirstNameS, YourLastNameS, YourSSNS);
         outFile.printf("%nSpouse's first name and initial: %s \tSpouse's last name: %s \tSpouse's social security number: %s", SpouseFirstNameS, SpouseLastNameS, SpouseSSNS);
         outFile.printf("%nHome address: (number and street): %s %s \tApt. No.: %s", HouseNumS, HouseStreetS, HouseAptS);
         outFile.printf("%nCity/State/Zip code: %s, \t%s \t%s", HouseCityS, HouseStateS, HouseZipS);
         outFile.printf("%nForeign country name (if any): %s \tForeign province/state/county: %s \tForeign postal code: %s", ForeignCountryS, ForeignStateS, ForeignZipS);
         outFile.printf("%n___________________________________________________________________________________________________________________");
         outFile.printf("%n(line 1) Wages, salaries, and tips: \t$%.2f", line1);
         outFile.printf("%n(line 2) Taxable interest: \t$%.2f", line2);
         outFile.printf("%n(line 3) Unemployment compensation and Alaska Permanent Fund dividends: \t$%.2f", line3);
         outFile.printf("%n(line 4) Adjusted Gross Income: \t$%.2f", line4);
         outFile.printf("%nYour Dependency Status: \t$%s.", DependStatusS);
         outFile.printf("%n(line 5) Total standard deduction and exemption (due to dependency status): \t$%.2f", line5);
         outFile.printf("%n(line 6) Taxable income: \t$%.2f", line6);
         outFile.printf("%n___________________________________________________________________________________________________________________");
         outFile.printf("%n(line7) Federal income tax withheld from Form(s) W-2 and 1099: \t$%.2f", line7);
         outFile.printf("%n(line 8a) Earned income credit (EIC): \t$%.2f", line8a);
         outFile.printf("%n(line 8b) Nontaxable combat pay election: $%.2f", line8b);
         outFile.printf("%n(line 9) Total payments and credits: \t$%.2f", line9);
         outFile.printf("%n(line 10) Tax (this has been estimated): \t$%.2f", line10);
         outFile.printf("%n___________________________________________________________________________________________________________________");
         outFile.printf("%n(line 11) Refund (if any): \t$%.2f", line11);
         outFile.printf("%n(line 12) Amount you owe: \t$%.2f", line12);
         outFile.printf("%n____________________________________________________E N D__________________________________________________________");
         outFile.printf("%nThank you for using the 1040EZ Tax Form Estimator Program.");
         outFile.printf("%nHave a nice day!");
         outFile.close(); 
         }
           
      }

      public static double larger(double x, double y)
      {
          double max;

          if (x >= y)
              max = x;
          else
              max = y;

          return max;
      }
      
      public static double smaller(double x, double y)
      {
          double min;

          if (x <= y)
              min = x;
          else
              min = y;

          return min;
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
       TaxProject taxObject = new TaxProject();
   }
}