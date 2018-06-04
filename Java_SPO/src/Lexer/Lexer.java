package Lexer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    static private StringBuilder currentString = new StringBuilder();
    static private StringBuilder acc = new StringBuilder();
    static private ArrayList<Token> list_tok1 = new ArrayList<>();

    private void lex(String string){
        while(string.length()!=0) {
            for(LexemType lexemType: LexemType.values()) {
                Pattern pattern = lexemType.getPattern();
                Matcher matcher = pattern.matcher(string);
                if(matcher.lookingAt()){
                    currentString.append(matcher.group());
                    addToken(lexemType,currentString.toString());
                    acc.append(string);
                    acc.delete(0,(currentString.length()));
                    string = acc.toString();
                    currentString.setLength(0);
                    acc.setLength(0);
                    break;
                }
            }
        }
    }

    private void addToken(LexemType lexemType,String value){
        String type = lexemType.getType();
        switch (type) {
            case "DIGITAL":
            case "VAR":
            case "TYPE":
                list_tok1.add(new TokenOperand(type, value));
                break;
            case "WS":
                break;
            case "END_STR":
            case "VIM":
            case "CYCLE":
            case "L_FIG_BR":
            case "R_FIG_BR":
                list_tok1.add(new Token(type, value));
                break;
            default:
                list_tok1.add(new TokenOperator(lexemType.getPriority(),type,value));
                break;
        }
    }

    public ArrayList<Token> getTokenList(String tok1){
        lex(tok1);
        return list_tok1;
    }
}