import java.util.*;

class InfixtoPostFix {

    // Function to return precedence of operators
    int prec(char c) {

        if (c == '.' )
            return 1;
        else if(c == '|')
            return 2;
        else if(c == '&')
            return 3;
        else
            return -1;
    }

    String infixToPostfix(String s) {
        Stack<Character> st = new Stack<>();
        StringBuilder result = new StringBuilder();
        ReInputs singleton = ReInputs.getInstance();
        Set<String> set = singleton.set;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ')continue;
            //ALPHA-NUMERIC CHARACTER OR THE CHARACTER CONTAINS BY SET
            if ((c >= 'a' && c <= 'z')
                    || (c >= 'A' && c <= 'Z')
                    || (c >= '0' && c <= '9')
                    || set.contains(String.valueOf(c))
            )
                result.append(c);

            //IF A KEYWORD IS EXPECTED
            else if (c == '_'){
                StringBuilder str = new StringBuilder();
                while (i < s.length()){
                    str.append(c);
                    if(set.contains(str.toString())) {
                        result.append(str.toString());
                        break;
                    }
                    i++;
                    c = s.charAt(i);
                }
            }

            //HANDLING THE RE CHARACTER FOR COMMENTS
            else if(c == '!'){
                StringBuilder str = new StringBuilder();
                str.append(c);
                i++;
                while (s.charAt(i) != '!'){
                    str.append(s.charAt(i));
                    i++;
                }
                str.append(s.charAt(i));
                result.append(str.toString());
            }
            else if (c == '(')
                st.push('(');
            else if (c == ')') {
                while (st.peek() != '(') {
                    result.append(st.pop());
                }
                st.pop();
            }
            else {
                System.out.println(c);
                while (!st.isEmpty() && (prec(c) < prec(st.peek()) ||
                        prec(c) == prec(st.peek()))) {
                    result.append(st.pop());
                }
                st.push(c);
            }
        }

        while (!st.isEmpty()) {
            result.append(st.pop());
        }
        return  result.toString();
    }


}
