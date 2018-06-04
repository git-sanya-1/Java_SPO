package Lexer;

public class TokenOperand extends Token {
    public TokenOperand(String type, String value){
        super(type,value);
    }
    public String toString(){
        return this.getValue();
    }
}