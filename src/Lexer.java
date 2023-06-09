import java.util.ArrayList;

public class Lexer {
    //File or String to process
    // Tokens we get in order
    String input;
    String lexeme = "";
    int i = 1, j = 0;
    int currChar = 0;
    Token token;
    Symbols symbol;
    ArrayList<Token> tokens;

    Lexer(String input, ArrayList<Token> tokens){
        this.input = input;
        this.tokens = tokens;
    }

    void startAnalysis(){
        //skips whitespace
        while(currChar < input.length()) {
            skipWhite();
            lex();
        }

        token = new Token(lexeme, Symbols.EOF, i, ++j);
        tokens.add(token);
    }

    void error(){
        lexeme += input.charAt(currChar);
        System.out.println("Unknown Symbol: " + lexeme);
        System.exit(1);
    }

    boolean isReservedWords(){

        if(lexeme.equals("true")){
            token = new Token(lexeme, Symbols.TRUE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("false")){
            token = new Token(lexeme, Symbols.FALSE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("int")){
            token = new Token(lexeme, Symbols.INT_TYPE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("float")){
            token = new Token(lexeme, Symbols.FLOAT_TYPE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("String")){
            token = new Token(lexeme, Symbols.STRING_TYPE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("char")){
            token = new Token(lexeme, Symbols.CHAR_TYPE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("boolean")){
            token = new Token(lexeme, Symbols.BOOL_TYPE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("while")){
            token = new Token(lexeme, Symbols.WHILE_CODE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("while")){
            token = new Token(lexeme, Symbols.WHILE_CODE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("if")){
            token = new Token(lexeme, Symbols.IF_CODE, i, ++j);
            tokens.add(token);
            return true;
        }

        if(lexeme.equals("else")){
            token = new Token(lexeme, Symbols.ELSE_CODE, i, ++j);
            tokens.add(token);
            return true;
        }

        return false;
    }

    boolean lookup(){
        if(Character.isWhitespace(input.charAt(currChar))){
            if(input.charAt(currChar) == '\n'){
                i++;
                j = 0;
            }
            return true;
        }

        switch(input.charAt(currChar)){
            case '+':
                token = new Token("+", Symbols.ADD_OP, i, ++j);
                tokens.add(token);
                return true;
            case '-':
                token = new Token("-", Symbols.MINUS_OP, i, ++j);
                tokens.add(token);
                return true;
            case '*':
                token = new Token("*", Symbols.MULT_OP, i, ++j);
                tokens.add(token);
                return true;
            case '/':
                token = new Token("/", Symbols.DIV_OP, i, ++j);
                tokens.add(token);
                return true;
            case '%':
                token = new Token("%", Symbols.MOD_OP, i, ++j);
                tokens.add(token);
                return true;
            case '^':
                token = new Token("^", Symbols.EXP_OP, i, ++j);
                tokens.add(token);
                return true;
            case '(':
                token = new Token("(", Symbols.LEFT_PAREN, i, ++j);
                tokens.add(token);
                return true;
            case ')':
                token = new Token(")", Symbols.RIGHT_PAREN, i, ++j);
                tokens.add(token);
                return true;
            case '{':
                token = new Token("{", Symbols.LEFT_CURLY, i, ++j);
                tokens.add(token);
                return true;
            case '}':
                token = new Token("}", Symbols.RIGHT_CURLY, i, ++j);
                tokens.add(token);
                return true;
            case ',':
                token = new Token(",", Symbols.COMMA, i, ++j);
                tokens.add(token);
                return true;
            case '!':
                if(input.charAt(currChar+1) == '='){
                    token = new Token("!=", Symbols.NOT_EQUALS, i, ++j);
                    tokens.add(token);
                    currChar++;
                }else{
                    token = new Token("!", Symbols.NOT_OP, i, ++j);
                    tokens.add(token);
                }
                return true;
            case '=':
                if(input.charAt(currChar+1) == '='){
                    token = new Token("==", Symbols.EQUALS, i, ++j);
                    tokens.add(token);
                    currChar++;
                }else{
                    token = new Token("=", Symbols.ASSIGN, i, ++j);
                    tokens.add(token);
                }
                return true;
            case '<':
                if(input.charAt(currChar+1) == '='){
                    token = new Token("<=", Symbols.LESS_AND_EQUAL, i, ++j);
                    tokens.add(token);
                    currChar++;
                }else{
                    token = new Token("<", Symbols.LESS_THAN, i, ++j);
                    tokens.add(token);
                }
                return true;
            case '>':
                if(input.charAt(currChar+1) == '='){
                    token = new Token(">=", Symbols.GREATER_AND_EQUAL, i, ++j);
                    tokens.add(token);
                    currChar++;
                }else{
                    token = new Token(">", Symbols.GREATER_THAN, i, ++j);
                    tokens.add(token);
                }
                return true;
            case '&':
                if(input.charAt(currChar+1) == '&'){
                    token = new Token("&&", Symbols.AND_OP, i, ++j);
                    tokens.add(token);
                    currChar++;
                    return true;
                }
                break;
            case '|':
                if(input.charAt(currChar+1) == '|'){
                    token = new Token("||", Symbols.OR_OP, i, ++j);
                    tokens.add(token);
                    currChar++;
                    return true;
                }
                break;
            case ';':
                token = new Token(";", Symbols.SEMICOLON, i, ++j);
                tokens.add(token);
                return true;
        }
        return false;
    }

    // DFA for Identifiers and reserved words
    void tokenizeID(){
        Identifiers currentId = Identifiers.start;
        while(currChar < input.length() && currentId != Identifiers.t){
            switch (currentId){
                case start:
                    if(Character.isLetter(input.charAt(currChar)) || input.charAt(currChar) == '_'){
                        currentId = Identifiers.a;
                        lexeme += input.charAt(currChar);
                    }else{
                        if(!isReservedWords()) {
                            token = new Token(lexeme, Symbols.IDENT, i, ++j);
                            tokens.add(token);
                        }

                        if(!lookup()) {
                            error();
                        }
                        lexeme = "";
                        currentId = Identifiers.t;
                    }
                    break;
                case a:
                    if(Character.isDigit(input.charAt(currChar)) || Character.isLetter(input.charAt(currChar)) || input.charAt(currChar) =='_'){
                        currentId = Identifiers.a;
                        lexeme += input.charAt(currChar);
                    }else{
                        if(!isReservedWords()) {
                            token = new Token(lexeme, Symbols.IDENT, i, ++j);
                            tokens.add(token);
                        }

                        if(!lookup()) {
                            error();
                        }
                        lexeme = "";
                        currentId = Identifiers.t;
                    }
                    break;
                case t:

            }
            currChar++;
        }

        if(currChar >= input.length() && currentId != Identifiers.t){
            token = new Token(lexeme, Symbols.IDENT, i, ++j);
            tokens.add(token);
        }
    }

    // DFA for integer and float literals
    void tokenizeNum(){
        Numbers currentNum = Numbers.start;
        while(currChar < input.length() && currentNum != Numbers.t){
            switch (currentNum){
                case start:
                    if(input.charAt(currChar) == '+' || input.charAt(currChar) == '-'){
                        currentNum = Numbers.sign;
                        lexeme += input.charAt(currChar);
                    }else if(Character.isDigit(input.charAt(currChar))){
                        currentNum = Numbers.c;
                        lexeme += input.charAt(currChar);
                    }else if(input.charAt(currChar) == '.'){
                        currentNum = Numbers.b;
                        lexeme += input.charAt(currChar);
                    }else{
                        error();
                        lexeme = "";
                        currentNum = Numbers.t;
                    }
                    break;
                case sign:
                    if(Character.isDigit(input.charAt(currChar))){
                        currentNum = Numbers.c;
                        lexeme += input.charAt(currChar);
                    }else if(input.charAt(currChar) == '.'){
                        currentNum = Numbers.b;
                        lexeme += input.charAt(currChar);
                    }else if(input.charAt(currChar) == '+' || input.charAt(currChar) == '-'){
                        if(lexeme.equals("+")){
                            token = new Token(lexeme, Symbols.ADD_OP, i, ++j);
                            tokens.add(token);
                        }
                        if (lexeme.equals("-")){
                            token = new Token(lexeme, Symbols.MINUS_OP, i, ++j);
                            tokens.add(token);
                        }
                        lexeme = "";
                        lexeme += input.charAt(currChar);
                    }else{
                        if(lexeme.equals("+")){
                            token = new Token(lexeme, Symbols.ADD_OP, i, ++j);
                            tokens.add(token);
                        }
                        if (lexeme.equals("-")){
                            token = new Token(lexeme, Symbols.MINUS_OP, i, ++j);
                            tokens.add(token);
                        }
                        return;
                    }
                    break;
                case b:
                    if(Character.isDigit(input.charAt(currChar))){
                        currentNum = Numbers.d;
                        lexeme += input.charAt(currChar);
                    }else{
                        error();
                        lexeme = "";
                        currentNum = Numbers.t;
                    }
                    break;
                case c:
                    if(Character.isDigit(input.charAt(currChar))){
                        currentNum = Numbers.c;
                        lexeme += input.charAt(currChar);
                    }else if(input.charAt(currChar) == '.'){
                        currentNum = Numbers.d;
                        lexeme += input.charAt(currChar);
                    }else if(input.charAt(currChar) == 'e' || input.charAt(currChar) == 'E'){
                        currentNum = Numbers.e;
                        lexeme += input.charAt(currChar);
                    }else{
                        token = new Token(lexeme, Symbols.INT_LIT, i, ++j);
                        tokens.add(token);
                        if(!lookup()) {
                            error();
                        }
                        lexeme = "";
                        currentNum = Numbers.t;
                    }
                    break;
                case d:
                    if(Character.isDigit(input.charAt(currChar))){
                        currentNum = Numbers.d;
                        lexeme += input.charAt(currChar);
                    }else if(input.charAt(currChar) == 'e' || input.charAt(currChar) == 'E'){
                        currentNum = Numbers.e;
                        lexeme += input.charAt(currChar);
                    }else if(input.charAt(currChar) == 'L' || input.charAt(currChar) == 'l' || input.charAt(currChar) == 'F' || input.charAt(currChar) == 'f'){
                        currentNum = Numbers.h;
                        lexeme += input.charAt(currChar);
                    }else{
                        token = new Token(lexeme, symbol.FLOAT_LIT, i, ++j);
                        tokens.add(token);
                        if(!lookup()) {
                            error();
                        }
                        lexeme = "";
                        currentNum = Numbers.t;
                    }
                    break;
                case e:
                    if(input.charAt(currChar) == '+' || input.charAt(currChar) == '-'){
                        currentNum = Numbers.f;
                        lexeme += input.charAt(currChar);
                    }else if(Character.isDigit(input.charAt(currChar))){
                        currentNum = Numbers.g;
                        lexeme += input.charAt(currChar);
                    }else{
                        error();
                        lexeme = "";
                        currentNum = Numbers.t;
                    }
                    break;
                case f:
                    if(Character.isDigit(input.charAt(currChar))){
                        currentNum = Numbers.g;
                        lexeme += input.charAt(currChar);
                    }else{
                        error();
                        lexeme = "";
                        currentNum = Numbers.t;
                    }
                    break;
                case g:
                    if(Character.isDigit(input.charAt(currChar))){
                        currentNum = Numbers.g;
                        lexeme += input.charAt(currChar);
                    }else if(input.charAt(currChar) == 'L' || input.charAt(currChar) == 'l' || input.charAt(currChar) == 'F' || input.charAt(currChar) == 'f'){
                        currentNum = Numbers.h;
                        lexeme += input.charAt(currChar);
                    }else{
                        token = new Token(lexeme, Symbols.FLOAT_LIT, i, ++j);
                        tokens.add(token);
                        if(!lookup()) {
                            error();
                        }
                        lexeme = "";
                        currentNum = Numbers.t;
                    }
                    break;
                case h:
                    token = new Token(lexeme, Symbols.FLOAT_LIT, i, ++j);
                    tokens.add(token);
                    if(!lookup()) {
                        error();
                    }
                    lexeme = "";
                    currentNum = Numbers.t;
                    break;
                case t:

            }
            currChar++;
        }

        if(currChar >= input.length() && currentNum == Numbers.c){
            token = new Token(lexeme, Symbols.INT_LIT, i, ++j);
            tokens.add(token);
        }else if(currChar >= input.length() && (currentNum == Numbers.d || currentNum == Numbers.g || currentNum == Numbers.h)){
            token = new Token(lexeme, Symbols.FLOAT_LIT, i, ++j);
            tokens.add(token);
        }
    }

    // DFA for String and Character literals
    void tokenizeChars(){
        Characters currentChar = Characters.a;

        while(currChar < input.length() && currentChar != currentChar.t){
            switch (currentChar){
                case a:
                    if(input.charAt(currChar) == '\"'){
                        lexeme += input.charAt(currChar);
                        currentChar = Characters.b;
                    }else if(input.charAt(currChar) == '\''){
                        lexeme += input.charAt(currChar);
                        currentChar = Characters.c;
                    }else{
                        error();
                    }
                    break;
                case b:
                    lexeme += input.charAt(currChar);
                    if(input.charAt(currChar) == '\"'){
                        token = new Token(lexeme, Symbols.STRING_LIT, i, ++j);
                        tokens.add(token);

                        lexeme = "";

                        currentChar = Characters.t;
                    }
                    break;
                case c:
                    lexeme += input.charAt(currChar);
                    if(input.charAt(currChar) == '\\'){
                        currentChar = Characters.e;
                    }else{
                        currentChar = Characters.d;
                    }
                    break;
                case d:
                    if(input.charAt(currChar) == '\'') {
                        lexeme += input.charAt(currChar);

                        token = new Token(lexeme, Symbols.CHAR_LIT, i, ++j);
                        tokens.add(token);

                        lexeme = "";

                        currentChar = Characters.t;
                    }else{
                        error();
                    }
                    break;
                case e:
                    switch(input.charAt(currChar)){
                        case 't':
                        case 'b':
                        case 'n':
                        case 'r':
                        case 'f':
                        case '\'':
                        case '\"':
                        case '\\':
                            lexeme += input.charAt(currChar);
                            currentChar = Characters.d;
                            break;
                        default:
                            error();
                            break;
                    }
                case t:
            }
            currChar++;
        }

        if(currChar >= input.length() && (currentChar != Characters.t)){
            System.out.println("String was not closed!\nReached EOF");
            System.exit(1);
        }

    }

    //Automata for Single line and Multi line Comments
    void ignoreComment(){
        Comments currState = Comments.a;

        while(currChar < input.length() && currState != Comments.t){
            switch (currState){
                case a:
                    if (input.charAt(currChar) == '/'){
                        currState = Comments.b;
                        lexeme += input.charAt(currChar);
                    }else{
                        currState = Comments.t;
                    }
                    break;
                case b:
                    if (input.charAt(currChar) == '/'){
                        currState = Comments.c;
                    }else if (input.charAt(currChar) == '*'){
                        currState = Comments.d;
                    }else{
                        error();
                    }
                    break;
                case c:
                    if (input.charAt(currChar) == '\n'){
                        currState = Comments.t;
                    }
                    break;
                case d:
                    if (input.charAt(currChar) == '*'){
                        currState = Comments.e;
                    }
                    break;
                case e:
                    if (input.charAt(currChar) == '/'){
                        currState = Comments.t;
                    }else{
                        currState = Comments.d;
                    }
                    break;
                case t:
            }
            currChar++;
        }

        if(currChar >= input.length() && ((currState != Comments.c) && (currState != Comments.t))){
            System.out.println("Comment was not closed!\nReached EOF");
            System.exit(1);
        }
    }

    void skipWhite(){
        if(input.charAt(currChar) != '\n' && currChar < input.length() && Character.isWhitespace(input.charAt(currChar))){
            currChar++;
        }
    }

    void lex(){
        if(currChar < input.length()) {
            if (Character.isLetter(input.charAt(currChar)) || input.charAt(currChar) == '_') {
                tokenizeID();
            } else if (Character.isDigit(input.charAt(currChar)) || (currChar < input.length()-1 && Character.isDigit(input.charAt(currChar + 1)) && (input.charAt(currChar) == '+' || input.charAt(currChar) == '-')) || input.charAt(currChar) == '.') {
                tokenizeNum();
            } else if(input.charAt(currChar) == '/'){
                ignoreComment();
                lexeme = "";
            } else if(input.charAt(currChar) == '\'' || input.charAt(currChar) == '\"'){
                tokenizeChars();
            }else {
                if(!lookup()){
                    error();
                }
                currChar++;
            }
        }
    }
}
