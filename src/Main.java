import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Token> tokens = new ArrayList<>();
        System.out.println();
        Path filePath = Path.of("input.txt");
        String content = Files.readString(filePath);
        Lexer lex = new Lexer(content, tokens);
        lex.startAnalysis();

        Syntax syntax = new Syntax(lex.tokens);
        syntax.startAnalysis();
    }
}


