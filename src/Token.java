public class Token {
    String lexeme;
    Symbols sCode;
    int row, column;

    Token (String lexeme, Symbols sCode, int row, int column){
        this.lexeme = lexeme;
        this.sCode = sCode;
        this.row = row;
        this.column = column;

    }
}
