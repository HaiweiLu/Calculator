import java.util.Stack;

import static java.lang.Math.*;

public class Main {

    private static String[] op = {"+", "-", "*", "/"};// Operation set

    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++) {
            String question = makeFormula();
            String ret = solve(question);
        }
        // String question = makeFormula();
        // System.out.println(question);
        // String ret = solve(question);
        // System.out.println(ret);
    }

    public static String makeFormula() {
        StringBuilder build = new StringBuilder();
        int count = (int) (random() * 2) + 1; // generate random count
        int start = 0;
        int number1 = (int) (random() * 99) + 1;
        build.append(number1);
        while (start <= count) {
            int operation = (int) (random() * 3); // generate operator
            int number2 = (int) (random() * 99) + 1;
            build.append(op[operation]).append(number2);
            start++;
        }
        return build.toString();
    }

    public static String solve(String formula) {
        Stack<String> tempStack = new Stack<>();//Store number or operator
        Stack<Character> operatorStack = new Stack<>();//Store operator
        int len = formula.length();
        int k = 0;
        for (int j = -1; j < len - 1; j++) {
            char formulaChar = formula.charAt(j + 1);
            if (j == len - 2 || formulaChar == '+' || formulaChar == '-' || formulaChar == '/' || formulaChar == '*') {
                if (j == len - 2) {
                    tempStack.push(formula.substring(k));
                } else {
                    if (k < j) {
                        tempStack.push(formula.substring(k, j + 1));
                    }
                    if (operatorStack.empty()) {
                        operatorStack.push(formulaChar); //if operatorStack is empty, store it
                    } else {
                        char stackChar = operatorStack.peek();
                        if ((stackChar == '+' || stackChar == '-')
                                && (formulaChar == '*' || formulaChar == '/')) {
                            operatorStack.push(formulaChar);
                        } else {
                            tempStack.push(operatorStack.pop().toString());
                            operatorStack.push(formulaChar);
                        }
                    }
                }
                k = j + 2;
            }
        }
        while (!operatorStack.empty()) { // Append remaining operators
            tempStack.push(operatorStack.pop().toString());
        }
        Stack<String> calcStack = new Stack<>();
        for (String peekChar : tempStack) { // Reverse traversing of stack
            if (!"+".equals(peekChar) && !"-".equals(peekChar) && !"/".equals(peekChar) && !"*".equals(peekChar)) {
                calcStack.push(peekChar); // Push number to stack
            } else {
                int a1 = 0;
                int b1 = 0;
                if (!calcStack.empty()) {
                    b1 = Integer.parseInt(calcStack.pop());
                }
                if (!calcStack.empty()) {
                    a1 = Integer.parseInt(calcStack.pop());
                }
                switch (peekChar) {
                    case "+":
                        calcStack.push(String.valueOf(a1 + b1));
                        break;
                    case "-":
                        calcStack.push(String.valueOf(a1 - b1));
                        break;
                    case "*":
                        calcStack.push(String.valueOf(a1 * b1));
                        break;
                    default:
                        calcStack.push(String.valueOf(a1 / b1));
                        break;
                }
            }
        }
        return formula + "=" + calcStack.pop();
    }
}
