import java.nio.file.*;

public class Main {


    static String sourse_code = null;
    static boolean check_file_name(String file_name){
        return file_name.contains(".nx");
    }

    public static void main(String[] args) throws Exception {

        /*
            RE OPERATORS:
            & -----> CONCATENATIONS
            | ------> UNION
            * ------> CLEAN CLOSURE
            + ------> POSITIVE CLOSURE

         */
        InfixtoPostFix converter = new InfixtoPostFix();
        String exp = "(_main|_int|_float|_char|_string|_true|_false|_print) |" +
                     " ([0-9])+ | (S) | (\\+|\\*|\\/|\\%|\\-|\\^) |" +
                     "  (!&!&.&\\n)  | (!&-&-&.& -&-&!) |  (\"({N\"\\}|\\&.)*\") | ([a-z]+)";
        System.out.println(converter.infixToPostfix(exp));

//        if(!check_file_name(args[0])){
//            System.out.println("file type not supported.");
//            return;
//        }
//
//        try{
//            Path path = Paths.get(args[0]);
//            byte[] content = Files.readAllBytes(path);
//            sourse_code = new String(content);
//            sourse_code = path.getFileName().toString();
//        }catch(Exception e){
//            if(e instanceof NoSuchFileException){
//                System.out.println("file not found.");
//                return;
//            }
//            System.out.println("Something went wrong.");
//        }
    }
}