    //forLoop Java 156 - Lab3. 
    //Date: 08/20/13
    //Author: Ayesha Ahmed
    //This program will display the following message to the terminal screen: “Hello CSC 156!!!”. 
    
    import javax.swing.*; 
    public class forLoop{ 
        public static void main(String[] args) { 
                int sum = 0;
                //loop structure
                for (int count = 1; count<=100; count ++){
                    sum += count;
                }
                //Display the sum.
                JOptionPane.showMessageDialog(null,"The sum of 1 to 100 is: "+ sum, "The sum of 1 to 100", JOptionPane.INFORMATION_MESSAGE);
            }
        }
