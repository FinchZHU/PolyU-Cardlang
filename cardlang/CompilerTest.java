package cardlang;

import cardlang.parser.*;
import cardlang.lexer.*;
import cardlang.node.*;
import cardlang.analysis.*;
import java.io.*;

public class CompilerTest {
    public static void main(String[] args) {
        try {
            Parser p = new Parser(new Lexer(new PushbackReader (new FileReader(args[0]), 1024)));
            Start tree = p.parse();
            tree.apply(new SemanticAnalyzer());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}