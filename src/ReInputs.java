import java.util.HashSet;
import java.util.Set;

public class ReInputs {

    Set<String> set = new HashSet<>();

    private  static  ReInputs reInputs;
    private ReInputs(){
        set.add("*");
        set.add("+");
        set.add("[");
        set.add("]");
        set.add("_main");
        set.add("_int");
        set.add("_print");
        set.add("_float");
        set.add("_string");
        set.add("_true");
        set.add("_false");
        set.add("_char");
        set.add("-");
        set.add("\\");
        set.add("%");
        set.add("^");
        set.add("!!");
        set.add(".");
        set.add("/");
        set.add("\"");
    }

    public  Set<String> getTokens(){
        return set;
    }

    public static ReInputs getInstance(){
        return reInputs;
    }


}
