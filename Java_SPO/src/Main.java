import Lexer.Lexer;
import Parser.Parser;
import Lexer.Token;
import RPN.RPN;
import StackMachine.StackMachine;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main{

    public static void main(String[] args) throws IOException {
        ArrayList<Token> tokens;
        final String input = new String(Files.readAllBytes(Paths.get("C:/Users/User/IdeaProjects/Java_SPO/src", "in.txt")),"UTF-8");
        Lexer lexer = new Lexer();
        tokens = lexer.getTokenList(input);
        Parser parser = new Parser(tokens);
        parser.parse();
        RPN rpn = new RPN(tokens);
        rpn.toRPN();
        StackMachine stackMachine = new StackMachine(rpn.getOutput());
        stackMachine.calculate();
    }
}