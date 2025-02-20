package file;
import java.util.*;
class ErrorHandler {
    private List<String> errors;

    public ErrorHandler() {
        this.errors = new ArrayList<>();
    }

    public void logError(int lineNumber, String message) {
        errors.add("Error (Line " + lineNumber + "): " + message);
    }

    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("No errors found.");
        } else {
            System.out.println("\n--- Errors Detected ---");
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}

