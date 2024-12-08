import java.util.Stack;
import java.util.Scanner;

public class main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        mathematicalExpression a = new mathematicalExpression();
        System.out.println("the expression is: ");
        printStack(a.getExpression());
        
        System.out.print("expression in postfix is: ");
        printStack(a.toPostFix());
            
        System.out.print("expression in prefix is: ");
        printStack(a.toPreFix());
        if(!a.getHasVariable()){
            System.out.print("the resualt is: "+ a.calculate());
        }
        else{
            boolean isRunning = true;

        while(isRunning){
            System.out.println("the entered expression has a variable ");
            System.out.println("enter 1 if you want to draw the plot of it");
            System.out.println("enter 2 if you want to calculate it with a given value for the varibale");
            System.out.println("enter -1 to exit");
            String i = input.nextLine();
            while (!i.equals("1") && !i.equals("2") && !i.equals("-1")) {
                System.out.println("Invalid input. Please enter 1, 2, or -1: ");
                i = input.nextLine();
            }
            if(i.equals("1")){
                a.graph();
            }
            else if(i.equals("2")){

                System.out.println("enter the value you want to calculate the expression with");
                double num = input.nextDouble();
                input.nextLine();
                System.out.println("the resualt is : "+ a.calculate(num));
                
            }
            else if(i.equals("-1")){
                isRunning = false;
            }
            }
        }
        
    }

    public static void printStack(Stack<String> s){
        Stack<String> tempStack = (Stack<String>) s.clone(); 
        Stack<String> reversedStack = new Stack<>();

        
        while (!tempStack.isEmpty()) {
            reversedStack.push(tempStack.pop());
        }
    
        
        while (!reversedStack.isEmpty()) {
            System.out.print(reversedStack.pop() + " ");
        }
        System.out.println();
    }
}

