import java.util.*;


// The NFA graph will be a sparse graph so let's use the adjacency list for it.
/*
    FOR THE ADJACENCY LIST:
    We will use the hashmaps.

    Will show the
    lambda as lambda
 */


/*
* RULES FOR PARSING THE RE:
* Kleene Closure(*) 3 Lambda 3 States
* Union 2 State, NO Lambda
* Positive Closure(+) 2 Lambda 3 States
*  */

class NFA {


    InfixtoPostFix convert = new InfixtoPostFix();
    State startState;
    State acceptingState;

    public NFA(State start, State accepting) {
        this.startState = start;
        this.acceptingState = accepting;
    }

    void manageUnion(String re){

    }

    void manageCancatenation(String re){}

    void  manageKleeneClosure(String re){}

    void  managePositiveClosure(String re){}

    private  Boolean isAlphaNeumeric(Character c){
        return  (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') ;
    }
    // ACTUAL BACKBONE OF THE CLASS. WILL CONVERT RE TO NFA
    public void createNfa(String re){
        Stack<String> st = new Stack<String>();
        String postFix = convert.infixToPostfix(re);
        ReInputs singleton = ReInputs.getInstance();
        Set<String> set = singleton.set;

        for (int i = 0; i < postFix.length(); i++){
            char c = postFix.charAt(i);

            //  IT WILL BE AN RE INPUT CONSISTING OF SINGLE CHARACTER
            //  JUST PUT IT INTO THE STACK
            if(isAlphaNeumeric(c)){
                st.push(Character.toString(c));
            }
            //  A KEYWORD IS COMING
            else if(c == '_'){
                StringBuilder str = new StringBuilder();
                while(!set.contains(str.toString())){
                    str.append(postFix.charAt(i));
                    i++;
                }
                st.push(str.toString());
            }else if(c == '['){
                
            }

        }

    }


}
