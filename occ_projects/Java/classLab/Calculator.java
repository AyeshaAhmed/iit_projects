

public class Calculator{
    private enum Numbers {ZERO(0), ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE}
    private char decimal, plus, minus, times, divide, equal, clear;
    private String input, output, screen;
    
    public Calculator(){
        screen = "Enter:";
        input = "";
        output = "";
        decimal = '.';
        plus = '+';
        minus = '-';
        times = '*';
        divide = '/';
        clear = ' ';
    }
    
    public void calculate(){
        String parsed;
        Stack process;
        input = screen;
        JTree infix;
        
        if(!input.equals("Enter:")){
            parsed = input.substring(6, screen.length());
            // 1 ~ build the infix tree/stack
            while(tree.leftnode && tree.rightnode != null){// while: tree has nodes    // 2 ~ build the stack w/ infix push
                //push the input
                //process.push(parsed.charAt(i));
            }
            //extra credit +2 Labs ~ Infix Algorithm w/ order of Operations
            // 3 ~ process the input
            input = "";
            output = ?;
            screen = output;
        }
        
    }
    
    public void setInput(char c){
        input + = c;
    }
    
    public String toString(){
        return output;
    }
    
}

public class testCalculator extends JApplet implements ActionListener{
    public init(){
        calculator myCalc = new Calculator();
        // 4 ~ calculator object
    }
    // page 848
    public void actionPreformed(ActionEvent e){
        //if '=' is clicked, call myCalc.calculate();
        //if '+' is clicked, call myCalc.setInput('+');
    }
}
    
// Wikipedia - mathML - mathematical mark up language