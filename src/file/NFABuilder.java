package file;
import java.util.*;
import java.io.*;
import java.nio.file.*;
class NFAState {
    int stateId;
    boolean isFinal;
    Map<Character, List<NFAState>> transitions;

    public NFAState(int id) {
        this.stateId = id;
        this.isFinal = false;
        this.transitions = new HashMap<>();
    }

    public void addTransition(char input, NFAState nextState) {
        transitions.putIfAbsent(input, new ArrayList<>());
        transitions.get(input).add(nextState);
    }
}

class NFA {
    NFAState startState;
    Set<NFAState> finalStates;
    Set<NFAState> allStates;

    public NFA() {
        this.finalStates = new HashSet<>();
        this.allStates = new HashSet<>();
    }

    public void setStartState(NFAState state) {
        this.startState = state;
        allStates.add(state);
    }

    public void addFinalState(NFAState state) {
        state.isFinal = true;
        finalStates.add(state);
        allStates.add(state);
    }

    public boolean matches(String input) {
        Set<NFAState> currentStates = new HashSet<>();
        currentStates.add(startState);

        for (char c : input.toCharArray()) {
            Set<NFAState> nextStates = new HashSet<>();
            for (NFAState state : currentStates) {
                if (state.transitions.containsKey(c)) {
                    nextStates.addAll(state.transitions.get(c));
                }
            }
            if (nextStates.isEmpty()) return false;
            currentStates = nextStates;
        }

        for (NFAState state : currentStates) {
            if (state.isFinal) return true;
        }
        return false;
    }

    public void printTransitions() {
        for (NFAState state : allStates) {
            for (Map.Entry<Character, List<NFAState>> entry : state.transitions.entrySet()) {
                for (NFAState nextState : entry.getValue()) {
                    System.out.println("State " + state.stateId + " --" + entry.getKey() + "--> State " + nextState.stateId);
                }
            }
        }
    }
}
/////////////////////////////////////////////////////////////////////////
class DFAState {
    int stateId;
    boolean isFinal;
    Map<Character, DFAState> transitions;

    public DFAState(int id) {
        this.stateId = id;
        this.isFinal = false;
        this.transitions = new HashMap<>();
    }

    public void addTransition(char input, DFAState nextState) {
        transitions.put(input, nextState);
    }
}

class DFA {
    DFAState startState;
    Set<DFAState> finalStates;
    Set<DFAState> allStates;

    public DFA() {
        this.finalStates = new HashSet<>();
        this.allStates = new HashSet<>();
    }

    public void setStartState(DFAState state) {
        this.startState = state;
        allStates.add(state);
    }

    public void addFinalState(DFAState state) {
        state.isFinal = true;
        finalStates.add(state);
        allStates.add(state);
    }

    public boolean matches(String input) {
        DFAState currentState = startState;

        for (char c : input.toCharArray()) {
            if (currentState.transitions.containsKey(c)) {
                currentState = currentState.transitions.get(c);
            } else {
                return false;
            }
        }

        return currentState.isFinal;
    }

