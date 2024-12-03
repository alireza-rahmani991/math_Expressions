import java.util.Stack;
import java.util.Scanner;


public class mathematicalExpression {
    // private Stack<String> expression = new Stack<>();
    private String expression = "";

    mathematicalExpression(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("enter the expression in infix order");

        String input = scanner.nextLine();
        while (!input.matches(("-?\\d+")) && !input.equals("x") && !input.equals(")") &&!input.equals("(") ) {
            System.out.println("infix expression should start with a number or variable please enter you input again");
            input = scanner.nextLine();
        }

        int openParenthesesCount = 0;

        boolean prevIsDigit = false;
        while(!input.equals("END")){
            if((input.matches("-?\\d+") && !prevIsDigit )|| (input.matches("[+\\-^*/]") && prevIsDigit) || (input.equals("x") && !prevIsDigit )){

                if(!prevIsDigit && !input.equals("x")){
                    int num = Integer.parseInt(input);
                    if(num < 0){
                        // expression.push("!");
                        expression += "!";       
                    }
                }

                if (input.startsWith("-") && !prevIsDigit) {
                    // expression.push(input.substring(1)); 
                    expression += input.substring(1);
                }
                else{
                    // expression.push(input);
                    expression += input;
                }
                
                System.out.println("enter the next element or END to exit");
                prevIsDigit = !prevIsDigit;

            }
            
            else if(input.equals("(")){
                expression += input;
                openParenthesesCount++;
                System.out.println("enter the next element or END to exit");
            }

            else if(input.equals(")")){
                if(openParenthesesCount > 0){
                    expression += input;
                    openParenthesesCount--;
                }
                else{
                    System.out.println("no open parenthese to close");
                }
                
                System.out.println("enter the next element or END to exit");
            }

            else if(input.matches("-?\\d+") && prevIsDigit){
                System.out.println("after a number or closing Parentheses or variable there must be an operator please enter the element again or END to exit");
            }
            else if(input.matches("[+\\-^*/]") && !prevIsDigit){
                System.out.println("after an operator or opening Parentheses there must be a number or variable please enter the element again or END to exit");
            }
            else{
                System.out.println("invalid input enter END if the exppression is finished otherwise ");
            }

            input = scanner.nextLine();
        }

        if(!prevIsDigit){
            System.out.println("there was no operand after the last operator so it was removed");
            expression = expression.substring(0, expression.length() - 1); 
        }

        if(openParenthesesCount > 0){
            for(int i = 0; i < openParenthesesCount; i++){
                expression += ")";
            }
        }
        System.out.println("the expression is: " + expression);
        // printStack();

    }

    public String toPostFix(){
        String res = "";
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char element = expression.charAt(i);

            if ((element >= '0' && element <= '9') || element == '!'  || element == 'x')
            {
                res += element;
            }

            else if (element == '(')
                operators.push('(');

        
            else if (element == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    res += operators.pop();
                }
                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop(); 
                }
            }

        
            else {
                while (!operators.isEmpty() && (prec(element) < prec(operators.peek()) || prec(element) == prec(operators.peek()))) {
                    res += operators.pop();
                }
                operators.push(element);
            }
        }
        while (!operators.isEmpty()) {
            res += operators.pop();
        }
    
    return res;
    }

    public String toPreFix(){
        String temp = expression;
        StringBuilder reversed = new StringBuilder();
        for (int i = expression.length() - 1; i >= 0; i--) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                reversed.append(')');
            } else if (ch == ')') {
                reversed.append('(');
            } else {
                reversed.append(ch);
            }
        }
        expression = reversed.toString();

        String postfix = toPostFix();
        String prefix = new StringBuilder(postfix).reverse().toString();
        expression = temp;
        return prefix;
        
    }

    // public int calculate(){

    // }

    int prec(char c) {
        if (c == '^')
            return 3;
        else if (c == '/' || c == '*')
            return 2;
        else if (c == '+' || c == '-')
            return 1;
        else
            return -1;
    }

    // public void printStack() {
    //     for (String item : expression) {
    //         System.out.print(item);
    //     }
    // }
}


class main{
    public static void main(String[] args){

        mathematicalExpression a = new mathematicalExpression();
        System.out.println(a.toPostFix());
        System.out.println(a.toPreFix());
    }
}