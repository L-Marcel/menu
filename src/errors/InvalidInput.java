package src.errors;

public class InvalidInput extends RuntimeException {
    public InvalidInput(String message) {
        super(message);
    }
}