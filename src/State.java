import java.util.*;

class State {

    ArrayList<String> tokens;
    int nextState;
    boolean isAccepting ;
    boolean isInitial ;

    public State(){
        tokens = new ArrayList<>();
        nextState = -1;
        isAccepting = false;
        isInitial = false;
    }



//    public void addEpsilonTransition(State state) {
//        addTransition('ε', state);
//    }
}

