    //Adder Java 156 - Lab3. 
    //Date: 08/20/13
    //Author: Ayesha Ahmed
    
    import javax.swing.*; 
    public class Adder{ 
        public static void main(String[] args) { 
                int count = 1;
                int sum = 0;
                //loop structure
                while (count<=100){
                    sum += count;
                    count ++;
                }
                //Display the sum.
                JOptionPane.showMessageDialog(null,"The sum of 1 to 100 is: "+ sum, "The sum of 1 to 100", JOptionPane.INFORMATION_MESSAGE);
            }
        }
