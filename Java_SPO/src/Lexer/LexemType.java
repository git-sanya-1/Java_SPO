package Lexer;

import java.util.regex.Pattern;

public enum LexemType {
    CYCLE("CYCLE",Pattern.compile("^while"),0),
    PRINTLN("PRINTLN",Pattern.compile("^println"),0),
    KEY_TYPE("KEY_TYPE",Pattern.compile("^type"),1),
    TYPE("TYPE",Pattern.compile("^Variable|List|HashSet"),0),
    FUNC("FUNC",Pattern.compile("^[.](add|get|remove|contains|set)"),5),
    DIGITAL("DIGITAL", Pattern.compile("^0|[1-9][0-9]*[.]?[0-9]*|0[.][0-9]*"),0),
    VAR("VAR", Pattern.compile("^[a-zA-Z]+[0-9]*[a-zA-Z]*"),0),
    COMPARATOR("COMPARATOR",Pattern.compile("^==|<|>"),1),
    ASSIGN_OP("ASSIGN_OP", Pattern.compile("^:="),2),
    OP_ADD_SUB("OP_ADD_SUB", Pattern.compile("^[+|-]"),3),
    OP_DIV_MUL("OP_DIV_MUL", Pattern.compile("^[*|/]"),4),
    WS("WS",Pattern.compile("^\\s"),0),
    L_BR("L_BR",Pattern.compile("^[(]"),0),
    J_C("J_C",Pattern.compile("^!F"),0),
    JMP("JMP",Pattern.compile("^!"),0),
    R_BR("R_BR",Pattern.compile("^[)]"),0),
    L_FIG_BR("L_FIG_BR",Pattern.compile("^[{]"),0),
    R_FIG_BR("R_FIG_BR",Pattern.compile("^[}]"),0),
    END_STR("END_STR",Pattern.compile("^[;]"),0),
    VIM("VIM",Pattern.compile("^[,]"),0);

    private String type;
    private Pattern pattern;
    private int priority;

    LexemType(String type, Pattern pattern,int priority){
        this.type = type;
        this.pattern = pattern;
        this.priority = priority;
    }

    public String getType(){
        return type;
    }
    public Pattern getPattern(){
        return pattern;
    }

    public int getPriority() {
        return priority;
    }
}