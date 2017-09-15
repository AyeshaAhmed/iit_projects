    //doWhileLoop Java 156 - Lab3. 
    //Date: 08/20/13
    //Author: Ayesha Ahmed
    
    import javax.swing.*; 
    public class doWhileLoop{ 
        public static void main(String[] args) { 
            //do-while loop goes at least once
            int count = 1;
            int sum = 0;
                do{
                //loop structure
                sum += count;
                count ++;
                //Display the sum.
            }while (count<=100);
            JOptionPane.showMessageDialog(null,"The sum of 1 to 100 is: "+ sum, "The sum of 1 to 100", JOptionPane.INFORMATION_MESSAGE);
        }
    }