    public void printTransitions() {
        for (DFAState state : allStates) {
            for (Map.Entry<Character, DFAState> entry : state.transitions.entrySet()) {
                System.out.println("State " + state.stateId + " --" + entry.getKey() + "--> State " + entry.getValue().stateId);
            }
        }
    }
}
////////////////////////////////////////////////////////////////////////
public class NFABuilder {
    public static NFA buildNFA() {
        NFA nfa = new NFA();

        // Create states
        NFAState q0 = new NFAState(0); // Start state
        nfa.setStartState(q0);
        NFAState q1 = new NFAState(1);
        NFAState q2 = new NFAState(2);
        NFAState q3 = new NFAState(3);
        NFAState q4 = new NFAState(4);
        NFAState q5 = new NFAState(5);
        NFAState q6 = new NFAState(6);
        NFAState q7 = new NFAState(7);
        NFAState q8 = new NFAState(8);
        NFAState q9 = new NFAState(9);
        NFAState q10 = new NFAState(10);
        
        NFAState qCharStart = new NFAState(12);
        NFAState qCharFinal = new NFAState(13);
        q0.addTransition('\'', qCharStart);
        for (char c = 32; c <= 126; c++) { // Any printable character
            if (c != '\'') qCharStart.addTransition(c, qCharFinal);
        }
        qCharFinal.addTransition('\'', q0); // Correctly transition back
        nfa.addFinalState(qCharFinal);

        // String Constants: "..."
        NFAState qStringStart = new NFAState(14);
        NFAState qStringBody = new NFAState(15);
        NFAState qStringFinal = new NFAState(16);
        q0.addTransition('"', qStringStart);
        for (char c = 32; c <= 126; c++) { // Any printable character except "
            if (c != '"') qStringStart.addTransition(c, qStringBody);
        }
        qStringBody.addTransition('"', qStringFinal);
        qStringBody.addTransition((char) 32, qStringBody); // Allow spaces
        qStringFinal.addTransition('"', q0); // Transition back to start
        nfa.addFinalState(qStringFinal);

        // Relational Operators: ==, !=, >=, <=, >, <
        NFAState qRelOp = new NFAState(17);
        NFAState qRelOpFinal = new NFAState(18);
        q0.addTransition('=', qRelOp);
        q0.addTransition('!', qRelOp);
        q0.addTransition('>', qRelOp);
        q0.addTransition('<', qRelOp);
        qRelOp.addTransition('=', qRelOpFinal);
        nfa.addFinalState(qRelOp);
        nfa.addFinalState(qRelOpFinal);

        // Logical Operators: &&, ||, !
        NFAState qLogicOp = new NFAState(19);
        NFAState qLogicOpFinal = new NFAState(20);
        q0.addTransition('&', qLogicOp);
        q0.addTransition('|', qLogicOp);
        qLogicOp.addTransition('&', qLogicOpFinal);
        qLogicOp.addTransition('|', qLogicOpFinal);
        q0.addTransition('!', qLogicOpFinal);
        nfa.addFinalState(qLogicOpFinal);

        // Grouping Symbols: ( ) { } [ ]
        NFAState qGroupSymbol = new NFAState(21);
        q0.addTransition('(', qGroupSymbol);
        q0.addTransition(')', qGroupSymbol);
        q0.addTransition('{', qGroupSymbol);
        q0.addTransition('}', qGroupSymbol);
        q0.addTransition('[', qGroupSymbol);
        q0.addTransition(']', qGroupSymbol);
        nfa.addFinalState(qGroupSymbol);

        // Assignment Operator: =
        NFAState qAssignOp = new NFAState(22);
        q0.addTransition('=', qAssignOp);
        nfa.addFinalState(qAssignOp);

        // Delimiters: ; ,
        NFAState qDelimiter = new NFAState(23);
        q0.addTransition(';', qDelimiter);
        q0.addTransition(',', qDelimiter);
        nfa.addFinalState(qDelimiter);

        // Single-Line Comments: //...
        NFAState qSingleLineComment = new NFAState(24);
        q0.addTransition('/', qSingleLineComment);
        qSingleLineComment.addTransition('/', qSingleLineComment);
        for (char c = 32; c <= 126; c++) { // Any printable character until newline
            qSingleLineComment.addTransition(c, qSingleLineComment);
        }
        nfa.addFinalState(qSingleLineComment);

        // Multi-Line Comments: /* ... */
        NFAState qMultiLineStart = new NFAState(25);
        NFAState qMultiLineMid = new NFAState(26);
        NFAState qMultiLineEnd = new NFAState(27);
        q0.addTransition('/', qMultiLineStart);
        qMultiLineStart.addTransition('*', qMultiLineMid);
        for (char c = 32; c <= 126; c++) {
            qMultiLineMid.addTransition(c, qMultiLineMid);
        }
        qMultiLineMid.addTransition('*', qMultiLineEnd);
        qMultiLineEnd.addTransition('/', q0); // Correctly transition back
        nfa.addFinalState(qMultiLineEnd);
        
        // Identifiers: [a-z][a-z0-9]*
        for (char c = 'a'; c <= 'z'; c++) q0.addTransition(c, q1);
        for (char c = 'a'; c <= 'z'; c++) q1.addTransition(c, q1);
        for (char c = '0'; c <= '9'; c++) q1.addTransition(c, q1);
//        nfa.setStartState(q0);
        nfa.addFinalState(q1);

        // Integer Constants: [0-9]+
        for (char c = '0'; c <= '9'; c++) {
            q2.addTransition(c, q3);
            q3.addTransition(c, q3);
        }
        nfa.addFinalState(q3);
        
        // Link integer state from start state
        for (char c = '0'; c <= '9'; c++) {
            q0.addTransition(c, q2);
        }

        // Decimal Numbers: [0-9]+\.[0-9]{1,5}
        q3.addTransition('.', q4);
        for (char c = '0'; c <= '9'; c++) {
            q4.addTransition(c, q5);
            q5.addTransition(c, q5);
        }
        nfa.addFinalState(q5);

        // Boolean Constants: "true" and "false"
        q6.addTransition('t', q7);
        q7.addTransition('r', q8);
        q8.addTransition('u', q9);
        q9.addTransition('e', q10);
        nfa.addFinalState(q10);

        q6.addTransition('f', q7);
        q7.addTransition('a', q8);
        q8.addTransition('l', q9);
        q9.addTransition('s', q10);
        q10.addTransition('e', q10);
        nfa.addFinalState(q10);

        // Operators: +, -, *, /, %
        NFAState qOperator = new NFAState(11);
        q0.addTransition('+', qOperator);
        q0.addTransition('-', qOperator);
        q0.addTransition('*', qOperator);
        q0.addTransition('/', qOperator);
        q0.addTransition('%', qOperator);
        q0.addTransition('=', qOperator);
        q0.addTransition(';', qOperator);
        nfa.addFinalState(qOperator);

        return nfa;
    }
    
