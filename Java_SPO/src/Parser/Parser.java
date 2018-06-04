package Parser;

import Lexer.Token;

import java.util.ArrayList;

public class Parser {

    private static final Token all = new Token("all", "");
    private final ArrayList<Token> tokens;
    private int pos;
    private final int size;

    public Parser(ArrayList<Token> token) {
        this.tokens = token;
        size = tokens.size();
    }

    private Token get(int relative) {
        final int position = pos + relative;
        if (position >= size) return all;
        return tokens.get(position);
    }

    private boolean match(String type) {
        final Token current = get(0);
        if ((type.equals(current.getType()))) {
            pos++;
            return true;
        } else {
            return false;
        }
    }

    public void parse() {
        lang();
    }

    private void lang() {
        while (get(0) != all) {
            expr();
        }
    }

    private void expr() {
        if (match("VAR")) {
            if (match("KEY_TYPE")) {
                type();
            } else if (match("ASSIGN_OP")) {
                assign();
            } else if(match("FUNC")){
                func();
            } else {
                throw new RuntimeException("expr dont correct");
            }
        } else if (match("CYCLE")) {
            cycle();
        } else if (match("PRINTLN")) {
            println();
        } else {
            throw new RuntimeException("No any doing ");
        }
    }


    private void assign() {
        assign_value();
        if (!match("END_STR")) {
            throw new RuntimeException("Dont find end string");
        }
    }

    private void assign_value() {
        math_expr();
        while (true) {
            if ((match("OP_DIV_MUL")) || (match("OP_ADD_SUB"))) {
                math_expr();
            } else {
                break;
            }
        }
    }

    private void math_expr() {
        try {
            add_expr();
        } catch (RuntimeException e) {
            caclulate();
        }
    }

    private void add_expr() throws RuntimeException {
        value();
        while (true) {
            if ((match("OP_DIV_MUL")) || (match("OP_ADD_SUB"))) {
                value();
            } else {
                break;
            }
        }
    }

    private void caclulate() {
        while (!(match("R_BR"))) {
            if (match("L_BR")) {
                assign_value();
            } else {
                throw new RuntimeException("Dont have )");
            }
        }
    }

    private void value() throws RuntimeException {
        if (!match("DIGITAL") ) {
            if(match("VAR")){
                func_br();
            }else {
                throw new RuntimeException("No any value");
            }
        }
    }

    private void func_br() {
        if(match("L_BR")) {
            value();
        }
        if(!match("R_BR")){
            throw new RuntimeException("No )");
        }
    }



    private void cycle() {
        compare();
        body();
    }

    private void compare() {
        if ((match("L_BR")) && (match("VAR")) && (match("COMPARATOR"))) {
            value();
        } else {
            throw new RuntimeException("Compare is not correct");
        }
        if (!match("R_BR")) {
            throw new RuntimeException("No )");
        }
    }

    private void body() {
        if (match("L_FIG_BR")) {
            while (!match("R_FIG_BR")) {
                expr();
            }
        } else {
            throw new RuntimeException("Body is not correct");
        }
    }

    private void println() {
        if (match("L_BR")) {
            value();
            while (!((match("R_BR")) && (match("END_STR")))) {
                if (match("VIM")) {
                    value();
                } else {
                    throw new RuntimeException("Println dont write to next");
                }
            }
        } else {
            throw new RuntimeException("Print dont any val ");
        }
    }

    private void type() {
        if (match("TYPE")) {
            if (!match("END_STR")) {
                throw new RuntimeException("TYPE ne sformirovan");
            }
        } else {
            throw new RuntimeException("ERROR - type is not correct");
        }
    }

    private void func() {
        int buffer = pos - 1;
        if (match("L_BR")) {
            value();
            if(get(buffer-pos).getValue().equals(".set")){
                if(!match("VIM")){
                    throw new RuntimeException(", yes , next value  no");
                }else {
                    value();
                }
            } if(!(match("R_BR")&&match("END_STR"))){
                throw new RuntimeException("No any value");
            }
        } else {
            throw new RuntimeException("func is zero");
        }
    }
}