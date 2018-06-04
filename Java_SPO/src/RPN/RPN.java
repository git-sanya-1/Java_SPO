package RPN;

import Lexer.TokenOperand;
import Lexer.TokenOperator;
import Lexer.LexemType;
import Lexer.Token;
import java.util.*;

public class RPN {
    private static Stack<TokenOperator> stack = new Stack<>();
    private static int i;
    private ArrayList<Token> output = new ArrayList<>();
    private ArrayList<Token> str;


    public RPN(ArrayList<Token> token_rpn){
        this.str=token_rpn;
    }

    private void exprHandle(){
        if(str.get(i) instanceof TokenOperand){
            output.add(str.get(i));
        }
        else if(str.get(i) instanceof TokenOperator){
            OperatorHandle();
        }
        else if(str.get(i).getType().equals("END_STR")){
            while(!stack.empty()){
                output.add(stack.pop());
            }
        }
    }

    private void OperatorHandle() {

        TokenOperator tokenOperator = (TokenOperator) str.get(i);
        if (tokenOperator.getType().equals("L_BR")) {
            stack.push(tokenOperator);
        } else if (tokenOperator.getType().equals("R_BR")) {
            while (!stack.peek().getType().equals("L_BR")) {
                output.add(stack.pop());
            }
            stack.pop();
        } else if (!stack.empty() && tokenOperator.getPriority() <= stack.peek().getPriority()) {
            while (!stack.empty()&&tokenOperator.getPriority() <= stack.peek().getPriority()) {
                output.add(stack.pop());
            }
            stack.push(tokenOperator);
        } else {
            stack.push(tokenOperator);
        }
    }
    private void cycleRPN(){
        int JmpToStart = output.size();
        int buff;
        while(!str.get(i).getType().equals("L_FIG_BR")){
            exprHandle();
            i++;
        }
        buff=output.size();
        output.add(new TokenOperand("VAR"," "));
        output.add(new TokenOperator(0,LexemType.J_C.getType(),"!F"));
        i++;
        while(!str.get(i).getType().equals("R_FIG_BR")){
            switch (str.get(i).getType()) {
                case "CYCLE":
                    cycleRPN();
                    break;
                case "PRINTLN":
                    printHandle();
                    break;
                default:
                    exprHandle();
                    break;
            }
            i++;
        }
        output.add(new TokenOperand("DIGITAL",Integer.toString(JmpToStart)));
        output.add(new TokenOperator(0,LexemType.JMP.getType(),"!"));
        output.set(buff,new TokenOperand("DIGITAL",Integer.toString(output.size())));
    }
    private void printHandle(){
        i++;
        while(!str.get(i).getType().equals("END_STR")){
            if(str.get(i) instanceof TokenOperand){
                output.add(str.get(i));
                output.add(new TokenOperator(0,"PRINTLN","println"));
            }
            i++;
        }
    }
    public void toRPN() {
        for(i = 0;i<str.size(); i++){
            switch (str.get(i).getType()) {
                case "CYCLE":
                    cycleRPN();
                    break;
                case "PRINTLN":
                    printHandle();
                    break;
                default:
                    exprHandle();
                    break;
            }
        }
        output.add(new TokenOperand("VAR","end text"));
    }
    public ArrayList<Token> getOutput(){
        return output;
    }
}