    public static DFA convertNFAToDFA(NFA nfa) {
        DFA dfa = new DFA();

        // Create DFA states based on the NFA states
        DFAState dfaStartState = new DFAState(0);
        dfa.setStartState(dfaStartState);
        DFAState dfaState1 = new DFAState(1);
        DFAState dfaState2 = new DFAState(2);
        DFAState dfaState3 = new DFAState(3);
        DFAState dfaState4 = new DFAState(4);
        DFAState dfaState5 = new DFAState(5);
        DFAState dfaState6 = new DFAState(6);
        DFAState dfaState7 = new DFAState(7);
        DFAState dfaState8 = new DFAState(8);
        DFAState dfaState9 = new DFAState(9);
        DFAState dfaState10 = new DFAState(10);
        DFAState dfaState11 = new DFAState(11);
        DFAState dfaState12 = new DFAState(12);
        DFAState dfaState13 = new DFAState(13);
        DFAState dfaState14 = new DFAState(14);
        DFAState dfaState15 = new DFAState(15);
        DFAState dfaState16 = new DFAState(16);
        DFAState dfaState17 = new DFAState(17);
        DFAState dfaState18 = new DFAState(18);
        DFAState dfaState19 = new DFAState(19);
        DFAState dfaState20 = new DFAState(20);
        DFAState dfaState21 = new DFAState(21);
        DFAState dfaState22 = new DFAState(22);
        DFAState dfaState23 = new DFAState(23);
        DFAState dfaState24 = new DFAState(24);
        DFAState dfaState25 = new DFAState(25);
        DFAState dfaState26 = new DFAState(26);
        
        // Character Constants
        dfaStartState.addTransition('\'', dfaState10);
        for (char c = 32; c <= 126; c++) {
            if (c != '\'') dfaState10.addTransition((char) c, dfaState11);
        }
        dfaState11.addTransition('\'', dfaState12);
        dfa.addFinalState(dfaState12);

        // String Constants
        dfaStartState.addTransition('"', dfaState13);
        for (char c = 32; c <= 126; c++) {
            if (c != '"') dfaState13.addTransition((char) c, dfaState13);
        }
        dfaState13.addTransition('"', dfaState14);
        dfa.addFinalState(dfaState14);

        // Relational Operators
        dfaStartState.addTransition('=', dfaState15);
        dfaState15.addTransition('=', dfaState16); // "=="
        dfaStartState.addTransition('!', dfaState15);
        dfaState15.addTransition('=', dfaState16); // "!="
        dfaStartState.addTransition('>', dfaState15);
        dfaState15.addTransition('=', dfaState16); // ">="
        dfaStartState.addTransition('<', dfaState15);
        dfaState15.addTransition('=', dfaState16); // "<="
        dfa.addFinalState(dfaState15);
        dfa.addFinalState(dfaState16);

        // Logical Operators
        dfaStartState.addTransition('&', dfaState17);
        dfaState17.addTransition('&', dfaState18); // "&&"
        dfaStartState.addTransition('|', dfaState17);
        dfaState17.addTransition('|', dfaState18); // "||"
        dfaStartState.addTransition('!', dfaState19); // "!" is valid alone
        dfa.addFinalState(dfaState18);
        dfa.addFinalState(dfaState19);

        // Grouping Symbols
        dfaStartState.addTransition('(', dfaState20);
        dfaStartState.addTransition(')', dfaState20);
        dfaStartState.addTransition('{', dfaState20);
        dfaStartState.addTransition('}', dfaState20);
        dfaStartState.addTransition('[', dfaState20);
        dfaStartState.addTransition(']', dfaState20);
        dfa.addFinalState(dfaState20);

        // Assignment Operator
        dfaStartState.addTransition('=', dfaState21);
        dfa.addFinalState(dfaState21);

        // Delimiters
        dfaStartState.addTransition(';', dfaState22);
        dfaStartState.addTransition(',', dfaState22);
        dfa.addFinalState(dfaState22);

        // Single-Line Comments
        dfaStartState.addTransition('/', dfaState23);
        dfaState23.addTransition('/', dfaState24);
        for (char c = 32; c <= 126; c++) {
            if (c != '\n') dfaState24.addTransition(c, dfaState24);
        }
        dfa.addFinalState(dfaState24);

        // Multi-Line Comments
        dfaStartState.addTransition('/', dfaState25);
        dfaState25.addTransition('*', dfaState26);
        for (char c = 32; c <= 126; c++) {
            dfaState26.addTransition(c, dfaState26);
        }
        dfaState26.addTransition('*', dfaState25);
        dfaState25.addTransition('/', dfaState22);
        dfa.addFinalState(dfaState22);
        // Set the start state
//        dfa.setStartState(dfaStartState);

        // Add transitions based on the NFA transitions
        // Identifiers: [a-z][a-z0-9]*
        for (char c = 'a'; c <= 'z'; c++) {
            dfaStartState.addTransition(c, dfaState1);
            dfaState1.addTransition(c, dfaState1);
        }
        for (char c = '0'; c <= '9'; c++) {
            dfaState1.addTransition(c, dfaState1);
        }
        dfa.addFinalState(dfaState1);

        // Integer Constants: [0-9]+
        for (char c = '0'; c <= '9'; c++) {
            dfaStartState.addTransition(c, dfaState2);
            dfaState2.addTransition(c, dfaState2);
        }
        dfa.addFinalState(dfaState2);

        // Decimal Numbers: [0-9]+\.[0-9]{1,5}
        dfaState2.addTransition('.', dfaState3);
        for (char c = '0'; c <= '9'; c++) {
            dfaState3.addTransition(c, dfaState4);
            dfaState4.addTransition(c, dfaState4);
        }
        dfa.addFinalState(dfaState4);

        // Boolean Constants: "true" and "false"
        dfaStartState.addTransition('t', dfaState5);
        dfaState5.addTransition('r', dfaState6);
        dfaState6.addTransition('u', dfaState7);
        dfaState7.addTransition('e', dfaState8);
        dfa.addFinalState(dfaState8);

        dfaStartState.addTransition('f', dfaState5);
        dfaState5.addTransition('a', dfaState6);
        dfaState6.addTransition('l', dfaState7);
        dfaState7.addTransition('s', dfaState8);
        dfaState8.addTransition('e', dfaState8);
        dfa.addFinalState(dfaState8);

        // Operators: +, -, *, /, %
        dfaStartState.addTransition('+', dfaState9);
        dfaStartState.addTransition('-', dfaState9);
        dfaStartState.addTransition('*', dfaState9);
        dfaStartState.addTransition('/', dfaState9);
        dfaStartState.addTransition('%', dfaState9);
        dfaStartState.addTransition('=', dfaState9);
        dfaStartState.addTransition(';', dfaState9);

        dfa.addFinalState(dfaState9);

        return dfa;
    }
    
