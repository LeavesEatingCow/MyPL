import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Token> tokens = new ArrayList<>();
        System.out.println();
        Path filePath = Path.of("input.txt");
        String content = Files.readString(filePath);
        Lexer lex = new Lexer(content, tokens);
        lex.startAnalysis();

        /*
        System.out.print("[");
        for(Token t:tokens){
            System.out.print(t.lexeme + ", ");
        }
        System.out.println("]");
        */
        Syntax syntax = new Syntax(lex.tokens);
        syntax.startAnalysis();
    }
}


