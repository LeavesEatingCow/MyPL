import java.util.ArrayList;

public class Syntax {
    ArrayList<Token> tokens;
    int i = 0;
    Symbols nextToken;
    Syntax(ArrayList<Token> tokens){
        this.tokens = tokens;
        nextToken = tokens.get(i).sCode;
    }

    void startAnalysis(){
        stmt();
        if(i != tokens.size() - 1) {
            System.out.println("Unexpected symbol: " + tokens.get(i + 1).lexeme + "\nPosition: [" + tokens.get(i + 1).row + ":" + tokens.get(i + 1).column + "]");
        }else{
            System.out.println("Successfully Processed!");
        }
    }

    void stmt(){
        if(nextToken == Symbols.WHILE_CODE){
            whileLoop();
        }else if(nextToken == Symbols.IF_CODE){
            ifStmt();
        }else if(nextToken == Symbols.LEFT_CURLY){
            block();
        }else if(nextToken == Symbols.IDENT){
            assign();
        }else if(nextToken == Symbols.DATATYPE){
            declare();
        }else{
            System.out.println("Unexpected symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
            System.exit(1);
        }
    }

    void stmtList(){
        if(nextToken != Symbols.RIGHT_CURLY) {
            while(nextToken == Symbols.IF_CODE || nextToken == Symbols.LEFT_CURLY || nextToken == Symbols.IDENT || nextToken == Symbols.INT_LIT || nextToken == Symbols.FLOAT_LIT || nextToken == Symbols.LEFT_PAREN || nextToken == Symbols.WHILE_CODE) {
                stmt();
                if(nextToken  == Symbols.RIGHT_CURLY){
                    getNextToken();
                }
                if(nextToken  == Symbols.EOF){
                    return;
                }
                if(nextToken != Symbols.SEMICOLON){
                    System.out.println("Expected a \";\" in statement list!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                    System.exit(1);
                }else{
                    getNextToken();
                }
            }
        }
    }

    void whileLoop(){
        if(nextToken != Symbols.WHILE_CODE){
            System.out.println("Expected \"while\" in loop!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
            System.exit(1);
        }else{
            getNextToken();
            if(nextToken != Symbols.LEFT_PAREN){
                System.out.println("Expected \"(\" in loop!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                System.exit(1);
            }else{
                getNextToken();
                boolExpr();
                if(nextToken !=Symbols.RIGHT_PAREN){
                    System.out.println("Expected \")\" in loop!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                    System.exit(1);
                }

                getNextToken();

                if(nextToken == Symbols.LEFT_CURLY){
                    block();
                }else{
                    stmt();
                    if(nextToken != Symbols.SEMICOLON){
                        System.out.println("Expected \";\" in loop!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                        System.exit(1);
                    }
                }
            }
        }
    }

    void ifStmt(){
        if(nextToken != Symbols.IF_CODE){
            System.out.println("Expected \"if\" in loop!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
            System.exit(1);
        }else{
            getNextToken();
            if(nextToken != Symbols.LEFT_PAREN){
                System.out.println("Expected \"(\" in loop!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                System.exit(1);
            }else{
                getNextToken();
                boolExpr();
                if(nextToken !=Symbols.RIGHT_PAREN){
                    System.out.println("Expected \")\" in loop!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                    System.exit(1);
                }else{
                    getNextToken();
                    if(nextToken == Symbols.LEFT_CURLY){
                        block();
                    }else {
                        stmt();
                        if(nextToken != Symbols.SEMICOLON){
                            System.out.println("Expected a \";\" in statement list!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                            System.exit(1);
                        }else{
                            getNextToken();
                        }
                    }
                    if (nextToken == Symbols.ELSE_CODE) {
                        getNextToken();
                        if(nextToken == Symbols.LEFT_CURLY){
                            block();
                        }else {
                            stmt();
                            if(nextToken != Symbols.SEMICOLON){
                                System.out.println("Expected a \";\" in statement list!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                                System.exit(1);
                            }else{
                                getNextToken();
                            }
                        }
                    }
                }
            }
        }
    }

    void block(){
        if(nextToken != Symbols.LEFT_CURLY){
            System.out.println("Expected \"{\"\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
            System.exit(1);
        }else{
            getNextToken();
            stmtList();
            if(nextToken != Symbols.RIGHT_CURLY){
                System.out.println("Expected \"}\"\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                System.exit(1);
            }
        }
    }

    void assign(){
        if(nextToken != Symbols.IDENT){
            System.out.println("Expected an identifier\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
            System.exit(1);
        }else{
            getNextToken();
            if(nextToken != Symbols.ASSIGN){
                System.out.println("Expected \"=\"\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                System.exit(1);
            }else{
                getNextToken();
                expr();
            }
        }
    }
    
    void declare(){
        if(nextToken != Symbols.DATATYPE){
            System.out.println("Expected a data type\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
            System.exit(1);
        }else{
            getNextToken();
            if(nextToken != Symbols.IDENT){
                System.out.println("Expected an identifier\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                System.exit(1);
            }else{
                getNextToken();
                while(nextToken == Symbols.COMMA){
                    getNextToken();
                    if(nextToken != Symbols.IDENT){
                        System.out.println("Expected an identifier\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                        System.exit(1);
                    }else{
                        getNextToken();
                    }
                }
            }
        }
    }

    void expr(){
        term();
        while(nextToken == Symbols.ADD_OP || nextToken == Symbols.MINUS_OP){
            getNextToken();
            term();
        }
    }

    void term(){
        fact();
        while(nextToken == Symbols.MULT_OP || nextToken == Symbols.DIV_OP || nextToken == Symbols.MOD_OP){
            getNextToken();
            fact();
        }
    }

    void fact(){
        if(nextToken == Symbols.IDENT || nextToken == Symbols.INT_LIT || nextToken == Symbols.FLOAT_LIT){
            if(i < tokens.size()-1) {
                getNextToken();
            }
        }else if(nextToken == Symbols.LEFT_PAREN){
            getNextToken();
            expr();
            if(nextToken == Symbols.RIGHT_PAREN){
                getNextToken();
            }else{
                System.out.println("Expected \")\" in loop!\nInstead received symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
                System.exit(1);
            }
        }else{
            System.out.println("Unexpected symbol: " + tokens.get(i).lexeme + "\nPosition: [" + tokens.get(i).row + ":" + tokens.get(i).column + "]");
            System.exit(1);
        }
    }

    void boolExpr(){
        bTerm();
        while(nextToken == Symbols.GREATER_THAN || nextToken == Symbols.LESS_THAN || nextToken == Symbols.GREATER_AND_EQUAL || nextToken == Symbols.LESS_AND_EQUAL){
            getNextToken();
            bTerm();
        }
    }

    void bTerm(){
        bAnd();
        while(nextToken == Symbols.EQUALS || nextToken == Symbols.NOT_EQUALS){
            getNextToken();
            bAnd();
        }
    }

    void bAnd(){
        bOr();
        while(nextToken == Symbols.AND_OP){
            getNextToken();
            bOr();
        }
    }

    void bOr(){
        expr();
        while(nextToken == Symbols.OR_OP){
            getNextToken();
            expr();
        }
    }

    void getNextToken() {
        if(i < tokens.size() - 1) {
            nextToken = tokens.get(++i).sCode;
        }else{
            System.out.println("Error: Cannot Reach EOF!");
            System.exit(1);
        }
    }
}
