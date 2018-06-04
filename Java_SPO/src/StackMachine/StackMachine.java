package StackMachine;

import Lexer.Token;
import HashSet.HashSet;
import LinkedList.LinkedList;
import Lexer.TokenOperand;
import Lexer.TokenOperator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class StackMachine {
    private ArrayList<Token> tokens;
    private Stack<Token> stackMachine = new Stack<>();
    private HashMap<String, Variable> VarTable = new HashMap<>();
    private static int i;
    private static Variable eq;
    private LinkedList list;
    private HashSet hset;

    public StackMachine(ArrayList<Token> tokens_new) {
        this.tokens = tokens_new;
    }

    public void calculate() {
        for (i = 0; i < tokens.size() - 1; i++)
            if (tokens.get(i) instanceof TokenOperand) {
                stackMachine.push(tokens.get(i));
            } else if (tokens.get(i) instanceof TokenOperator) {
                String value = tokens.get(i).getValue();
                if (tokens.get(i).getType().equals("FUNC") || value.equals(".get"))
                    func(value);
                else
                    else_op(value);
            } else if (tokens.get(i).getValue().equals("end text")) break;
    }

    private float getOperand() {
        if (stackMachine.peek().getType().equals("VAR")) {
            String var = stackMachine.pop().getValue();
            try {
                eq = VarTable.get(var);
                return eq.getValue();
            } catch (NullPointerException n) {
                throw new NullPointerException("This var -->" + var + " dont init");
            }

        } else {
            return Float.parseFloat(stackMachine.pop().getValue());
        }

    }

    private void num_op(float a, float b, String value) {
        float finish;
        Integer comp;
        switch (value) {
            case "+":
                finish = b + a;
                stackMachine.push(new TokenOperand("DIGITAL", Float.toString(finish)));
                break;
            case "-":
                finish = b - a;
                stackMachine.push(new TokenOperand("DIGITAL", Float.toString(finish)));
                break;
            case "/":
                finish = b / a;
                stackMachine.push(new TokenOperand("DIGITAL", Float.toString(finish)));
                break;
            case "*":
                finish = b * a;
                stackMachine.push(new TokenOperand("DIGITAL", Float.toString(finish)));
                break;
            case ":=":
                eq.set_func(a);
                break;
            case "==":
                comp = Boolean.compare(b == a, false);
                stackMachine.push(new TokenOperand("DIGITAL", comp.toString()));
                break;
            case ">":
                comp = Boolean.compare(b > a, false);
                stackMachine.push(new TokenOperand("DIGITAL", comp.toString()));
                break;
            case "<":
                comp = Boolean.compare(b < a, false);
                stackMachine.push(new TokenOperand("DIGITAL", comp.toString()));
                break;
            default:
                break;
        }


    }

    private void else_op(String value) {
        switch (value) {
            case "!F":
                int go_f = Integer.parseInt(stackMachine.pop().getValue());
                i = stackMachine.pop().getValue().equals("0") ? go_f - 1 : i;
                break;
            case "!":
                i = Integer.parseInt(stackMachine.pop().getValue()) - 1;
                break;
            case "println":
                Variable printer = VarTable.get(stackMachine.peek().getValue());
                if (printer == null) {
                    throw new NullPointerException("Label dont print, because --> " + stackMachine.peek().getValue() + " is dont have");
                } else if (printer.getType().equals("List")) {
                    lable_list(printer.getList(), stackMachine.pop().getValue());
                } else {
                    lable(stackMachine.pop().getValue());
                }
                break;
            case "type":
                String type_is = stackMachine.pop().getValue();
                switch (type_is) {
                    case "List":
                        VarTable.put(stackMachine.pop().getValue(), new Variable(type_is, new LinkedList()));
                        break;
                    case "HashSet":
                        VarTable.put(stackMachine.pop().getValue(), new Variable(type_is, new HashSet()));
                        break;
                    default:
                        VarTable.put(stackMachine.pop().getValue(), new Variable(type_is, 0));
                        break;
                }
                break;

            default:
                num_op(getOperand(), getOperand(), value);
                break;
        }
    }

    private void lable(String string) {
        try {
            System.out.println(VarTable.get(string).getType() + " " + string + " := " + VarTable.get(string).getValue() + ";");
        } catch (NullPointerException e) {
            throw new NullPointerException("This var --->"+ string  + "dont init!");
        }
    }

    private void lable_list(LinkedList list, String varName) {
        System.out.print("List " + varName + " : ");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i)+"; " );
        }
        System.out.print("\n");
    }

    private void func(String value) {
        float elem2 = getOperand();
        int elem1 = 0;
        if (value.equals(".set")) {
            elem1 = Integer.parseInt(stackMachine.pop().getValue());
        }
        String varName = stackMachine.peek().getValue();
        Variable variable = VarTable.get(varName);
        try {
            if (variable.getList() != null) {
                list = VarTable.get(stackMachine.pop().getValue()).getList();
            } else if (variable.getSet() != null) {
                hset = VarTable.get(stackMachine.pop().getValue()).getSet();
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("This func is not corrrect");
        }

        switch (value) {
            case ".add":
                if (variable.getList() != null) {
                    list.add(elem2);
                } else if (variable.getSet() != null) {
                    hset.add(elem2);
                }
                break;

            case ".remove":
                if (variable.getList() != null) {
                    list.remove(elem2);
                } else if (variable.getSet() != null) {
                    hset.remove(elem2);
                }
                break;
            case ".contains":
                if (variable.getList() != null) {
                    System.out.println("List " + varName + " contains " + elem2 + "' --> " + list.contains(elem2));
                } else if (variable.getSet() != null) {
                    System.out.println("HashSet " + varName + " contains " + elem2 + " --> " + hset.contains(elem2));
                }
                break;
            case ".get":
                if (variable.getList() != null) {
                    TokenOperand token = new TokenOperand("DIGITAL", String.valueOf(list.get((int) elem2)));
                    stackMachine.push(token);
                } else if (variable.getSet() != null) {
                    throw new NullPointerException("This func is not corrrect to HashSet");
                }

                break;

            case ".set":

                if (variable.getList() != null) {
                    list.set(elem1, elem2);
                } else if (variable.getSet() != null) {
                    throw new NullPointerException("This func is not corrrect to HashSet");
                }
                break;
            default:
                break;
        }
    }

}