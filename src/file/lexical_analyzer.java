package file;
import java.util.*;

class Token {
    String type;
    String value;

    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token(Type: " + type + ", Value: '" + value + "')";
    }
}

class LexicalAnalyzer {
    private DFA dfa;
    private List<Token> tokens;
    private SymbolTable symbolTable;
    private ErrorHandler errorHandler;

    private final Set<String> keywords = new HashSet<>(Arrays.asList("int", "float", "bool", "string", "if", "else", "while", "return", "true", "false"));
    private final Set<String> arithmeticOperators = new HashSet<>(Arrays.asList("+", "-", "*", "/", "%"));
    private final Set<String> inputOutputFunctions = new HashSet<>(Arrays.asList("printf", "scanf"));

    private String lastKeyword = "";  
    private int currentLine = 1;  
    private boolean lastStatementNeedsSemicolon = false;

    public LexicalAnalyzer(DFA dfa, SymbolTable symbolTable, ErrorHandler errorHandler) {
        this.dfa = dfa;
        this.tokens = new ArrayList<>();
        this.symbolTable = symbolTable;
        this.errorHandler = errorHandler;
    }

    public void analyze(String sourceCode) {
        sourceCode = preprocess(sourceCode);
        int start = 0;

        while (start < sourceCode.length()) {
            if (sourceCode.charAt(start) == '\n') {
                
                if (lastStatementNeedsSemicolon) {
                    errorHandler.logError(currentLine, "Missing semicolon (`;`).");
                    lastStatementNeedsSemicolon = false;
                }
                currentLine++;
                start++;
                continue;
            }

            if (sourceCode.charAt(start) == ' ') {
                start++;
                continue;
            }

            int end = start;
            DFAState currentState = dfa.startState;
            DFAState lastValidState = null;
            int lastValidIndex = start;

            while (end < sourceCode.length() && currentState.transitions.containsKey(sourceCode.charAt(end))) {
                currentState = currentState.transitions.get(sourceCode.charAt(end));

                if (currentState.isFinal) {
                    lastValidState = currentState;
                    lastValidIndex = end + 1;
                }

                end++;
            }

            if (lastValidState != null) {
                String tokenValue = sourceCode.substring(start, lastValidIndex);
                String tokenType = getTokenType(tokenValue);

                tokens.add(new Token(tokenType, tokenValue));

               
                if (inputOutputFunctions.contains(tokenValue)) {
                    if (!symbolTable.contains(tokenValue)) {
                        symbolTable.insert(tokenValue, "FUNCTION", "global", "I/O Operation");
                    }
                    lastStatementNeedsSemicolon = true;
                    start = lastValidIndex;
                    continue;
                }

               
                if (tokenType.equals("KEYWORD")) {
                    lastKeyword = tokenValue;
                    lastStatementNeedsSemicolon = false; 
                }

              
                if (tokenType.equals("IDENTIFIER") && !keywords.contains(tokenValue)) {
                    if (lastKeyword.equals("int") || lastKeyword.equals("float") || lastKeyword.equals("bool") || lastKeyword.equals("string")) {
                        if (!symbolTable.insert(tokenValue, lastKeyword, "global", "N/A")) {
                            errorHandler.logError(currentLine, "Duplicate declaration of variable '" + tokenValue + "'.");
                        }
                        lastKeyword = ""; 
                        lastStatementNeedsSemicolon = true;
                    } else {
                        if (!symbolTable.contains(tokenValue)) {
                            errorHandler.logError(currentLine, "Undeclared variable '" + tokenValue + "'.");
                        }
                        lastStatementNeedsSemicolon = true; 
                    }
                }

               
                if (tokenType.equals("INTEGER_CONSTANT") || tokenType.equals("DECIMAL_CONSTANT")) {
                    lastStatementNeedsSemicolon = true; 
                }

               
                if (tokenType.equals("DECIMAL_CONSTANT") && tokens.size() > 1) {
                    Token prevToken = tokens.get(tokens.size() - 2); 
                    if (prevToken.type.equals("IDENTIFIER") && symbolTable.contains(prevToken.value)) {
                        symbolTable.updateType(prevToken.value, "float");
                    }
                }

               
                if (arithmeticOperators.contains(tokenValue)) {
                    if (!symbolTable.contains(tokenValue)) {
                        symbolTable.insert(tokenValue, "ARITHMETIC_OPERATOR", "global", "N/A");
                    }
                    lastStatementNeedsSemicolon = true;
                }

               
                if (tokenType.equals("DELIMITER") && tokenValue.equals(";")) {
                    lastStatementNeedsSemicolon = false; 
                }

                start = lastValidIndex;
            } else {
                errorHandler.logError(currentLine, "Unrecognized token at position " + start + ": '" + sourceCode.charAt(start) + "'");
                start++;
            }
        }

       
        if (lastStatementNeedsSemicolon) {
            errorHandler.logError(currentLine, "Missing semicolon (`;`).");
        }
    }

    private String preprocess(String code) {
        return code.replaceAll("\\s+", " ").trim();
    }

    private String getTokenType(String value) {
        if (keywords.contains(value)) return "KEYWORD";
        if (dfa.matches(value)) {
            if (value.matches("[a-z][a-z0-9]*")) return "IDENTIFIER";
            if (value.matches("[0-9]+")) return "INTEGER_CONSTANT";
            if (value.matches("[0-9]+\\.[0-9]{1,5}")) return "DECIMAL_CONSTANT";
            if (value.matches("==|!=|>=|<=|&&|\\|\\|")) return "OPERATOR";
            if ("+-*/%=".contains(value)) return "OPERATOR";
            if ("(){}[]".contains(value)) return "BRACKET";
            if (";,.".contains(value)) return "DELIMITER";
        }
        return "UNKNOWN";
    }

    public void printTokens() {
        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println("Total Tokens: " + tokens.size());
    }
}