    public static void main(String[] args) {
        NFA nfa = buildNFA();

        DFA dfa = convertNFAToDFA(nfa);
        SymbolTable symbolTable = new SymbolTable();
        ErrorHandler errorHandler = new ErrorHandler();


        System.out.println("DFA Transitions:");
        dfa.printTransitions();

        String[] testInputs = {"abc", "123", "45.67", "true", "false", "+", "-", "%","int"};
        for (String input : testInputs) {
            System.out.println("Does DFA accept \"" + input + "\"? " + dfa.matches(input));
        }
        
        // Example source code (modify as needed)
//        String sourceCode = "int x = 10;"
//        		+ "int y= 20.5; "
//        		+ " int result = x + y;"
//        		+ " if (true) { printf(result); }";

        
        String filePath = "exampleCode.fastlang"; // Path to the source file
        System.out.println("Looking for file in: " + Path.of(filePath).toAbsolutePath());

        try {
            String sourceCode = Files.readString(Path.of(filePath));
            LexicalAnalyzer lexer = new LexicalAnalyzer(dfa, symbolTable, errorHandler);
            lexer.analyze(sourceCode);
            lexer.printTokens();
            
            symbolTable.printTable();
            errorHandler.printErrors();

        } catch (IOException e) {
            System.err.println("Error: Unable to read file " + filePath);
            e.printStackTrace();
        }

    }
}
